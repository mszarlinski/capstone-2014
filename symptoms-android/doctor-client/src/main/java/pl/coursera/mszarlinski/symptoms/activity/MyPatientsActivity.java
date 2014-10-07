package pl.coursera.mszarlinski.symptoms.activity;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.activity.component.patientList.PatientItem;
import pl.coursera.mszarlinski.symptoms.activity.component.patientList.PatientListAdapter;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.manager.PatientManager;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;
import pl.mszarlinski.coursera.symptoms.R;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTextChanged;

/**
 * 
 * @author Maciej
 *
 */
public class MyPatientsActivity extends AsyncActivity {
	private static final PatientManager patientManager = PatientManager.getInstance();

	@InjectView(R.id.patientFilter)
	EditText patientFilter;
	@InjectView(R.id.patientListView)
	ListView patientListView;

	private List<PatientItem> allPatientList = new ArrayList<PatientItem>();
	private List<PatientItem> currentPatientList = new ArrayList<PatientItem>();
	private PatientListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_patients);

		ButterKnife.inject(this);

		setupView();

		adapter = new PatientListAdapter(this, currentPatientList);
		patientListView.setAdapter(adapter);
	}

	private void setupView() {
		patientManager.fetchPatients(this);
	}

	@OnTextChanged(R.id.patientFilter)
	public void filterPatients() {
		currentPatientList.clear();
		if (patientFilter.getText().toString().isEmpty()) {
			currentPatientList.addAll(allPatientList);
		} else {
			String filterWord = patientFilter.getText().toString().toLowerCase();
			for (PatientItem item : allPatientList) {
				if (item.label.toLowerCase().contains(filterWord)) {
					currentPatientList.add(item);
				}
			}
		}

		adapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		List<Patient> patients = (List<Patient>) result;
		allPatientList = PatientItem.convert(patients);
		currentPatientList.clear();
		currentPatientList.addAll(allPatientList);
		adapter.notifyDataSetChanged();
	}
}
