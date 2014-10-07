package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.EHealthState;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.DoctorRepository;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class PatientConverter extends Converter<Patient, pl.coursera.mszarlinski.symptoms.rest.resource.Patient> {

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public Patient convertToEntity(pl.coursera.mszarlinski.symptoms.rest.resource.Patient resource,
			Map<String, Object> params) {
		Patient entity = new Patient();
		entity.name = resource.name;
		entity.secondName = resource.secondName;
		entity.username = resource.username;
		return entity;
	}

	@Override
	public pl.coursera.mszarlinski.symptoms.rest.resource.Patient convertToResource(Patient entity,
			Map<String, Object> params) {
		pl.coursera.mszarlinski.symptoms.rest.resource.Patient resource = new pl.coursera.mszarlinski.symptoms.rest.resource.Patient();
		resource.id = entity.id;
		resource.name = entity.name;
		resource.secondName = entity.secondName;
		resource.medicalRecordNumber = entity.medicalRecordNumber;
		resource.dateOfBirth = entity.dateOfBirth;
		resource.inDanger = entity.healthState == EHealthState.IN_DANGER;
		return resource;
	}

}
