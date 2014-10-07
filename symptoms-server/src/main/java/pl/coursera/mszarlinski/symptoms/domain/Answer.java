package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Maciej
 *
 */
@Entity
public class Answer extends Identifiable<Long> {

	@ManyToOne
	public CheckIn checkIn;

	@ManyToOne
	public Question question;

	public String content;
}
