package pl.coursera.mszarlinski.symptoms.activity;

import pl.coursera.mszarlinski.symptoms.async.FetchDoctorTask;
import pl.coursera.mszarlinski.symptoms.async.RegisterDoctorTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.constants.BootstrapConstants;
import pl.coursera.mszarlinski.symptoms.commons.utils.UIUtils;
import pl.coursera.mszarlinski.symptoms.manager.AccountManager;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;
import pl.mszarlinski.coursera.symptoms.R;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class AccountActivity extends AsyncActivity {

	private static final AccountManager accountManager = AccountManager.getInstance();

	@InjectView(R.id.registerButton)
	BootstrapButton registerButton;
	@InjectView(R.id.nameInput)
	EditText nameInput;
	@InjectView(R.id.secondNameInput)
	EditText secondNameInput;
	@InjectView(R.id.passwordInput)
	EditText passwordInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);

		ButterKnife.inject(this);

		setupView();
	}

	private void setupView() {
		User doctor = accountManager.loadUser(this);
		if (doctor != null) {
			disableEditMode();
			accountManager.fetchDoctor(this);
		}
	}

	private void disableEditMode() {
		nameInput.setEnabled(false);
		secondNameInput.setEnabled(false);
		passwordInput.setEnabled(false);
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

		showProgressDialog();
		accountManager.registerDoctor(registration, passwordInput.getText(), this);
	}

	private boolean validate() {
		return !UIUtils.isEmpty(nameInput) && !UIUtils.isEmpty(secondNameInput) && !UIUtils.isEmpty(passwordInput);
	}

	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		switch (asyncTaskId) {
		case RegisterDoctorTask.ASYNC_TASK_ID:
			disableEditMode();
			Toast.makeText(this, R.string.successfully_registered, Toast.LENGTH_LONG).show();
			break;
		case FetchDoctorTask.ASYNC_TASK_ID:
			Doctor doctor = (Doctor) result;
			disableEditMode();
			nameInput.setText(doctor.name);
			secondNameInput.setText(doctor.secondName);
			break;
		default:
			throw new IllegalArgumentException("Unexpected asyncTaskId: " + asyncTaskId);
		}
	}
}
