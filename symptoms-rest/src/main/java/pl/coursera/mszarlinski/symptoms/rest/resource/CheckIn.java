package pl.coursera.mszarlinski.symptoms.rest.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Maciej
 *
 */
public class CheckIn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="CET")
	public Date creationDate;

	public List<Answer> answers = new ArrayList<Answer>();
	
	public List<Medication> medications = new ArrayList<Medication>();

	public byte[] throatPhoto;

	@JsonIgnore
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
}
