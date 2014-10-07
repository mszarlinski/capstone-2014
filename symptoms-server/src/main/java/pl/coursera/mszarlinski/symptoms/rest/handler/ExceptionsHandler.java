package pl.coursera.mszarlinski.symptoms.rest.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.coursera.mszarlinski.symptoms.rest.exception.InvalidRequestException;

/**
 * 
 * @author Maciej
 *
 */
@ControllerAdvice
public class ExceptionsHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsHandler.class);

	/**
	 * Default global exception handler.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unexpected error occured")
	@ExceptionHandler(RuntimeException.class)
	public void runtimeException(RuntimeException ex) {
		LOGGER.error(ex.getMessage(), ex);
	}

	//TODO: przetestowac status 400
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request was invalid")
	@ExceptionHandler(InvalidRequestException.class)
	public void invalidRequestException() {
		LOGGER.error("InvalidRequestException handler executed");
	}
}
