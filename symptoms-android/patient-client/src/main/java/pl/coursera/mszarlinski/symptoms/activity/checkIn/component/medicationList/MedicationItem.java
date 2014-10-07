package pl.coursera.mszarlinski.symptoms.activity.checkIn.component.medicationList;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;
import pl.coursera.mszarlinski.symptoms.rest.resource.Medication;

/**
 * 
 * @author Maciej
 *
 */
public class MedicationItem {
	public boolean selected;
	public int medicationId;
	public String name;
	public TimeOfDay time;

	private MedicationItem(Medication medication) {
		medicationId = medication.medicationId;
		name = medication.name;
	}

	public static List<MedicationItem> convert(List<Medication> medications) {
		List<MedicationItem> medicationItems = new ArrayList<MedicationItem>();
		for (Medication medication : medications) {
			MedicationItem item = new MedicationItem(medication);
			medicationItems.add(item);
		}
		return medicationItems;
	}
}
