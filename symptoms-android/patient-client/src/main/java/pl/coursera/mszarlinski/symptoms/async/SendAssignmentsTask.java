package pl.coursera.mszarlinski.symptoms.async;

import org.springframework.web.client.RestTemplate;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.async.SymptomsAsyncTask;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;

/**
 * 
 * @author Maciej
 *
 */
public class SendAssignmentsTask extends SymptomsAsyncTask<long[], Void, Void> {

	public final static int ASYNC_TASK_ID = 5;

	private final User user;

	public SendAssignmentsTask(User user, AsyncActivity context) {
		super(context);
		this.user = user;
	}

	@Override
	protected Void doInBackground(long[]... params) {
		try {
			RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			final String url = Constants.SEND_ASSIGNMENTS_URL;
			boolean result = restTemplate.postForObject(url, params[0], Boolean.class);
			if (!result) {
				throw new IllegalStateException("Failed to send assignments");
			}
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
