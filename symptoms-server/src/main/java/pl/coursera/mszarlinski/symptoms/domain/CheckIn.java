package pl.coursera.mszarlinski.symptoms.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CheckIn extends Identifiable<Long> {
	
	@ManyToOne
	public Patient patient;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "checkIn")
	public Set<Answer> answers = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	public Date creationDate;
}
