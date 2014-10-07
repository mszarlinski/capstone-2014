package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;

/**
 * 
 * @author Maciej
 *
 */
@Entity
public class Medication extends Identifiable<Integer> {
	
	public String name;
	public String description;
}
