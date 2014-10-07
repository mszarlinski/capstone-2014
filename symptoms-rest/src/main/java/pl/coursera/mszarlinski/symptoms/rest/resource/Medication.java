package pl.coursera.mszarlinski.symptoms.rest.resource;

import java.io.Serializable;

/**
 * 
 * @author Maciej
 *
 */
public class Medication implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int medicationId;
	public String name;
	public boolean assigned;
}
