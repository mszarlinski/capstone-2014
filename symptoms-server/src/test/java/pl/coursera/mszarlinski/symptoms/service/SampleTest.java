package pl.coursera.mszarlinski.symptoms.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.coursera.mszarlinski.symptoms.SymptomsContextTest;
import pl.coursera.mszarlinski.symptoms.domain.Doctor;
import pl.coursera.mszarlinski.symptoms.domain.EHealthState;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.DoctorRepository;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
public class SampleTest extends SymptomsContextTest {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;

	@Test
	public void testCountPatientsByHealthStateAndDoctor() {
		// given
		Patient p = new Patient();
		p.name = "Scott";
		p.secondName = "Tiger";
		p.username = "scott.tiger";
		p.password = "qwerty";
		p.healthState = EHealthState.IN_DANGER;
		patientRepository.save(p);

		Doctor d = new Doctor();
		d.name = "John";
		d.secondName = "Parker";
		d.username = "john.parker";
		d.password = "xxx";
		d = doctorRepository.save(d);

		p.doctors.add(d);
		patientRepository.save(p);
		// when
		int inDanger = patientRepository.countPatientsByHealthStateAndDoctor(EHealthState.IN_DANGER, d);
		// then
		Assert.assertEquals(1, inDanger);
	}
}
