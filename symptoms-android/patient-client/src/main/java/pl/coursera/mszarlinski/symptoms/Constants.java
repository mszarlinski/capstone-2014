package pl.coursera.mszarlinski.symptoms;

import pl.coursera.mszarlinski.symptoms.commons.config.Config;


/**
 * 
 * @author Maciej
 *
 */
public interface Constants {
	
	int MIN_CHECK_IN_ALARMS = 4;
	
	String CHECK_IN_EXTRA = "checkInExtra";
	String EXIT_APP_EXTRA = "exitAppExtra";
	String SELECTED_DOCTOR_EXTRA = "selectedDoctorExtra";
	String DOCTORS_LIST_EXTRA = "doctorsListExtra";

	String CLIENT_ID = "patientClient";

	// FIXME:
	String GET_PATIENT_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient";
	String SEND_CHECK_IN_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/checkIn";
	String GET_CHECK_IN_TEMPLATE_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/checkIn/template";
	String REGISTER_PATIENT_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient?connect=%b";
	String GET_DOCTORS_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/doctor/%s";
	String SEND_ASSIGNMENTS_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient/doctors";
	String FETCH_ASSIGNMENTS_URL = Config.SERVER_LOCAL + "/symptoms-server/rest/patient/doctors";
}
