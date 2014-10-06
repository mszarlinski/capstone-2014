package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;

@Entity
public class Medication extends Identifiable<Integer> {
	
	public String name;
	public String description;
}
