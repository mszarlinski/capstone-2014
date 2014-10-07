package pl.coursera.mszarlinski.symptoms.rest.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Maciej
 *
 */
@ControllerAdvice
public class ExceptionsHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExceptionsHandler.class);

	/**
	 * Default global exception handler.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "IOException occured")
	@ExceptionHandler(RuntimeException.class)
	public void runtimeException() {
		LOGGER.error("RuntimeException handler executed");
	}
}
