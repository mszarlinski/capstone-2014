package pl.coursera.mszarlinski.symptoms.activity.checkIn;

import static pl.coursera.mszarlinski.symptoms.domain.EPainIntensity.MODERATE;
import static pl.coursera.mszarlinski.symptoms.domain.EPainIntensity.SEVERE;
import static pl.coursera.mszarlinski.symptoms.domain.EPainIntensity.WELL_CONTROLLED;
import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.commons.constants.BootstrapConstants;
import pl.coursera.mszarlinski.symptoms.domain.EPainIntensity;
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
public class HowBadIsMouthPainActivity extends Activity {

	private int selectedButton = -1;
	
	private CheckIn checkIn;

	@InjectView(R.id.controlledButton)
	BootstrapButton controlledButton;
	@InjectView(R.id.moderateButton)
	BootstrapButton moderateButton;
	@InjectView(R.id.severeButton)
	BootstrapButton severeButton;
	@InjectView(R.id.nextButton)
	BootstrapButton nextButton;

	private final EPainIntensity[] ANSWERS = { WELL_CONTROLLED, MODERATE, SEVERE };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_bad_is_mouth_pain);
		
		ButterKnife.inject(this);

		checkIn = (CheckIn) getIntent().getExtras().getSerializable(Constants.CHECK_IN_EXTRA);
	}

	@OnClick(R.id.controlledButton)
	public void clickControlled() {
		if (selectedButton == 0) {
			controlledButton.setBootstrapType(BootstrapConstants.DEFAULT);
			selectedButton = -1;
		} else {
			resetButtons();
			controlledButton.setBootstrapType(BootstrapConstants.SUCCESS);
			selectedButton = 0;
		}
	}

	@OnClick(R.id.moderateButton)
	public void clickModerate() {
		if (selectedButton == 1) {
			moderateButton.setBootstrapType(BootstrapConstants.DEFAULT);
			selectedButton = -1;
		} else {
			resetButtons();
			moderateButton.setBootstrapType(BootstrapConstants.WARNING);
			selectedButton = 1;
		}
	}

	@OnClick(R.id.severeButton)
	public void clickSevere() {
		if (selectedButton == 2) {
			severeButton.setBootstrapType(BootstrapConstants.DEFAULT);
			selectedButton = -1;
		} else {
			resetButtons();
			severeButton.setBootstrapType(BootstrapConstants.DANGER);
			selectedButton = 2;
		}
	}

	@OnClick(R.id.nextButton)
	public void clickNext() {
		if (selectedButton != -1) {
			checkIn.addAnswer(new Answer().forQuestion(EQuestion.HOW_BAD_IS_MOUTH_PAIN)
					.withContent(ANSWERS[selectedButton].name()));

			Intent i = new Intent(this, DoesPainStopFromEatingActivity.class);
			i.putExtra(Constants.CHECK_IN_EXTRA, checkIn);
			startActivity(i);
		} else {
			Toast.makeText(this, R.string.select_one_option, Toast.LENGTH_LONG).show();
		}
	}

	private void resetButtons() {
		controlledButton.setBootstrapType(BootstrapConstants.DEFAULT);
		moderateButton.setBootstrapType(BootstrapConstants.DEFAULT);
		severeButton.setBootstrapType(BootstrapConstants.DEFAULT);
	}
}
