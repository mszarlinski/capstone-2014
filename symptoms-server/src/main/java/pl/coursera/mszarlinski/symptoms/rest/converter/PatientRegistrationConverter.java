package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class PatientRegistrationConverter extends Converter<Patient, UserRegistration> {

	@Override
	public Patient convertToEntity(UserRegistration resource, Map<String, Object> params) {
		Patient entity = new Patient();
		entity.name = resource.name;
		entity.secondName = resource.secondName;
		entity.password = resource.password;
		entity.medicalRecordNumber = resource.medicalRecordNumber;
		entity.dateOfBirth = resource.dateOfBirth;
		return entity;
	}

	@Override
	public UserRegistration convertToResource(Patient entity, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
