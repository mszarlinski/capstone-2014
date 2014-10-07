package pl.coursera.mszarlinski.symptoms.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
@Controller
public class DoctorController {

	@Autowired
	private PatientRepository patientRepository;

	// @Autowired
	// private CheckInController

	@RequestMapping(value = "patient/search/{query}")
	public @ResponseBody List<Patient> searchPatientsByName(
			@RequestParam String query, Principal principal) {
		Assert.notNull(query, "query cannot be null");
		return patientRepository.queryByName(query);
	}
}
