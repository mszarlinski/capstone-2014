package pl.coursera.mszarlinski.symptoms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.coursera.mszarlinski.symptoms.domain.Doctor;

/**
 * 
 * @author Maciej
 *
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	@Query(value = "from Doctor d where lower(concat(d.name, d.secondName)) like lower(concat('%', :query, '%')) order by d.name, d.secondName")
	public List<Doctor> queryByName(@Param("query") String query);
}
