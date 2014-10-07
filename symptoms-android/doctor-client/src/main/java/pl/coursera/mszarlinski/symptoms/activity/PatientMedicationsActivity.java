package pl.coursera.mszarlinski.symptoms.activity;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.activity.component.medicationList.MedicationItem;
import pl.coursera.mszarlinski.symptoms.activity.component.medicationList.MedicationListAdapter;
import pl.coursera.mszarlinski.symptoms.async.FetchMedicationsTask;
import pl.coursera.mszarlinski.symptoms.async.SaveMedicationsTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.manager.MedicationManager;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;
import pl.mszarlinski.coursera.symptoms.R;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class PatientMedicationsActivity extends AsyncActivity {
	private static final MedicationManager medicationManager = MedicationManager.getInstance();

	@InjectView(R.id.medicationsListView)
	ListView medicationsListView;
	@InjectView(R.id.saveButton)
	BootstrapButton saveButton;

	private long patientId;

	private List<MedicationItem> medicationList = new ArrayList<MedicationItem>();
	private MedicationListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_medications);

		ButterKnife.inject(this);

		patientId = getIntent().getExtras().getLong(Constants.PATIENT_ID_EXTRA);

		adapter = new MedicationListAdapter(this, medicationList);
		medicationsListView.setAdapter(adapter);

		setupView();
	}

	private void setupView() {
		medicationManager.fetchMedications(patientId, this);
	}

	@OnClick(R.id.saveButton)
	public void save() {
		medicationManager.saveMedications(MedicationItem.convertToResources(medicationList), patientId, this);
	}

	@OnClick(R.id.returnButton)
	public void cancel() {
		finish();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		switch (asyncTaskId) {
		case FetchMedicationsTask.ASYNC_TASK_ID:
			List<Medication> medications = (List<Medication>) result;
			medicationList.clear();
			medicationList.addAll(MedicationItem.convert(medications));
			adapter.notifyDataSetChanged();
			break;
		case SaveMedicationsTask.ASYNC_TASK_ID:
			finish();
			break;
		default:
			throw new IllegalArgumentException("Unknown asyncTaskId: " + asyncTaskId);
		}
	}
}
