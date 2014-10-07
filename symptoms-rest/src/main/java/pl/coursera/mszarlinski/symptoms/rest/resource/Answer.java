package pl.coursera.mszarlinski.symptoms.rest.resource;

import java.io.Serializable;

import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Maciej
 *
 */
public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	public EQuestion question;
	public Integer medicationId;
	
	public String content;

	@JsonIgnore
	public Answer forQuestion(EQuestion question) {
		this.question = question;
		return this;
	}
	
	@JsonIgnore
	public Answer forMedication(int medicationId) {
		this.medicationId = medicationId;
		return this;
	}

	@JsonIgnore
	public Answer withContent(String content) {
		this.content = content;
		return this;
	}
}
