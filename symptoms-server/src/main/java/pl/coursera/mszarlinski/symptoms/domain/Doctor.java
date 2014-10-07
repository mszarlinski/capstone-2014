package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

import pl.coursera.mszarlinski.symptoms.configuration.auth.EAuthority;

/**
 * 
 * @author Maciej
 *
 */
@Entity
public class Doctor extends User {

	private static final long serialVersionUID = 1L;

	@Transient
	@Override
	public EAuthority getAuthority() {
		return EAuthority.DOCTOR;
	}
}
