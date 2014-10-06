package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends Identifiable<Long> {

	@ManyToOne
	public CheckIn checkIn;

	@ManyToOne
	public Question question;

	private String content;
	private EAnswerType answerType;

	// @Transient
	// public Integer getContentAsInteger() {
	//
	// }
}
