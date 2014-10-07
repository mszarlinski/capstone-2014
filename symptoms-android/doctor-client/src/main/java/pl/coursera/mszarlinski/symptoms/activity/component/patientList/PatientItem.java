package pl.coursera.mszarlinski.symptoms.activity.component.patientList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;

/**
 * 
 * @author Maciej
 *
 */
public class PatientItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String PATIENT_IN_DANGER_MARK = " !";

	public long patientId;
	public String label;

	private PatientItem(Patient patient) {
		label = String.format("%s %s", patient.name, 
				patient.inDanger ? patient.secondName + PATIENT_IN_DANGER_MARK : patient.secondName);
		this.patientId = patient.id;
	}

	public static List<PatientItem> convert(List<Patient> patients) {
		List<PatientItem> doctorItems = new ArrayList<PatientItem>();
		for (Patient patient : patients) {
			PatientItem item = new PatientItem(patient);
			doctorItems.add(item);
		}
		return doctorItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (patientId ^ (patientId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientItem other = (PatientItem) obj;
		if (patientId != other.patientId)
			return false;
		return true;
	}

}
