package pl.coursera.mszarlinski.symptoms.rest.resource;

import java.io.Serializable;

/**
 * 
 * @author Maciej
 *
 */
public class Doctor implements Serializable {

	private static final long serialVersionUID = 1L;
	public long id;
	public String name;
	public String secondName;
	// credentials
	public String username;
}
