package pl.coursera.mszarlinski.symptoms.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import pl.coursera.mszarlinski.symptoms.domain.Answer;
import pl.coursera.mszarlinski.symptoms.domain.CheckIn;
import pl.coursera.mszarlinski.symptoms.domain.CheckInTemplate;
import pl.coursera.mszarlinski.symptoms.domain.EHealthState;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.AnswerRepository;
import pl.coursera.mszarlinski.symptoms.repository.CheckInRepository;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EPainIntensity;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EProblemWithEating;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

/**
 * 
 * @author Maciej
 *
 */
@Transactional(readOnly = true)
@Service
public class CheckInService {

	private static final int SEVERE_PAIN_THRESHOLD = 12;
	private static final int MIXED_PAIN_THRESHOLD = 16;
	private static final int CANT_EAT_THRESHOLD = 12;

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private CheckInRepository checkInRepository;
	@Autowired
	private AnswerRepository answerRepository;

	public CheckInTemplate createCheckInTemplateForPatient(Long patientId) {
		Assert.notNull(patientId, "patientId cannot be null");
		Patient patient = patientRepository.findOneByIdWithMedications(patientId);
		Assert.notNull(patient, "Patient not found for id: " + patientId);
		CheckInTemplate checkInTemplate = new CheckInTemplate().withMedications(patient.medications);

		return checkInTemplate;
	}

	public List<CheckIn> getCheckInsFormPatient(Long patientId) {
		Assert.notNull(patientId, "patientId cannot be null");
		Patient patient = patientRepository.findOneByIdWithCheckIns(patientId);
		List<CheckIn> checkInsList = Lists.newArrayList(patient.checkIns);
		return checkInsList;
	}

	@Transactional
	public CheckIn createCheckIn(CheckIn checkIn, Long patientId) {
		Assert.notNull(patientId, "patientId cannot be null");
		Patient patient = patientRepository.findOne(patientId);
		Assert.notNull(patient, "Patient not found for id: " + patientId);

		checkIn.creationDate = new Date();
		checkIn.patient = patient;
		checkIn = checkInRepository.save(checkIn);

		patient.healthState = recalculatePatientHealthState(patientId);
		return checkIn;
	}

	/**
	 * A Doctor is alerted if a Patient experiences 12 hours of "severe pain,"
	 * 16 hours of "moderate" to "severe pain," or 12 hours of "I can’t eat".
	 */
	private EHealthState recalculatePatientHealthState(long patientId) {
		if (patientExperiencesSeverePain(patientId)) {
			return EHealthState.IN_DANGER;
		}
		if (patientExperiencesMixedPain(patientId)) {
			return EHealthState.IN_DANGER;
		}
		if (patientCantEat(patientId)) {
			return EHealthState.IN_DANGER;
		}

		return EHealthState.STABLE;
	}

	private boolean patientExperiencesSeverePain(long patientId) {
		Date now = new Date();
		Date thresholdDate = DateUtils.addDays(now, -SEVERE_PAIN_THRESHOLD);
		List<Answer> answers = answerRepository.findByCheckInCreationDateGreaterThanAndCheckInPatientIdAndQuestion(
				thresholdDate, patientId, EQuestion.HOW_BAD_IS_MOUTH_PAIN);
		if (answers.isEmpty()) {
			return false;
		}
		return FluentIterable.from(answers).allMatch(new Predicate<Answer>() {
			@Override
			public boolean apply(Answer answer) {
				return EPainIntensity.SEVERE.name().equals(answer.content);
			}
		});
	}

	private boolean patientExperiencesMixedPain(long patientId) {
		Date now = new Date();
		Date thresholdDate = DateUtils.addDays(now, -MIXED_PAIN_THRESHOLD);
		List<Answer> answers = answerRepository.findByCheckInCreationDateGreaterThanAndCheckInPatientIdAndQuestion(
				thresholdDate, patientId, EQuestion.HOW_BAD_IS_MOUTH_PAIN);
		if (answers.isEmpty()) {
			return false;
		}
		return FluentIterable.from(answers).allMatch(new Predicate<Answer>() {
			@Override
			public boolean apply(Answer answer) {
				return EPainIntensity.SEVERE.name().equals(answer.content)
						|| EPainIntensity.MODERATE.name().equals(answer.content);
			}
		});
	}

	private boolean patientCantEat(long patientId) {
		Date now = new Date();
		Date thresholdDate = DateUtils.addDays(now, -CANT_EAT_THRESHOLD);
		List<Answer> answers = answerRepository.findByCheckInCreationDateGreaterThanAndCheckInPatientIdAndQuestion(
				thresholdDate, patientId, EQuestion.DOES_PAIN_STOP_FROM_EATING);
		if (answers.isEmpty()) {
			return false;
		}
		return FluentIterable.from(answers).allMatch(new Predicate<Answer>() {
			@Override
			public boolean apply(Answer answer) {
				return EProblemWithEating.CANT_EAT.name().equals(answer.content);
			}
		});
	}
}
