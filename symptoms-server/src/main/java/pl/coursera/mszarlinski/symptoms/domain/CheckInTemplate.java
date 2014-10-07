package pl.coursera.mszarlinski.symptoms.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author Maciej
 *
 */
public class CheckInTemplate {

	public List<Medication> medications = new ArrayList<Medication>();

	public CheckInTemplate withMedications(Collection<Medication> medications) {
		this.medications = Lists.newArrayList(medications);

		Collections.sort(this.medications, new Comparator<Medication>() {
			@Override
			public int compare(Medication o1, Medication o2) {
				return o1.id.compareTo(o2.id);
			}
		});
		return this;
	}
}
