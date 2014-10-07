package pl.coursera.mszarlinski.symptoms.manager;

import java.util.Date;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.broadcastReceiver.LaunchCheckInWizardAlarmReceiver;
import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;
import pl.coursera.mszarlinski.symptoms.persistence.SQLiteDAO;
import pl.coursera.mszarlinski.symptoms.persistence.entity.Alarm;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Maciej
 *
 */
public class NotificationManager {

	public void rescheduleAlarms(List<Alarm> alarmsToAdd, List<Alarm> alarmsToRemove, Context context) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		for (Alarm alarm : alarmsToRemove) {
			if (alarm.id > 0) {
				unregisterAlarm(alarm.id, alarmManager, context);
			}
		}
		for (Alarm alarm : alarmsToAdd) {
			registerAlarm(alarm.time, alarmManager, context);
		}
	}

	private void registerAlarm(TimeOfDay time, AlarmManager alarmManager, Context context) {
		final SQLiteDAO dao = new SQLiteDAO(context);
		Alarm alarm = dao.findAlarmByTime(time);
		if (alarm == null) {
			alarm = dao.createAlarm(time);
			final Intent receiverIntent = new Intent(context, LaunchCheckInWizardAlarmReceiver.class);
			final PendingIntent receiverPendingIntent = PendingIntent
					.getBroadcast(context, alarm.id, receiverIntent, 0);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.supplyTime(new Date()).getTime(),
					AlarmManager.INTERVAL_DAY, receiverPendingIntent);
		}
	}

	private void unregisterAlarm(int alarmId, AlarmManager alarmManager, Context context) {

		final Intent receiverIntent = new Intent(context, LaunchCheckInWizardAlarmReceiver.class);
		final PendingIntent receiverPendingIntent = PendingIntent.getBroadcast(context, alarmId, receiverIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		receiverPendingIntent.cancel();
		alarmManager.cancel(receiverPendingIntent);

		new SQLiteDAO(context).deleteAlarm(alarmId);

	}

	// SINGLETON
	private static final NotificationManager INSTANCE = new NotificationManager();

	private NotificationManager() {
	}

	public static NotificationManager getInstance() {
		return INSTANCE;
	}

}
