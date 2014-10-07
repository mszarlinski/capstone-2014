package pl.coursera.mszarlinski.symptoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coursera.mszarlinski.symptoms.domain.CheckIn;
/**
 * 
 * @author Maciej
 *
 */
public interface CheckInRepository  extends JpaRepository<CheckIn, Long> {

}
