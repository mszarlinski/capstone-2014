package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.CheckIn;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class CheckInConverter extends Converter<CheckIn, pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn> {

	@Autowired
	private AnswerConverter answerConverter;
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public CheckIn convertToEntity(pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn object,
			Map<String, Object> params) {
		CheckIn checkIn = new CheckIn();
		checkIn.creationDate = object.creationDate;
		checkIn.throatPhoto = object.throatPhoto;
		// convert answers
		params.put("checkIn", checkIn);
		checkIn.answers.addAll(answerConverter.convertToEntities(object.answers, params));

		return checkIn;
	}

	@Override
	public pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn convertToResource(CheckIn entity,
			Map<String, Object> params) {
		pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn resource = new pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn();
		resource.creationDate = entity.creationDate;
		// convert answers
		resource.answers.addAll(answerConverter.convertToResources(entity.answers, params));

		return resource;
	}

}
