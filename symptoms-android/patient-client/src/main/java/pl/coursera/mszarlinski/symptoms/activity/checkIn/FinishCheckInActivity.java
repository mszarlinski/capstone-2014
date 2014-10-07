package pl.coursera.mszarlinski.symptoms.activity.checkIn;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.app.MainActivity;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.manager.CheckInManager;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 
 * @author Maciej
 *
 */
public class FinishCheckInActivity extends AsyncActivity {

	private static final CheckInManager checkInManager = new CheckInManager();

	private CheckIn checkIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_check_in);

		ButterKnife.inject(this);

		checkIn = (CheckIn) getIntent().getExtras().getSerializable(Constants.CHECK_IN_EXTRA);
	}

	@OnClick(R.id.completeButton)
	public void complete() {
		showProgressDialog();
		checkInManager.sendCheckIn(checkIn, this);
	}
	
	@OnClick(R.id.cancelCheckinButton)
	public void cancel() {
		exitWizard();
	}

	@Override
	protected void handleResult(Object result, int asyncTaskId) {
		exitWizard();
	}

	private void exitWizard() {
		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
		moveTaskToBack(true);
	}
}
