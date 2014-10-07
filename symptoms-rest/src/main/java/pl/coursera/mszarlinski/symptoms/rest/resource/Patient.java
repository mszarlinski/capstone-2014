package pl.coursera.mszarlinski.symptoms.rest.resource;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author Maciej
 *
 */
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	public long id;
	public String name;
	public String secondName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
	public Date dateOfBirth;
	public String medicalRecordNumber;

	public String username;
	
	public boolean inDanger;

}
