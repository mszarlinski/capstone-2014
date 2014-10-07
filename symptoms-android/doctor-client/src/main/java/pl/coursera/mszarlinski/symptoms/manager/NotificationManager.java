package pl.coursera.mszarlinski.symptoms.manager;

import pl.coursera.mszarlinski.symptoms.async.CheckPatientsHealthStatusAndNotifyTask;
import pl.coursera.mszarlinski.symptoms.broadcastReceiver.CheckPatientsHealthStatusAlarmReceiver;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * 
 * @author Maciej
 *
 */
public class NotificationManager {
	private static final AccountManager accountManager = AccountManager.getInstance();
	
	private static final long INITIAL_ALARM_DELAY = 30 * 1000L;

	public void startHealthStatusCheckingJob(Context context) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		final Intent receiverIntent = new Intent(context, CheckPatientsHealthStatusAlarmReceiver.class);
		final PendingIntent receiverPendingIntent = PendingIntent.getBroadcast(context, 0, receiverIntent, 0);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY, 
				//FIXME: AlarmManager.INTERVAL_HOUR,
				AlarmManager.INTERVAL_FIFTEEN_MINUTES,
				receiverPendingIntent);
	}

	public void checkPatientsHealthStatus(Context context) {
		User user = accountManager.loadUser(context);
		new CheckPatientsHealthStatusAndNotifyTask(user, context).execute();
	}

	// SINGLETON
	private static final NotificationManager INSTANCE = new NotificationManager();

	private NotificationManager() {
	}

	public static NotificationManager getInstance() {
		return INSTANCE;
	}

}
