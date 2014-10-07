package pl.coursera.mszarlinski.symptoms.activity.app.doctors;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList.DoctorItem;
import pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList.SelectedDoctorListAdapter;
import pl.coursera.mszarlinski.symptoms.async.FetchAssignmentsTask;
import pl.coursera.mszarlinski.symptoms.async.SendAssignmentsTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.manager.AssignmentManager;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * List of all doctors to select many.
 * 
 * @author Maciej
 *
 */
public class DoctorListActivity extends AsyncActivity {
	public static final int ADD_DOCTOR_REQUEST_CODE = 1;

	private static final AssignmentManager assignmentManager = AssignmentManager.getInstance();

	@InjectView(R.id.selectedDoctorList)
	ListView doctorsList;
	@InjectView(R.id.addDoctor)
	BootstrapButton addDoctorButton;

	private List<DoctorItem> selectedDoctorList = new ArrayList<DoctorItem>();
	private SelectedDoctorListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selected_doctors);

		ButterKnife.inject(this);

		adapter = new SelectedDoctorListAdapter(this, selectedDoctorList);
		doctorsList.setAdapter(adapter);

		fetchAssignments();
	}

	private void fetchAssignments() {
		assignmentManager.fetchAssignments(this);
	}

	@OnClick(R.id.addDoctor)
	public void addDoctor() {
		Intent i = new Intent(this, AddDoctorActivity.class);
		startActivityForResult(i, ADD_DOCTOR_REQUEST_CODE);
	}

	@OnClick(R.id.doneButton)
	public void addingComplete() {
		long[] doctorIds = extractDoctorIds();
		showProgressDialog();
		assignmentManager.sendAssignments(doctorIds, this);
	}

	private long[] extractDoctorIds() {
		long[] ids = new long[selectedDoctorList.size()];
		int i = 0;
		for (DoctorItem item : selectedDoctorList) {
			ids[i++] = item.doctorId;
		}
		return ids;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_DOCTOR_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				DoctorItem item = (DoctorItem) data.getExtras()
						.getSerializable(Constants.SELECTED_DOCTOR_EXTRA);
				if (!selectedDoctorList.contains(item)) {
					selectedDoctorList.add(item);
					adapter.notifyDataSetChanged();
				}

			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		switch (asyncTaskId) {
		case SendAssignmentsTask.ASYNC_TASK_ID:
			finish();
			break;
		case FetchAssignmentsTask.ASYNC_TASK_ID:
			List<Doctor> doctors = (List<Doctor>) result;
			selectedDoctorList.clear();
			selectedDoctorList.addAll(DoctorItem.convert(doctors));
			adapter.notifyDataSetChanged();
			break;
		default:
			throw new IllegalArgumentException("Unexpected asyncTaskId: " + asyncTaskId);
		}
	}
}
