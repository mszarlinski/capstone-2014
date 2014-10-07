package pl.coursera.mszarlinski.symptoms.async;

import org.springframework.web.client.RestTemplate;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.SSLRestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.manager.CheckInManager;
import pl.coursera.mszarlinski.symptoms.persistence.SQLiteDAO;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author Maciej
 *
 */
public class RegisterPatientTask extends AsyncTask<UserRegistration, Void, String> {

	public final static int ASYNC_TASK_ID = 2;

	private final AsyncActivity context;

	public RegisterPatientTask(AsyncActivity context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(UserRegistration... params) {
		try {
			RestTemplate restTemplate = new SSLRestTemplate(UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			String username = restTemplate.postForObject(Constants.REGISTER_PATIENT_URL, params[0],
					String.class);
			final String encryptedPass = params[0].password;
			
			if (encryptedPass == null) {
				throw new IllegalStateException("Failed to register patient");
			}
			
			// store user in db
			new SQLiteDAO(context).saveUser(username, encryptedPass);

			return username;
		} catch (Exception e) {
			Log.e(CheckInManager.class.getSimpleName(), e.getMessage(), e);
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		context.asyncJobDoneCallback(result, ASYNC_TASK_ID);
	}
}