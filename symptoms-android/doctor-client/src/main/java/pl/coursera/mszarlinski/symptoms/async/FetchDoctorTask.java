package pl.coursera.mszarlinski.symptoms.async;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.async.SymptomsAsyncTask;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;

/**
 * 
 * @author Maciej
 *
 */
public class FetchDoctorTask extends SymptomsAsyncTask<Void, Void, Doctor> {

	public final static int ASYNC_TASK_ID = 2;

	private final User user;

	public FetchDoctorTask(User user, AsyncActivity context) {
		super(context);
		this.user = user;
	}

	@Override
	protected Doctor doInBackground(Void... params) {
		try {
			OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			final String url = Constants.DOCTOR_URL;
			return restTemplate.getForObject(url, Doctor.class);
		} catch (Exception ex) {
			setException(ex);
		}
		return null;
	}

	@Override
	protected int getTaskId() {
		return ASYNC_TASK_ID;
	}
}