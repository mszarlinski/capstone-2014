package pl.coursera.mszarlinski.symptoms.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Maciej
 *
 */
@MappedSuperclass
public abstract class Identifiable<T> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public T id;
}
