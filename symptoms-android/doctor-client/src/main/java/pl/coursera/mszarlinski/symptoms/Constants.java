package pl.coursera.mszarlinski.symptoms;

import pl.coursera.mszarlinski.symptoms.commons.config.Config;

/**
 * 
 * @author Maciej
 *
 */
public interface Constants {
	String CLIENT_ID = "doctorClient";
	
	String PATIENT_ID_EXTRA = "patientIdExtra";

	// FIXME:
	String DOCTOR_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/doctor";
	String GET_PATIENTS_IN_DANGER_NUMBER_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient/inDanger";
	String GET_PATIENTS_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/doctor/patients";
	String GET_MEDICATIONS_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient/%d/medications";
	String GET_PATIENT_CHECKINS_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient/%s/checkIns";

}
