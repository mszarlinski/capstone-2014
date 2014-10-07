package pl.coursera.mszarlinski.symptoms.broadcastReceiver;

import pl.coursera.mszarlinski.symptoms.manager.CheckInManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Maciej
 *
 */
public class LaunchCheckInWizardAlarmReceiver extends BroadcastReceiver {

	private final static CheckInManager checkInManager = new CheckInManager();

	@Override
	public void onReceive(Context context, Intent intent) {
		checkInManager.startCheckInWizard(context);
	}
}
