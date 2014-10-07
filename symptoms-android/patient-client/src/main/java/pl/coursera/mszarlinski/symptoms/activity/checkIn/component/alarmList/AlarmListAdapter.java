package pl.coursera.mszarlinski.symptoms.activity.checkIn.component.alarmList;

import java.util.List;

import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.app.AlarmSettingsActivity;
import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;
import pl.coursera.mszarlinski.symptoms.manager.NotificationManager;
import pl.coursera.mszarlinski.symptoms.persistence.entity.Alarm;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class AlarmListAdapter extends ArrayAdapter<Alarm> {

	private static final NotificationManager notificationManager = NotificationManager.getInstance();

	private AlarmSettingsActivity context;

	public AlarmListAdapter(AlarmSettingsActivity context, List<Alarm> items) {
		super(context, R.layout.alarm_list_item, items);
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Alarm item = getItem(position);

		View rowView = convertView;
		AlarmViewHolder holder;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			rowView = inflater.inflate(R.layout.alarm_list_item, parent, false);
			holder = new AlarmViewHolder();
			holder.time = (TextView) rowView.findViewById(R.id.time);
			holder.deleteButton = (BootstrapButton) rowView.findViewById(R.id.deleteButton);
			rowView.setTag(holder);
		} else {
			holder = (AlarmViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	private void setupView(final AlarmViewHolder holder, final Alarm item) {
		holder.time.setText(item.time.toString());

		holder.deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				context.unregisterAlarm(item, context);
			}
		});

	}

	public static class AlarmViewHolder {
		TextView time;
		BootstrapButton deleteButton;
	}
}
