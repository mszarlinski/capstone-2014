package pl.coursera.mszarlinski.symptoms.async;

import org.springframework.web.client.RestTemplate;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.SSLRestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.async.SymptomsAsyncTask;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.manager.NotificationManager;
import pl.coursera.mszarlinski.symptoms.persistence.SQLiteDAO;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;

/**
 * 
 * @author Maciej
 *
 */
public class RegisterDoctorTask extends SymptomsAsyncTask<UserRegistration, Void, String> {

	public static final int ASYNC_TASK_ID = 1;
	
	private static final NotificationManager notificationManager = NotificationManager.getInstance();

	public RegisterDoctorTask(AsyncActivity context) {
		super(context);
	}

	@Override
	protected String doInBackground(UserRegistration... params) {
		try {
			RestTemplate restTemplate = new SSLRestTemplate(
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));

			String username = restTemplate.postForObject(Constants.DOCTOR_URL, params[0], String.class);
			final String encryptedPass = params[0].password;

			if (encryptedPass == null) {
				throw new IllegalStateException("Failed to register doctor");
			}
			
			// store user in db
			new SQLiteDAO(context).saveUser(username, encryptedPass);
			
			// turn on job that checks patients health status
			notificationManager.startHealthStatusCheckingJob(context); // TODO: application context?
			
			return username;
		} catch (Exception e) {
			setException(e);
		}

		return null;
	}

	@Override
	protected int getTaskId() {
		return ASYNC_TASK_ID;
	}

}