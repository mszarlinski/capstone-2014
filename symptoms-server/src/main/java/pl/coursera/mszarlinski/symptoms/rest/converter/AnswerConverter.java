package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.Answer;
import pl.coursera.mszarlinski.symptoms.domain.CheckIn;
import pl.coursera.mszarlinski.symptoms.repository.MedicationRepository;

/**
 * 
 * @author Maciej
 *
 */
@Component
class AnswerConverter extends Converter<Answer, pl.coursera.mszarlinski.symptoms.rest.resource.Answer> {

	@Autowired
	private MedicationRepository medicationRepository;

	@Override
	public Answer convertToEntity(final pl.coursera.mszarlinski.symptoms.rest.resource.Answer resource, final Map<String, Object> params) {
		Answer answer = new Answer();
		answer.content = resource.content;
		answer.checkIn = (CheckIn) params.get("checkIn");

		if (resource.question != null) {
			// answer to "static" question
			answer.question = resource.question;
		} else {
			// answer to question implied by medication - "Did you take..."
			answer.medication = medicationRepository.findOne(resource.medicationId);
		}
		
		return answer;
	}

	@Override
	public pl.coursera.mszarlinski.symptoms.rest.resource.Answer convertToResource(Answer entity, Map<String, Object> params) {
		pl.coursera.mszarlinski.symptoms.rest.resource.Answer answer = new pl.coursera.mszarlinski.symptoms.rest.resource.Answer();
		answer.content = entity.content;

		if (entity.question != null) {
			answer.question = entity.question;
		} else {
			answer.medicationId = entity.medication.id;
		}
		
		return answer;
	}

}
