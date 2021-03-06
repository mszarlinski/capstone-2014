package pl.coursera.mszarlinski.symptoms.activity;

import pl.coursera.mszarlinski.symptoms.manager.AccountManager;
import pl.coursera.mszarlinski.symptoms.manager.PatientManager;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.mszarlinski.coursera.symptoms.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 
 * @author Maciej
 *
 */
public class MainActivity extends Activity {
	private static final AccountManager accountManager = AccountManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ButterKnife.inject(this);
	}

	@OnClick(R.id.accountButton)
	public void manageAccount() {
		Intent i = new Intent(this, AccountActivity.class);
		startActivity(i);
	}

	@OnClick(R.id.patientsButton)
	public void changeSettings() {
		User user = accountManager.loadUser(this);
		if (user != null) {
			Intent i = new Intent(MainActivity.this, MyPatientsActivity.class);
			startActivity(i);
		} else {
			Toast.makeText(this, R.string.you_must_register_first, Toast.LENGTH_LONG).show();
		}
	}

}
