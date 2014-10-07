package pl.coursera.mszarlinski.symptoms.broadcastReceiver;

import pl.coursera.mszarlinski.symptoms.manager.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Maciej
 *
 */
public class CheckPatientsHealthStatusAlarmReceiver extends BroadcastReceiver {

	private final static NotificationManager notificationManager = NotificationManager.getInstance();

	@Override
	public void onReceive(Context context, Intent intent) {
		notificationManager.checkPatientsHealthStatus(context);
	}
}
