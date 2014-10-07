package pl.coursera.mszarlinski.symptoms.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.coursera.mszarlinski.symptoms.SymptomsContextTest;
import pl.coursera.mszarlinski.symptoms.domain.Answer;
import pl.coursera.mszarlinski.symptoms.domain.CheckIn;
import pl.coursera.mszarlinski.symptoms.domain.EHealthState;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EPainIntensity;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EProblemWithEating;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;

/**
 * 
 * @author Maciej
 *
 */
public class CheckInServiceTest extends SymptomsContextTest {
	@Autowired
	private CheckInService checkInService;
	@Autowired
	private UserService userService;
	@Autowired
	private PatientRepository patientRepository;

	private Patient patient;

	@Before
	public void setUp() {
		patient = new Patient();
		patient.name = "";
		patient.secondName = "";
		patient.dateOfBirth = new Date();
		patient.medicalRecordNumber = "";
		patient.password = "";

		patient = userService.createPatient(patient);
	}

	@Test
	public void testCreateCheckIn_cantEat() {
		// when
		saveCantEatCheckIn();
		// then
		Patient p = patientRepository.findOne(patient.id);
		Assert.assertEquals(EHealthState.IN_DANGER, p.healthState);
	}

	@Test
	public void testCreateCheckIn_positive() {
		// when
		saveCantEatCheckIn();
		savePositiveCheckIn();
		// then
		Patient p = patientRepository.findOne(patient.id);
		Assert.assertEquals(EHealthState.STABLE, p.healthState);
	}

	@Test
	public void testCreateCheckIn_cantEat2() {
		// when
		saveCantEatCheckIn();
		savePositiveCheckIn();
		saveCantEatCheckIn();
		// then
		Patient p = patientRepository.findOne(patient.id);
		Assert.assertEquals(EHealthState.STABLE, p.healthState);
	}

	@Test
	public void testCreateCheckIn_moderate() {
		// when
		saveModerateEatCheckIn();
		// then
		Patient p = patientRepository.findOne(patient.id);
		Assert.assertEquals(EHealthState.IN_DANGER, p.healthState);
	}

	private void saveModerateEatCheckIn() {
		CheckIn checkIn = new CheckIn();
		Answer answer = new Answer();
		answer.question = EQuestion.HOW_BAD_IS_MOUTH_PAIN;
		answer.content = EPainIntensity.MODERATE.name();
		checkIn.answers.add(answer);
		answer.checkIn = checkIn;
		checkInService.createCheckIn(checkIn, patient.id);
	}

	private void saveCantEatCheckIn() {
		CheckIn checkIn = new CheckIn();
		Answer answer = new Answer();
		answer.question = EQuestion.DOES_PAIN_STOP_FROM_EATING;
		answer.content = EProblemWithEating.CANT_EAT.name();
		checkIn.answers.add(answer);
		answer.checkIn = checkIn;
		checkInService.createCheckIn(checkIn, patient.id);
	}

	private void savePositiveCheckIn() {
		CheckIn checkIn = new CheckIn();
		Answer answer1 = new Answer();
		answer1.question = EQuestion.DOES_PAIN_STOP_FROM_EATING;
		answer1.content = EProblemWithEating.NONE.name();
		answer1.checkIn = checkIn;
		
		Answer answer2 = new Answer();
		answer2.question = EQuestion.HOW_BAD_IS_MOUTH_PAIN;
		answer2.content = EPainIntensity.WELL_CONTROLLED.name();
		answer2.checkIn = checkIn;
		
		checkIn.answers.add(answer1);
		checkIn.answers.add(answer2);

		checkInService.createCheckIn(checkIn, patient.id);
	}
}
