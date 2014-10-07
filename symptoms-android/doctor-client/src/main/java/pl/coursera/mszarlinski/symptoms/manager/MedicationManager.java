package pl.coursera.mszarlinski.symptoms.manager;

import java.util.List;

import pl.coursera.mszarlinski.symptoms.async.FetchMedicationsTask;
import pl.coursera.mszarlinski.symptoms.async.SaveMedicationsTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;

/**
 * 
 * @author Maciej
 *
 */
public class MedicationManager {
	private static final AccountManager accountManager = AccountManager.getInstance();

	/**
	 * Load medications configuration for given patient from server.
	 */
	public void fetchMedications(long patientId, AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new FetchMedicationsTask(user, context).execute(patientId);
	}

	/**
	 * Update medications configuration for given patient.
	 */
	public void saveMedications(List<Medication> medications, long patientId, AsyncActivity context) {
		User user = accountManager.loadUser(context);
		new SaveMedicationsTask(medications, patientId, user, context).execute();
	}

	// SINGLETON
	private static final MedicationManager INSTANCE = new MedicationManager();

	private MedicationManager() {
	}

	public static MedicationManager getInstance() {
		return INSTANCE;
	}

}
