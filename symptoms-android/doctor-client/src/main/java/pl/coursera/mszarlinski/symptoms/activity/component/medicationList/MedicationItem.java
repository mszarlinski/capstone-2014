package pl.coursera.mszarlinski.symptoms.activity.component.medicationList;

import java.util.ArrayList;
import java.util.List;

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

	private MedicationItem(Medication medication) {
		medicationId = medication.medicationId;
		name = medication.name;
		selected = medication.assigned;
		System.out.println("########## selected : " + selected);
	}

	public static List<MedicationItem> convert(List<Medication> medications) {
		List<MedicationItem> medicationItems = new ArrayList<MedicationItem>();
		for (Medication medication : medications) {
			MedicationItem item = new MedicationItem(medication);
			medicationItems.add(item);
		}
		return medicationItems;
	}

	public static List<Medication> convertToResources(List<MedicationItem> medicationItems) {
		List<Medication> medications = new ArrayList<Medication>();
		for (MedicationItem item : medicationItems) {
			Medication medication = new Medication();
			medication.medicationId = item.medicationId;
			medication.assigned = item.selected;
			medication.name = item.name;
			medications.add(medication);
		}
		return medications;
	}
}
