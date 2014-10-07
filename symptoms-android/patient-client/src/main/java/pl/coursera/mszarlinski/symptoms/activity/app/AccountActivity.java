package pl.coursera.mszarlinski.symptoms.activity.app;

import java.util.Calendar;
import java.util.Date;

import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.app.doctors.DoctorListActivity;
import pl.coursera.mszarlinski.symptoms.async.FetchPatientTask;
import pl.coursera.mszarlinski.symptoms.async.RegisterPatientTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.constants.BootstrapConstants;
import pl.coursera.mszarlinski.symptoms.commons.utils.UIUtils;
import pl.coursera.mszarlinski.symptoms.manager.AccountManager;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class AccountActivity extends AsyncActivity {

	private static final AccountManager accountManager = AccountManager.getInstance();

	private static final int DATE_PICKER_DIALOG_ID = 1;

	@InjectView(R.id.registerButton)
	BootstrapButton registerButton;
	@InjectView(R.id.selectDoctorsButton)
	BootstrapButton selectDoctorButton;
	@InjectView(R.id.connectCheckBox)
	CheckBox connectCheckBox;
	@InjectView(R.id.nameInput)
	EditText nameInput;
	@InjectView(R.id.secondNameInput)
	EditText secondNameInput;
	@InjectView(R.id.passwordInput)
	EditText passwordInput;
	@InjectView(R.id.datePickerButton)
	BootstrapButton datePickerButton;
	@InjectView(R.id.dateOfBirthText)
	TextView dateOfBirthText;
	@InjectView(R.id.medicalRecordNumberInput)
	EditText medicalRecordNumberInput;

	private Date dateOfBirthday = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);

		ButterKnife.inject(this);

		setupView();

	}

	private void setupView() {
		User patient = accountManager.loadUser(this);
		if (patient != null) {
			disableEditMode();
			accountManager.fetchPatient(this);
		} else {
			selectDoctorButton.setEnabled(false); // Patient has to register
													// first
			selectDoctorButton.setBootstrapType(BootstrapConstants.DEFAULT);
		}
	}

	private void disableEditMode() {
		nameInput.setEnabled(false);
		secondNameInput.setEnabled(false);
		passwordInput.setEnabled(false);
		datePickerButton.setEnabled(false);
		datePickerButton.setBootstrapType(BootstrapConstants.DEFAULT);
		medicalRecordNumberInput.setEnabled(false);
		connectCheckBox.setEnabled(false);
		registerButton.setEnabled(false);
		registerButton.setBootstrapType(BootstrapConstants.DEFAULT);
	}

	@OnClick(R.id.registerButton)
	public void register() {
		if (!validate()) {
			Toast.makeText(this, R.string.registration_invalid, Toast.LENGTH_LONG).show();
			return;
		}
		UserRegistration registration = new UserRegistration();
		registration.name = nameInput.getText().toString();
		registration.secondName = secondNameInput.getText().toString();
		registration.connect = connectCheckBox.isChecked() && connectCheckBox.isEnabled();
		registration.dateOfBirth = dateOfBirthday;
		registration.medicalRecordNumber = medicalRecordNumberInput.getText().toString();

		showProgressDialog();
		accountManager.registerPatient(registration, passwordInput.getText(), this);
	}

	private boolean validate() {
		return !UIUtils.isEmpty(nameInput) && !UIUtils.isEmpty(secondNameInput) && !UIUtils.isEmpty(passwordInput)
				&& !UIUtils.isEmpty(medicalRecordNumberInput) && dateOfBirthday != null;
	}

	@OnClick(R.id.selectDoctorsButton)
	public void selectDoctors() {
		Intent i = new Intent(this, DoctorListActivity.class);
		startActivity(i);
	}

	/**
	 * "Connect" feature not implemented yet.
	 */
	@OnCheckedChanged(R.id.connectCheckBox)
	public void onChecked() {
		if (connectCheckBox.isChecked()) {
			selectDoctorButton.setEnabled(false);
		} else {
			selectDoctorButton.setEnabled(true);
		}
	}

	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		switch (asyncTaskId) {
		case RegisterPatientTask.ASYNC_TASK_ID:
			disableEditMode();
			selectDoctorButton.setEnabled(true);
			selectDoctorButton.setBootstrapType(BootstrapConstants.PRIMARY);
			Toast.makeText(this, R.string.successfully_registered, Toast.LENGTH_LONG).show();
			break;
		case FetchPatientTask.ASYNC_TASK_ID:
			Patient patient = (Patient) result;
			disableEditMode();
			selectDoctorButton.setEnabled(true);
			selectDoctorButton.setBootstrapType(BootstrapConstants.PRIMARY);
			nameInput.setText(patient.name);
			secondNameInput.setText(patient.secondName);
			dateOfBirthText.setText(DateFormat.format("yyyy-MM-dd", patient.dateOfBirth).toString());
			medicalRecordNumberInput.setText(patient.medicalRecordNumber);
			break;
		default:
			throw new IllegalArgumentException("Unexpected asyncTaskId: " + asyncTaskId);
		}

	}

	@OnClick(R.id.datePickerButton)
	public void pickDate() {
		showDialog(DATE_PICKER_DIALOG_ID);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_DIALOG_ID:
			Calendar calendar = Calendar.getInstance();
			return new DatePickerDialog(this, datePickerListener, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, monthOfYear, dayOfMonth);
			dateOfBirthday = calendar.getTime();
			dateOfBirthText.setText(DateFormat.format("yyyy-MM-dd", dateOfBirthday).toString());
		};

	};
}
