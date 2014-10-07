package pl.coursera.mszarlinski.symptoms.manager;

import pl.coursera.mszarlinski.symptoms.async.FetchPatientCheckInsTask;
import pl.coursera.mszarlinski.symptoms.async.FetchPatientsTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;

/**
 * 
 * @author Maciej
 *
 */
public class PatientManager {
	private static final AccountManager accountManager = AccountManager.getInstance();
	
	public void fetchPatients(AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new FetchPatientsTask(user, context).execute();
	}
	
	public void fetchPatientCheckIns(long patientId, AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new FetchPatientCheckInsTask(user, context).execute(patientId);
	}

	// SINGLETON
	private static final PatientManager INSTANCE = new PatientManager();

	private PatientManager() {
	}

	public static PatientManager getInstance() {
		return INSTANCE;
	}
}
