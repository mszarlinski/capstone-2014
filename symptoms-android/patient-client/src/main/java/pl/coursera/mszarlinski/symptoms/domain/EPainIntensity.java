package pl.coursera.mszarlinski.symptoms.domain;

/**
 * 
 * @author Maciej
 *
 */
public enum EPainIntensity {
	WELL_CONTROLLED(1), 
	MODERATE(2), 
	SEVERE(3);

	public final int id;

	private EPainIntensity(final int id) {
		this.id = id;
	}
}
