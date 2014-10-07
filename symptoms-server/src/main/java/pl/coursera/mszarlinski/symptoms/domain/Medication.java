package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 
 * @author Maciej
 *
 */
@Entity
public class Medication extends Identifiable<Integer> {

	public String name;
	public String description;

	@Transient
	public boolean assigned;

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Medication)) {
			return false;
		}
		Medication other = (Medication) obj;

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
