package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;

/**
 * 
 * @author Maciej
 *
 */
@Entity
public class Answer extends Identifiable<Long> {

	@ManyToOne(optional = false)
	public CheckIn checkIn;

//	@ManyToOne
//	public Question question;
	
	@Enumerated(EnumType.STRING)
	public EQuestion question;
	
	@ManyToOne
	public Medication medication;

	public String content;
}
