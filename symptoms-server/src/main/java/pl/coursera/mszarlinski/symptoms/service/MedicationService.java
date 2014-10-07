package pl.coursera.mszarlinski.symptoms.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;

import pl.coursera.mszarlinski.symptoms.domain.Medication;
import pl.coursera.mszarlinski.symptoms.domain.Patient;
import pl.coursera.mszarlinski.symptoms.repository.MedicationRepository;
import pl.coursera.mszarlinski.symptoms.repository.PatientRepository;

/**
 * 
 * @author Maciej
 *
 */
@Service
public class MedicationService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private MedicationRepository medicationRepository;

	@Transactional(readOnly = true)
	public List<Medication> getMedicationConfigForPatient(long patientId) {
		// LEFT JOIN query would be more efficient
		Patient patient = patientRepository.findOneByIdWithMedications(patientId);
		List<Medication> allMedications = medicationRepository.findAll(new Sort(Direction.ASC, "name"));

		for (Medication medication : allMedications) {
			medication.assigned = patient.medications.contains(medication);
		}

		return allMedications;
	}

	@Transactional
	public void saveMedicationConfiguration(long patientId, List<Medication> medications) {
		List<Medication> selectedMedications = getSelectedMedications(medications);
		Patient patient = patientRepository.findOneByIdWithMedications(patientId);

		patient.medications.clear();
		patient.medications.addAll(selectedMedications);
	}

	private List<Medication> getSelectedMedications(List<Medication> medications) {
		Set<Integer> medicationIds = FluentIterable.from(medications).filter(new Predicate<Medication>() {
			public boolean apply(Medication m) {
				return m.assigned;
			}
		}).transform(new Function<Medication, Integer>() {
			public Integer apply(Medication m) {
				return m.id;
			}
		}).toSet();

		List<Medication> selectedMedications = medicationRepository.findAll(medicationIds);
		return selectedMedications;
	}
}
