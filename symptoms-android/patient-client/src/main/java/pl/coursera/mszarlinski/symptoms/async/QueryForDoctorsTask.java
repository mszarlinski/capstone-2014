package pl.coursera.mszarlinski.symptoms.async;

import java.util.Arrays;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;
import android.os.AsyncTask;

/**
 * 
 * @author Maciej
 *
 */
public class QueryForDoctorsTask extends AsyncTask<String, Void, List<Doctor>> {

	public final static int ASYNC_TASK_ID = 4;

	private final AsyncActivity context;
	private final User user;

	public QueryForDoctorsTask(User user, AsyncActivity context) {
		this.context = context;
		this.user = user;
	}

	@Override
	protected List<Doctor> doInBackground(String... params) {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password,
				Constants.CLIENT_ID, UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
		final String url = String.format(Constants.GET_DOCTORS_URL, params[0]);
		Doctor[] doctors = restTemplate.getForObject(url, Doctor[].class);
		return Arrays.asList(doctors);
	}

	@Override
	protected void onPostExecute(List<Doctor> result) {
		context.asyncJobDoneCallback(result, ASYNC_TASK_ID);
	}
}