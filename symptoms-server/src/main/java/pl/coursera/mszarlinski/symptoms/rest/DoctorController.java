package pl.coursera.mszarlinski.symptoms.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.coursera.mszarlinski.symptoms.domain.EHealthState;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;
import pl.coursera.mszarlinski.symptoms.rest.converter.CheckInConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.DoctorConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.DoctorRegistrationConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.PatientConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.MedicationConverter;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;
import pl.coursera.mszarlinski.symptoms.service.CheckInService;
import pl.coursera.mszarlinski.symptoms.service.MedicationService;
import pl.coursera.mszarlinski.symptoms.service.UserService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Maciej
 *
 */
@RequestMapping("/rest")
@RestController
public class DoctorController {
	@Autowired
	private MedicationService medicationService;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CheckInService checkInService;
	@Autowired
	private DoctorConverter doctorConverter;
	@Autowired
	private DoctorRegistrationConverter doctorRegistrationConverter;
	@Autowired
	private PatientConverter patientConverter;
	@Autowired
	private MedicationConverter medicationConverter;
	@Autowired
	private CheckInConverter checkInConverter;

	@RequestMapping(value = "/doctor", method = RequestMethod.POST)
	public String createDoctor(@RequestBody UserRegistration resource) {
		RestUtil.notEmpty(resource);

		pl.coursera.mszarlinski.symptoms.domain.Doctor doctor = doctorRegistrationConverter.convertToEntity(resource,
				Maps.<String, Object> newHashMap());

		doctor = userService.createDoctor(doctor);
		return doctor.username;
	}

	@RequestMapping(value = "/doctor", method = RequestMethod.GET)
	public Doctor findDoctor(Principal principal) {
		RestUtil.notEmpty(principal);

		pl.coursera.mszarlinski.symptoms.domain.Doctor doctor = (pl.coursera.mszarlinski.symptoms.domain.Doctor) RestUtil
				.extractUser(principal);
		return doctorConverter.convertToResource(doctor, Maps.<String, Object> newHashMap());
	}

	@RequestMapping(value = "/patient/inDanger", method = RequestMethod.GET)
	public int createDoctor(Principal principal) {
		RestUtil.notEmpty(principal);

		pl.coursera.mszarlinski.symptoms.domain.Doctor doctor = (pl.coursera.mszarlinski.symptoms.domain.Doctor) RestUtil
				.extractUser(principal);
		return patientRepository.countPatientsByHealthStateAndDoctor(EHealthState.IN_DANGER, doctor);
	}

	@RequestMapping(value = "/doctor/patients", method = RequestMethod.GET)
	public List<Patient> getPatients(Principal principal) {
		RestUtil.notEmpty(principal);

		pl.coursera.mszarlinski.symptoms.domain.Doctor doctor = (pl.coursera.mszarlinski.symptoms.domain.Doctor) RestUtil
				.extractUser(principal);
		return Lists.newArrayList(patientConverter.convertToResources(patientRepository.findByDoctor(doctor),
				Maps.<String, Object> newHashMap()));
	}

	@RequestMapping(value = "/patient/{patientId}/medications", method = RequestMethod.GET)
	public List<Medication> getMedicationsForPatient(@PathVariable long patientId, Principal principal) {
		RestUtil.notEmpty(principal);
		return Lists.newArrayList(medicationConverter.convertToResources(
				medicationService.getMedicationConfigForPatient(patientId), Maps.<String, Object> newHashMap()));
	}

	@RequestMapping(value = "/patient/{patientId}/medications", method = RequestMethod.POST)
	public boolean saveMedicationsForPatient(@PathVariable long patientId, @RequestBody List<Medication> medications,
			Principal principal) {
		RestUtil.notEmpty(principal);
		medicationService.saveMedicationConfiguration(
				patientId,
				Lists.newArrayList(medicationConverter.convertToEntities(medications,
						Maps.<String, Object> newHashMap())));
		return true;
	}

	@RequestMapping(value = "/patient/{patientId}/checkIns", method = RequestMethod.GET)
	public List<CheckIn> getCheckInsForPatient(@PathVariable long patientId, Principal principal) {
		RestUtil.notEmpty(principal);
		return Lists.newArrayList(checkInConverter.convertToResources(checkInService.getCheckInsFormPatient(patientId),
				Maps.<String, Object> newHashMap()));
	}

}
