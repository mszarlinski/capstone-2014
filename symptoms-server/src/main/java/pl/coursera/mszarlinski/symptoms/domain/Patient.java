package pl.coursera.mszarlinski.symptoms.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import pl.coursera.mszarlinski.symptoms.configuration.auth.EAuthority;

/**
 * 
 * @author Maciej
 *
 */
@Entity
public class Patient extends User {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	public Date dateOfBirth;
	public String medicalRecordNumber;

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Doctor> doctors = new HashSet<Doctor>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Medication> medications = new HashSet<Medication>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "patient")
	@Sort(type = SortType.NATURAL)
	public SortedSet<CheckIn> checkIns = new TreeSet<CheckIn>();
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public EHealthState healthState = EHealthState.STABLE;

	@Transient
	@Override
	public EAuthority getAuthority() {
		return EAuthority.PATIENT;
	}
}
