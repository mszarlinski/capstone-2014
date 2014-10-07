package pl.coursera.mszarlinski.symptoms.async;

import java.util.List;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.commons.OAuth2RestTemplate;
import pl.coursera.mszarlinski.symptoms.commons.UnsafeHttpsClient;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.commons.async.SymptomsAsyncTask;
import pl.coursera.mszarlinski.symptoms.commons.config.Config;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;

/**
 * 
 * @author Maciej
 *
 */
public class SaveMedicationsTask extends SymptomsAsyncTask<Void, Void, Void> {

	public final static int ASYNC_TASK_ID = 5;

	private final User user;
	private final List<Medication> medications;
	private final long patientId;

	public SaveMedicationsTask(List<Medication> medications, long patientId, User user, AsyncActivity context) {
		super(context);
		this.user = user;
		this.medications = medications;
		this.patientId = patientId;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(user.username, user.password, Constants.CLIENT_ID,
					UnsafeHttpsClient.createUnsafeClient(Config.SERVER_LOCAL_PORT));
			final String url = String.format(Constants.GET_MEDICATIONS_URL, patientId);
			boolean success = restTemplate.postForObject(url, medications, Boolean.class);
			if (!success) {
				throw new IllegalStateException("Failed to save medications");
			}
			return null;
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