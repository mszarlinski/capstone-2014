package pl.coursera.mszarlinski.symptoms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import pl.coursera.mszarlinski.symptoms.domain.Doctor;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.DoctorRepository;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
@Service
public class AssignmentService {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;

	@Transactional
	public void assignDoctorsToPatient(Long patientId, List<Long> doctorIds) {
		Assert.notNull(patientId, "patientId cannot be null");
		Assert.notNull(doctorIds, "doctorIds cannot be null");
		
		Patient patient = patientRepository.findOneWithDoctors(patientId);
		Assert.notNull(patient, "Patient does not exist, id = " + patientId);
		// reassign doctors
		patient.doctors.clear();
		for (long doctorId : doctorIds) {
			Doctor doctor = doctorRepository.findOne(doctorId);
			Assert.notNull(doctor, "Doctor does not exist, id = " + doctorId);
			patient.doctors.add(doctor);
		}
	}

	@Transactional(readOnly = true)
	public long[] getAssignmentsForPatient(Long patientId) {
		Assert.notNull(patientId, "patientId cannot be null");
		Patient patient = patientRepository.findOneWithDoctors(patientId);
		
		long[] doctorIds = new long[patient.doctors.size()];
		int index = 0;
		for (Doctor doctor : patient.doctors) {
			doctorIds[index++] = doctor.id;
		}
		return doctorIds;
	}
}
