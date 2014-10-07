package pl.coursera.mszarlinski.symptoms.activity.checkIn;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.checkIn.component.medicationList.MedicationItem;
import pl.coursera.mszarlinski.symptoms.activity.checkIn.component.medicationList.MedicationListAdapter;
import pl.coursera.mszarlinski.symptoms.rest.resource.Answer;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
public class DidYouTakeMedicationsActivity extends Activity {

	public static final int TIME_PICKER_DIALOG_ID = 1;

	@InjectView(R.id.medicationsListView)
	ListView medicationsListView;

	private MedicationListAdapter medicationListAdapter;

	private CheckIn checkIn;
	/**
	 * Id of medication which has time being set by dialog.
	 */
	// TODO: custom TimePickerDialog with medicationId property
	public static int medicationId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.did_you_take_medications);

		ButterKnife.inject(this);

		checkIn = (CheckIn) getIntent().getExtras().get(Constants.CHECK_IN_EXTRA);
		medicationListAdapter = new MedicationListAdapter(this, MedicationItem.convert(checkIn.medications));
		medicationsListView.setAdapter(medicationListAdapter);
	}

	@OnClick(R.id.nextButton)
	public void clickNext() {
		for (MedicationItem item : medicationListAdapter.getItems()) {
			if (item.selected) {
				if (item.time != null) {
					checkIn.addAnswer(new Answer().forMedication(item.medicationId).withContent(item.time.toString()));
				} else {
					Toast.makeText(this, R.string.date_not_set, Toast.LENGTH_LONG).show();
					return;
				}
			}
		}

		Intent i = new Intent(this, TakeThroatPhotoActivity.class);
		i.putExtra(Constants.CHECK_IN_EXTRA, checkIn);
		startActivity(i);
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

		public void onTimeSet(TimePicker view, final int selectedHour, final int selectedMinute) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					medicationListAdapter.updateItem(medicationId, selectedHour, selectedMinute);
					medicationListAdapter.notifyDataSetChanged();
				}
			});

		}

	};

}
