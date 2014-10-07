package pl.coursera.mszarlinski.symptoms.activity.checkIn;

import static pl.coursera.mszarlinski.symptoms.domain.EProblemWithEating.CANT_EAT;
import static pl.coursera.mszarlinski.symptoms.domain.EProblemWithEating.NONE;
import static pl.coursera.mszarlinski.symptoms.domain.EProblemWithEating.SOME;
import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.commons.constants.BootstrapConstants;
import pl.coursera.mszarlinski.symptoms.domain.EProblemWithEating;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;
import pl.coursera.mszarlinski.symptoms.rest.resource.Answer;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class DoesPainStopFromEatingActivity extends Activity {

	private int selectedButton = -1;

	@InjectView(R.id.noneButton) BootstrapButton noneButton;
	@InjectView(R.id.someButton) BootstrapButton someButton;
	@InjectView(R.id.cantEatButton) BootstrapButton cantEatButton;
	@InjectView(R.id.nextButton) BootstrapButton nextButton;
	
	private CheckIn checkIn;

	private final EProblemWithEating[] ANSWERS = { NONE, SOME, CANT_EAT };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.does_pain_stop_from_eating);
		
		ButterKnife.inject(this);

		checkIn = (CheckIn) getIntent().getExtras().getSerializable(Constants.CHECK_IN_EXTRA);
	}
	
	@OnClick(R.id.noneButton)
	public void clickNone() {
		if (selectedButton == 0) {
			noneButton.setBootstrapType(BootstrapConstants.DEFAULT);
			selectedButton = -1;
		} else {
			resetButtons();
			noneButton.setBootstrapType(BootstrapConstants.SUCCESS);
			selectedButton = 0;
		}
	}
	
	@OnClick(R.id.someButton)
	public void clickSome() {
		if (selectedButton == 1) {
			someButton.setBootstrapType(BootstrapConstants.DEFAULT);
			selectedButton = -1;
		} else {
			resetButtons();
			someButton.setBootstrapType(BootstrapConstants.WARNING);
			selectedButton = 1;
		}
	}

	@OnClick(R.id.cantEatButton)
	public void clickCantEat() {
		if (selectedButton == 2) {
			cantEatButton.setBootstrapType(BootstrapConstants.DEFAULT);
			selectedButton = -1;
		} else {
			resetButtons();
			cantEatButton.setBootstrapType(BootstrapConstants.DANGER);
			selectedButton = 2;
		}
	}
	
	@OnClick(R.id.nextButton)
	public void clickNext() {
		if (selectedButton != -1) {
			checkIn.addAnswer(new Answer().forQuestion(EQuestion.DOES_PAIN_STOP_FROM_EATING).withContent(
					ANSWERS[selectedButton].name()));

			Intent i = new Intent(this, DidYouTakeMedicationsActivity.class);
			i.putExtra(Constants.CHECK_IN_EXTRA, checkIn);
			startActivity(i);
		} else {
			Toast.makeText(this, R.string.select_one_option, Toast.LENGTH_LONG)
					.show();
		}
	}


	private void resetButtons() {
		noneButton.setBootstrapType(BootstrapConstants.DEFAULT);
		someButton.setBootstrapType(BootstrapConstants.DEFAULT);
		cantEatButton.setBootstrapType(BootstrapConstants.DEFAULT);
	}

	

}
