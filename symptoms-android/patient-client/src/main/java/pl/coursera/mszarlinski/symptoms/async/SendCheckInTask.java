package pl.coursera.mszarlinski.symptoms.async;

import org.springframework.web.client.RestTemplate;

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
public class SendCheckInTask extends SymptomsAsyncTask<CheckIn, Void, Void> {

	public final static int ASYNC_TASK_ID = 1;

	private final User user;

	public SendCheckInTask(User user, AsyncActivity context) {
		super(context);
		this.user = user;
	}

	@Override
	protected Void doInBackground(CheckIn... params) {
		try {
			RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			final String url = Constants.SEND_CHECK_IN_URL;
			String resourceUrl = restTemplate.postForObject(url, params[0], String.class);
			if (resourceUrl == null) {
				throw new IllegalStateException("Empty response after posting check-in");
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