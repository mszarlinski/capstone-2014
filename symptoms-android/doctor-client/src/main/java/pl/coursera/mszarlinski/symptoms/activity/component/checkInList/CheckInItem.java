package pl.coursera.mszarlinski.symptoms.activity.component.checkInList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.rest.enumerated.EPainIntensity;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EProblemWithEating;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;
import pl.coursera.mszarlinski.symptoms.rest.resource.Answer;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;

/**
 * 
 * @author Maciej
 *
 */
public class CheckInItem {
	public enum ItemStyle {
		OK, MODERATE, DANGER;
	}
	
	public EPainIntensity painIntensity;
	public EProblemWithEating problemWithEating;
	public Date checkInDate;
	public ItemStyle itemStyle;

	private CheckInItem(CheckIn checkIn) {
		checkInDate = checkIn.creationDate;
		for (Answer answer : checkIn.answers) {
			if (answer.question == EQuestion.HOW_BAD_IS_MOUTH_PAIN) {
				painIntensity = EPainIntensity.valueOf(answer.content);
				continue;
			}
			if (answer.question == EQuestion.DOES_PAIN_STOP_FROM_EATING) {
				problemWithEating = EProblemWithEating.valueOf(answer.content);
				continue;
			}
		}

		itemStyle = computeStyle();
	}

	private ItemStyle computeStyle() {
		// TODO: this is not a good practice to write code based on enum
		// ordinal.
		final int painOrdinal = painIntensity.ordinal();
		final int problemOrdinal = problemWithEating.ordinal();
		final int maxOrdinal = Math.max(painOrdinal, problemOrdinal);
		return ItemStyle.values()[maxOrdinal];
	}

	public static List<CheckInItem> convert(List<CheckIn> checkIns) {
		List<CheckInItem> checkInItems = new ArrayList<CheckInItem>();
		for (CheckIn checkIn : checkIns) {
			CheckInItem item = new CheckInItem(checkIn);
			checkInItems.add(item);
		}
		return checkInItems;
	}

}
