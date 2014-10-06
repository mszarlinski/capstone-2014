package pl.coursera.mszarlinski.symptoms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.coursera.mszarlinski.symptoms.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	public List<Patient> findByName(String name);
}
