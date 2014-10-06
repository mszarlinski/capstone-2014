package pl.coursera.mszarlinski.symptoms.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.coursera.mszarlinski.symptoms.configuration.Application;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = Application.class, loader = AnnotationConfigContextLoader.class)
public class SampleTest {

	@Autowired
	private PatientRepository patientRepository;

	@Test
	public void test() {
		Patient p = new Patient();
		p.name = "Maciek";
		patientRepository.save(p);

		List<Patient> ps = patientRepository.findByName("Maciek");
		Assert.assertFalse(ps.isEmpty());
	}
}
