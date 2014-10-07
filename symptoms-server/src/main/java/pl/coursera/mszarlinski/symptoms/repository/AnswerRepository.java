package pl.coursera.mszarlinski.symptoms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coursera.mszarlinski.symptoms.domain.Answer;
import pl.coursera.mszarlinski.symptoms.rest.enumerated.EQuestion;

/**
 * 
 * @author Maciej
 *
 */

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	/**
	 * Spring Data magic query
	 */
	List<Answer> findByCheckInCreationDateGreaterThanAndCheckInPatientIdAndQuestion(Date creationDate, long patientId, EQuestion question);

}
