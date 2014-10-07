package pl.coursera.mszarlinski.symptoms.rest;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import pl.coursera.mszarlinski.symptoms.domain.User;
import pl.coursera.mszarlinski.symptoms.rest.exception.InvalidRequestException;

/**
 * 
 * @author Maciej
 *
 */
public final class RestUtil {
	/**
	 * Check if object is empty.
	 * 
	 * @param object
	 * @throws InvalidRequestException
	 */
	public static void notEmpty(Object object) throws InvalidRequestException {
		boolean empty = false;
		if (object instanceof String) {
			empty = StringUtils.isEmpty((String) object);
		} else if (object instanceof Collection<?>) {
			empty = CollectionUtils.isEmpty((Collection<?>) object);
		} else if (object instanceof Map<?, ?>) {
			empty = MapUtils.isEmpty((Map<?, ?>) object);
		} else {
			empty = object == null;
		}

		if (empty) {
			throw new InvalidRequestException();
		}
	}

	public static User extractUser(Principal principal) {
		if (principal instanceof OAuth2Authentication) {
			return (User) ((OAuth2Authentication) principal).getPrincipal();
		}
		return (User) principal;
	}
}
