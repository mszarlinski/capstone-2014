package pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;

/**
 * 
 * @author Maciej
 *
 */
public class DoctorItem implements Serializable {

	private static final long serialVersionUID = 8846489756642770069L;

	public long doctorId;
	public String label;

	private DoctorItem(Doctor doctor) {
		label = String.format("%s %s", doctor.name, doctor.secondName);
		this.doctorId = doctor.id;
	}

	public static List<DoctorItem> convert(List<Doctor> doctors) {
		List<DoctorItem> doctorItems = new ArrayList<DoctorItem>();
		for (Doctor doctor : doctors) {
			DoctorItem item = new DoctorItem(doctor);
			doctorItems.add(item);
		}
		return doctorItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (doctorId ^ (doctorId >>> 32));
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
		DoctorItem other = (DoctorItem) obj;
		if (doctorId != other.doctorId)
			return false;
		return true;
	}

}
