package pl.coursera.mszarlinski.symptoms.domain;
/**
 * 
 * @author Maciej
 *
 */
public enum EProblemWithEating {
	NONE(1), 
	SOME(2), 
	CANT_EAT(3);
	
	public final int id;
	
	private EProblemWithEating(final int id) {
		this.id = id;
	}
}
