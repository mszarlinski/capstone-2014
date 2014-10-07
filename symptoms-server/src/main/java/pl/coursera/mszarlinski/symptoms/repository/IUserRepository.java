package pl.coursera.mszarlinski.symptoms.repository;

import org.springframework.security.core.userdetails.UserDetailsService;

import pl.coursera.mszarlinski.symptoms.domain.User;

/**
 * Interfaces created only for purpose of autowiring <code>UserRepository</code>
 * 
 * @author Maciej
 *
 */
public interface IUserRepository extends UserDetailsService {
	User save(User user);
	User findByUsername(String username);

}
