package pl.coursera.mszarlinski.symptoms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.coursera.mszarlinski.symptoms.domain.Doctor;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.domain.User;
import pl.coursera.mszarlinski.symptoms.repository.DoctorRepository;
import pl.coursera.mszarlinski.symptoms.repository.IUserRepository;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
@Service
public class UserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Transactional
	public Patient createPatient(Patient patient) {
		patient.username = createUsername(patient);
		return patientRepository.save(patient);
	}

	@Transactional
	public Doctor createDoctor(Doctor doctor) {
		doctor.username = createUsername(doctor);
		return doctorRepository.save(doctor);
	}

	private String createUsername(User user) {
		String prefix = (user.name + "." + user.secondName).toLowerCase();

		// ensure username uniqueness
		User existingUser = userRepository.findByUsername(prefix);
		int index = 0;

		while (existingUser != null) {
			prefix = prefix + (++index);
			existingUser = userRepository.findByUsername(prefix);
		}
		return prefix;
	}

}
