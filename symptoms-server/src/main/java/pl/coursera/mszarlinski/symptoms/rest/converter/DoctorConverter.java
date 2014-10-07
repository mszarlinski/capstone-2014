package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.Doctor;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class DoctorConverter extends Converter<Doctor, pl.coursera.mszarlinski.symptoms.rest.resource.Doctor> {

	@Override
	public Doctor convertToEntity(pl.coursera.mszarlinski.symptoms.rest.resource.Doctor resource,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public pl.coursera.mszarlinski.symptoms.rest.resource.Doctor convertToResource(Doctor entity,
			Map<String, Object> params) {
		pl.coursera.mszarlinski.symptoms.rest.resource.Doctor doctor = new pl.coursera.mszarlinski.symptoms.rest.resource.Doctor();
		doctor.id = entity.id;
		doctor.name = entity.name;
		doctor.secondName = entity.secondName;
		return doctor;
	}

}
