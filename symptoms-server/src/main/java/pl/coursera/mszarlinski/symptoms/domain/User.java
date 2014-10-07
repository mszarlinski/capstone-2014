package pl.coursera.mszarlinski.symptoms.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import pl.coursera.mszarlinski.symptoms.configuration.auth.EAuthority;
import pl.coursera.mszarlinski.symptoms.repository.UserRepository;

/**
 * User entity for purpose of Spring Security.
 * 
 * @see UserRepository
 * @author Maciej
 *
 */
@NamedQueries(@NamedQuery(name = "user.findByUsername", query = "from User u where u.username=:username"))
@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends Identifiable<Long> implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	public String name;
	@Column(nullable = false)
	public String secondName;
	@Column(nullable = false, unique = true)
	public String username;
	@Column(nullable = false)
	//TODO: binary encrypted password
	public String password;
	
	@Transient
	public CharSequence rawPassword;

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(getAuthority().name());
	}

	protected abstract EAuthority getAuthority();

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}
}
