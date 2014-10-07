package pl.coursera.mszarlinski.symptoms.async;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.async.SymptomsAsyncTask;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;

/**
 * 
 * @author Maciej
 *
 */
public class FetchPatientTask extends SymptomsAsyncTask<Void, Void, Patient> {

	public final static int ASYNC_TASK_ID = 3;

	private final User user;

	public FetchPatientTask(User user, AsyncActivity context) {
		super(context);
		this.user = user;
	}

	@Override
	protected Patient doInBackground(Void... params) {
		try {
			OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			final String url = Constants.GET_PATIENT_URL;
			return restTemplate.getForObject(url, Patient.class);
		} catch (Exception ex) {
			setException(ex);
		}

		return null;
	}

	@Override
	protected void onPostExecute(Patient result) {
		context.asyncJobDoneCallback(result, ASYNC_TASK_ID);
	}

	@Override
	protected int getTaskId() {
		return ASYNC_TASK_ID;
	}
}