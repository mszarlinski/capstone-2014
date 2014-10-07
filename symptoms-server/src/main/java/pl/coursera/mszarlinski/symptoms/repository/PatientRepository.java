package pl.coursera.mszarlinski.symptoms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.coursera.mszarlinski.symptoms.domain.Doctor;
import pl.coursera.mszarlinski.symptoms.domain.EHealthState;
import pl.coursera.mszarlinski.symptoms.domain.Patient;

/**
 * 
 * @author Maciej
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
	Patient findOneByUsername(String username);

	@Query(value = "from Patient p left join FETCH p.doctors where p.id = :id")
	Patient findOneWithDoctors(@Param("id") Long id);

	@Query(value = "from Patient p left join FETCH p.medications where p.id = :id")
	Patient findOneByIdWithMedications(@Param("id") Long id);
	

	@Query(value = "from Patient p left join FETCH p.checkIns c left join FETCH c.answers where p.id = :id")
	Patient findOneByIdWithCheckIns(@Param("id") Long patientId);

	@Query(value = "select count(*) from Patient p where :doctor MEMBER p.doctors and p.healthState = :healthState")
	int countPatientsByHealthStateAndDoctor(@Param("healthState") EHealthState healthState,
			@Param("doctor") Doctor doctor);

	@Query(value = "from Patient p where :doctor MEMBER p.doctors")
	List<Patient> findByDoctor(@Param("doctor") Doctor doctor);
}
