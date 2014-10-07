package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.repository.MedicationRepository;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class MedicationConverter extends Converter<pl.coursera.mszarlinski.symptoms.domain.Medication, Medication> {

	@Autowired
	private MedicationRepository medicationRepository;

	@Override
	public Medication convertToResource(pl.coursera.mszarlinski.symptoms.domain.Medication entity,
			Map<String, Object> params) {
		Medication resource = new Medication();
		resource.medicationId = entity.id;
		resource.name = entity.name;
		resource.assigned = entity.assigned;
		return resource;
	}

	@Override
	public pl.coursera.mszarlinski.symptoms.domain.Medication convertToEntity(Medication resource,
			Map<String, Object> params) {
		pl.coursera.mszarlinski.symptoms.domain.Medication entity = new pl.coursera.mszarlinski.symptoms.domain.Medication();
		entity.id = resource.medicationId;
		entity.assigned = resource.assigned;
		entity.name = resource.name;
		return entity;
	}
}
