package pl.coursera.mszarlinski.symptoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coursera.mszarlinski.symptoms.domain.Medication;

/**
 * 
 * @author Maciej
 *
 */
public interface MedicationRepository extends JpaRepository<Medication, Integer> {

}
