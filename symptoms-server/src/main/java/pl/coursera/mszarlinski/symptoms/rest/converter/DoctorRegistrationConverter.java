package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.Doctor;
import pl.coursera.mszarlinski.symptoms.repository.DoctorRepository;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class DoctorRegistrationConverter extends Converter<Doctor, UserRegistration> {

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public Doctor convertToEntity(UserRegistration resource, Map<String, Object> params) {
		Doctor entity = new Doctor();
		entity.name = resource.name;
		entity.secondName = resource.secondName;
		entity.password = resource.password;
		return entity;
	}

	@Override
	public UserRegistration convertToResource(Doctor entity, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
