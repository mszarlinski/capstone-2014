package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;

@Entity
public class Question extends Identifiable<Integer> {

	public String content;
}
