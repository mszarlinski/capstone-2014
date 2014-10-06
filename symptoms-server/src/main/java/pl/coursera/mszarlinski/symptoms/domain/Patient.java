package pl.coursera.mszarlinski.symptoms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Patient extends Identifiable<Long> {

	public String name;
	public String secondName;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Medication> medications = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "patient")
	public Set<CheckIn> checkIns = new HashSet<>();
}
