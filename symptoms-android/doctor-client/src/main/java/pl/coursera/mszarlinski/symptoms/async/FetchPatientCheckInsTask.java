package pl.coursera.mszarlinski.symptoms.async;

import java.util.Arrays;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.async.SymptomsAsyncTask;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;

/**
 * 
 * @author Maciej
 *
 */
public class FetchPatientCheckInsTask extends SymptomsAsyncTask<Long, Void, List<CheckIn>> {

	public final static int ASYNC_TASK_ID = 6;

	private final User user;

	public FetchPatientCheckInsTask(User user, AsyncActivity context) {
		super(context);
		this.user = user;
	}

	@Override
	protected List<CheckIn> doInBackground(Long... params) {
		try {
			OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			final String url = String.format(Constants.GET_PATIENT_CHECKINS_URL, params[0]);
			return Arrays.asList(restTemplate.getForObject(url, CheckIn[].class));
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