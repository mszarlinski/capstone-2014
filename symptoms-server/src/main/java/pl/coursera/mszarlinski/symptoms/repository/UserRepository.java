package pl.coursera.mszarlinski.symptoms.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.coursera.mszarlinski.symptoms.domain.User;

/**
 * 
 * @author Maciej
 *
 */
@Transactional
@Repository
public class UserRepository implements IUserRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = em.createNamedQuery("user.findByUsername", User.class).setParameter("username", username)
					.getSingleResult();
			return user;
		} catch (NoResultException ex) {
			throw new UsernameNotFoundException("User not found for username: " + username, ex);
		}
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		List<User> users = em.createNamedQuery("user.findByUsername", User.class).setParameter("username", username)
				.getResultList();
		if (users.isEmpty()) {
			return null;
		} else if (users.size() == 1) {
			return users.get(0);
		} else {
			throw new NonUniqueResultException("Non unique user found for username: " + username);
		}

	}

	@Transactional
	public User save(User user) {
		return em.merge(user);
	}

}
