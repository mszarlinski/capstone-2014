package pl.coursera.mszarlinski.symptoms.configuration.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Maciej
 *
 */
public enum EAuthority {
	PATIENT, DOCTOR;

	public boolean in(Collection<? extends GrantedAuthority> authorities) {
		for (GrantedAuthority ga : authorities) {
			if (equals(ga.getAuthority())) {
				return true;
			}
		}
		return false;
	}
}
