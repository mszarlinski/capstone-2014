package pl.coursera.mszarlinski.symptoms.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.coursera.mszarlinski.symptoms.domain.User;
import pl.coursera.mszarlinski.symptoms.repository.CheckInRepository;
import pl.coursera.mszarlinski.symptoms.repository.DoctorRepository;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;
import pl.coursera.mszarlinski.symptoms.rest.converter.CheckInConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.CheckInTemplateConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.DoctorConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.PatientConverter;
import pl.coursera.mszarlinski.symptoms.rest.converter.PatientRegistrationConverter;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import pl.coursera.mszarlinski.symptoms.rest.resource.Doctor;
import pl.coursera.mszarlinski.symptoms.rest.resource.Patient;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;
import pl.coursera.mszarlinski.symptoms.service.AssignmentService;
import pl.coursera.mszarlinski.symptoms.service.CheckInService;
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
public class PatientController {

	@Autowired
	private PatientConverter patientConverter;
	@Autowired
	private CheckInConverter checkInConverter;
	@Autowired
	private DoctorConverter doctorConverter;
	@Autowired
	private CheckInRepository checkInRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CheckInTemplateConverter checkInTemplateConverter;
	@Autowired
	private PatientRegistrationConverter patientRegistrationConverter;
	@Autowired
	private CheckInService checkInService;
	@Autowired
	private AssignmentService assignmentService;

	@RequestMapping(value = "/checkIn", method = RequestMethod.POST)
	public String createCheckIn(@RequestBody CheckIn resource, Principal principal) {
		RestUtil.notEmpty(resource);
		RestUtil.notEmpty(principal);

		pl.coursera.mszarlinski.symptoms.domain.CheckIn checkIn = checkInConverter.convertToEntity(resource,
				Maps.<String, Object> newHashMap());

		User user = RestUtil.extractUser(principal);
		checkIn = checkInService.createCheckIn(checkIn, user.id);
		return "/rest/checkIn/" + checkIn.id;
	}

	// TODO: connect mode
	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public String createPatient(@RequestBody UserRegistration resource) {
		RestUtil.notEmpty(resource);

		pl.coursera.mszarlinski.symptoms.domain.Patient patient = patientRegistrationConverter.convertToEntity(
				resource, Maps.<String, Object> newHashMap());

		patient = userService.createPatient(patient);
		return patient.username;
	}

	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public @ResponseBody Patient findPatient(Principal principal) {
		RestUtil.notEmpty(principal);

		pl.coursera.mszarlinski.symptoms.domain.Patient patient = (pl.coursera.mszarlinski.symptoms.domain.Patient) RestUtil
				.extractUser(principal);
		return patientConverter.convertToResource(patient, Maps.<String, Object> newHashMap());
	}

	@RequestMapping(value = "/doctor/{query}", method = RequestMethod.GET)
	public List<Doctor> findDoctors(@PathVariable String query) {
		RestUtil.notEmpty(query);
		List<pl.coursera.mszarlinski.symptoms.domain.Doctor> doctors = doctorRepository.queryByName(query);

		return Lists.newArrayList(doctorConverter.convertToResources(doctors, Maps.<String, Object> newHashMap()));
	}

	@RequestMapping(value = "/patient/doctors", method = RequestMethod.POST)
	public boolean updateAssignments(@RequestBody List<Long> doctorIds, Principal principal) {
		RestUtil.notEmpty(principal);

		User user = RestUtil.extractUser(principal);
		assignmentService.assignDoctorsToPatient(user.id, doctorIds);
		return true;
	}

	@RequestMapping(value = "/patient/doctors", method = RequestMethod.GET)
	public List<Doctor> getAssignments(Principal principal) {
		RestUtil.notEmpty(principal);

		User user = RestUtil.extractUser(principal);
		pl.coursera.mszarlinski.symptoms.domain.Patient patientWithDoctors = patientRepository
				.findOneWithDoctors(user.id);
		Assert.notNull(patientWithDoctors, "Patient not found for id: " + user.id);
		return Lists.newArrayList(doctorConverter.convertToResources(patientWithDoctors.doctors,
				Maps.<String, Object> newHashMap()));
	}

	@RequestMapping(value = "/checkIn/template", method = RequestMethod.GET)
	public CheckIn getCheckInTemplate(Principal principal) {
		RestUtil.notEmpty(principal);
		User user = RestUtil.extractUser(principal);
		return checkInTemplateConverter
				.convertToResource(checkInService.createCheckInTemplateForPatient(user.id),
						Maps.<String, Object> newHashMap());
	}

}
