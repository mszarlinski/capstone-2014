package pl.coursera.mszarlinski.symptoms.async;

import java.util.Arrays;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;
import android.os.AsyncTask;

/**
 * 
 * @author Maciej
 *
 */
public class FetchPatientsTask extends AsyncTask<Void, Void, List<Patient>> {

	public final static int ASYNC_TASK_ID = 3;

	private final AsyncActivity context;
	private final User user;

	public FetchPatientsTask(User user, AsyncActivity context) {
		this.context = context;
		this.user = user;
	}

	@Override
	protected List<Patient> doInBackground(Void... params) {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
				UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
		Patient[] patients = restTemplate.getForObject(Constants.GET_PATIENTS_URL, Patient[].class);
		return Arrays.asList(patients);
	}

	@Override
	protected void onPostExecute(List<Patient> result) {
		context.asyncJobDoneCallback(result, ASYNC_TASK_ID);
	}
}