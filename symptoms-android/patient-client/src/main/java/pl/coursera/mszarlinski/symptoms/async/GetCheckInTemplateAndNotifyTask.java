package pl.coursera.mszarlinski.symptoms.async;

import org.springframework.web.client.RestTemplate;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.activity.checkIn.HowBadIsMouthPainActivity;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * 
 * @author Maciej
 *
 */
public class GetCheckInTemplateAndNotifyTask extends AsyncTask<Void, Void, CheckIn> {

	private static final int MY_NOTIFICATION_ID = 1;

	// notification sound and vibration
	private static Uri RINGTONE_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

	private static long[] VIBRATE_PATTERN = { 0, 200, 200, 300 };

	private final Context context;
	private final User user;

	public GetCheckInTemplateAndNotifyTask(User user, Context context) {
		this.context = context;
		this.user = user;
	}

	@Override
	protected CheckIn doInBackground(Void... params) {

		RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
				UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
		return restTemplate.getForObject(Constants.GET_CHECK_IN_TEMPLATE_URL, CheckIn.class);

	}

	@Override
	protected void onPostExecute(CheckIn result) {
		String ticker = "It's check-in time!";
		String title = "Check-in";
		String content = "Tap to enter a check-in wizard";

		Intent notificationIntent = new Intent(context, HowBadIsMouthPainActivity.class);
		notificationIntent.putExtra(Constants.CHECK_IN_EXTRA, result);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

		Notification.Builder notificationBuilder = new Notification.Builder(context).setTicker(ticker)
				.setSmallIcon(android.R.drawable.stat_sys_warning).setAutoCancel(true).setContentTitle(title)
				.setContentText(content).setSound(RINGTONE_URI).setVibrate(VIBRATE_PATTERN).setContentIntent(intent);

		// pass the Notification to the NotificationManager
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());

	}

}
