package pl.coursera.mszarlinski.symptoms.activity;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.activity.component.checkInList.CheckInItem;
import pl.coursera.mszarlinski.symptoms.activity.component.checkInList.CheckInListAdapter;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.manager.PatientManager;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import pl.mszarlinski.coursera.symptoms.R;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 
 * @author Maciej
 *
 */
public class PatientCheckInsActivity extends AsyncActivity {
	private static final PatientManager patientManager = PatientManager.getInstance();

	@InjectView(R.id.checkInsListView)
	ListView checkInsListView;

	private long patientId;

	private List<CheckInItem> checkInsList = new ArrayList<CheckInItem>();
	private CheckInListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_checkins);

		ButterKnife.inject(this);

		patientId = getIntent().getExtras().getLong(Constants.PATIENT_ID_EXTRA);

		adapter = new CheckInListAdapter(this, checkInsList);
		checkInsListView.setAdapter(adapter);

		setupView();
	}

	private void setupView() {
		patientManager.fetchPatientCheckIns(patientId, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		List<CheckIn> checkIns = (List<CheckIn>) result;
		checkInsList.clear();
		checkInsList.addAll(CheckInItem.convert(checkIns));
		adapter.notifyDataSetChanged();
	}
}
