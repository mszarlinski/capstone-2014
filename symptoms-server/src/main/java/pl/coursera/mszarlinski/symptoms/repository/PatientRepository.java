package pl.coursera.mszarlinski.symptoms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.coursera.mszarlinski.symptoms.domain.Patient;

/**
 * 
 * @author Maciej
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
	public List<Patient> findByName(String name);

	@Query(value = "from Patient p where lower(concat(p.name, p.secondName)) like lower(concat('%', :query, '%'))")
	public List<Patient> queryByName(@Param("query") String query);
}
