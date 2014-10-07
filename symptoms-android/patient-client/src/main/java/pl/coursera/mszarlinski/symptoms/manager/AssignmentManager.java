package pl.coursera.mszarlinski.symptoms.manager;

import pl.coursera.mszarlinski.symptoms.async.FetchAssignmentsTask;
import pl.coursera.mszarlinski.symptoms.async.QueryForDoctorsTask;
import pl.coursera.mszarlinski.symptoms.async.SendAssignmentsTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;

/**
 * 
 * @author Maciej
 *
 */
public class AssignmentManager {

	private final static AccountManager accountManager = AccountManager.getInstance();

	public void sendAssignments(long[] doctorIds, AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new SendAssignmentsTask(user, context).execute(doctorIds);
	}

	public void queryForDoctors(String query, AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new QueryForDoctorsTask(user, context).execute(query);
	}

	public void fetchAssignments(AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new FetchAssignmentsTask(user, context).execute();
	}

	// SINGLETON
	private static final AssignmentManager INSTANCE = new AssignmentManager();

	private AssignmentManager() {
	}

	public static AssignmentManager getInstance() {
		return INSTANCE;
	}
}
