package pl.coursera.mszarlinski.symptoms.activity.app.doctors;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList.DoctorItem;
import pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList.DoctorListAdapter;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.manager.AssignmentManager;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class AddDoctorActivity extends AsyncActivity {

	private static final AssignmentManager assignmentManager = AssignmentManager.getInstance();

	@InjectView(R.id.doctorsFilter)
	EditText doctorsFilter;
	@InjectView(R.id.selectDoctorList)
	ListView selectDoctorList;

	private DoctorListAdapter doctorListAdapter;
	private List<DoctorItem> filteredDoctorList = new ArrayList<DoctorItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_doctor);

		ButterKnife.inject(this);

		doctorListAdapter = new DoctorListAdapter(this, filteredDoctorList);
		selectDoctorList.setAdapter(doctorListAdapter);
	}

	@OnItemClick(R.id.selectDoctorList)
	public void doctorSelected(int position) {
		if (position >= 0) {
			Intent i = getIntent();
			DoctorItem item = filteredDoctorList.get(position);
			System.out.println("Selecte item id = " + item.doctorId);
			if (item != null) {
				i.putExtra(Constants.SELECTED_DOCTOR_EXTRA, item);
				setResult(RESULT_OK, i);
				finish();
			}
		}
	}

	@OnTextChanged(R.id.doctorsFilter)
	public void filterDoctors() {
		if (doctorsFilter.getText().toString().length() > 2) {
			updateList();
		}
	}

	private void updateList() {
		String filterWord = doctorsFilter.getText().toString();
		assignmentManager.queryForDoctors(filterWord, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		List<Doctor> doctors = (List<Doctor>) result;
		filteredDoctorList.clear();
		if (!doctors.isEmpty()) {
			filteredDoctorList.addAll(DoctorItem.convert(doctors));
		}
		doctorListAdapter.notifyDataSetChanged();
	}
}
