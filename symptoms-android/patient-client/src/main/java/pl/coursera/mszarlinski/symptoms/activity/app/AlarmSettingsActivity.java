package pl.coursera.mszarlinski.symptoms.activity.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapButton;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.checkIn.component.alarmList.AlarmListAdapter;
import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;
import pl.coursera.mszarlinski.symptoms.manager.NotificationManager;
import pl.coursera.mszarlinski.symptoms.persistence.SQLiteDAO;
import pl.coursera.mszarlinski.symptoms.persistence.entity.Alarm;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 
 * @author Maciej
 *
 */
public class AlarmSettingsActivity extends Activity {

	private static final int TIME_PICKER_DIALOG_ID = 1;

	private static final NotificationManager notificationManager = NotificationManager.getInstance();

	private List<Alarm> alarmList = new ArrayList<Alarm>();
	private List<Alarm> removedAlarmList = new ArrayList<Alarm>();
	private AlarmListAdapter alarmListAdapter;

	@InjectView(R.id.alarmListView)
	ListView alarmListView;
	@InjectView(R.id.saveAlarmsButton)
	BootstrapButton saveAlarmsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		ButterKnife.inject(this);

		alarmListAdapter = new AlarmListAdapter(this, alarmList);
		alarmListView.setAdapter(alarmListAdapter);

		setupView();
	}

	private void setupView() {
		SQLiteDAO dao = new SQLiteDAO(this);
		alarmList.clear();
		alarmList.addAll(dao.getAllAlarms());
	}

	@OnClick(R.id.addAlarm)
	public void addAlarm() {
		showDialog(TIME_PICKER_DIALOG_ID);
	}

	@OnClick(R.id.saveAlarmsButton)
	public void saveAlarms() {
		if (alarmList.size() < Constants.MIN_CHECK_IN_ALARMS) {
			Toast.makeText(this, String.format(getString(R.string.min_alarms_number_warning), Constants.MIN_CHECK_IN_ALARMS), Toast.LENGTH_LONG).show();
			return;
		}

		notificationManager.rescheduleAlarms(alarmList, removedAlarmList, AlarmSettingsActivity.this);
		Toast.makeText(this, R.string.alarms_saved, Toast.LENGTH_LONG).show();
		finish();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_PICKER_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, 12, 0, true);

		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			final TimeOfDay timeOfDay = TimeOfDay.of(selectedHour, selectedMinute);
			final Alarm newAlarm = new Alarm();
			newAlarm.time = timeOfDay;
			if (!alarmList.contains(newAlarm)) {
				alarmList.add(newAlarm);
				Collections.sort(alarmList, Alarm.COMPARATOR);
				alarmListAdapter.notifyDataSetChanged();
			}
		}

	};

	public void unregisterAlarm(Alarm item, AlarmSettingsActivity context) {
		removedAlarmList.add(item);
		if (alarmList.remove(item)) {
			alarmListAdapter.notifyDataSetChanged();
		}
	}
}
