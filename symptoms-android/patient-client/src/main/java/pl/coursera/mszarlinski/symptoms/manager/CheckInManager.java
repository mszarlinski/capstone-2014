package pl.coursera.mszarlinski.symptoms.manager;

import java.util.Date;

import pl.coursera.mszarlinski.symptoms.async.GetCheckInTemplateAndNotifyTask;
import pl.coursera.mszarlinski.symptoms.async.SendCheckInTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import android.content.Context;

/**
 * 
 * @author Maciej
 *
 */
public class CheckInManager {
	
	private static final AccountManager accountManager = AccountManager.getInstance();
	
	/**
	 * Initialize getting check-in template from server and register a callback.
	 * 
	 * @param patientId
	 * @param activity
	 */
	public void startCheckInWizard(Context context) {
		User user = accountManager.loadUser(context);
		new GetCheckInTemplateAndNotifyTask(user, context).execute();
	}

	/**
	 * Send check-in data to server.
	 * 
	 * @param checkIn
	 */
	public void sendCheckIn(CheckIn checkIn, AsyncActivity activity) {
		checkIn.creationDate = new Date();
		User user = accountManager.loadUser(activity);
		new SendCheckInTask(user, activity).execute(checkIn);
	}
}
