package pl.coursera.mszarlinski.symptoms.activity.checkIn.component.medicationList;

import java.util.List;

import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.checkIn.DidYouTakeMedicationsActivity;
import pl.coursera.mszarlinski.symptoms.commons.constants.BootstrapConstants;
import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;
import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class MedicationListAdapter extends ArrayAdapter<MedicationItem> {
	private final SparseArray<MedicationItem> itemsMap;
	private final Activity context;

	public MedicationListAdapter(Activity context, List<MedicationItem> items) {
		super(context, R.layout.medication_list_item, items);
		this.context = context;

		this.itemsMap = new SparseArray<MedicationItem>();
		for (MedicationItem item : items) {
			this.itemsMap.put(item.medicationId, item);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MedicationItem item = getItem(position);

		View rowView = convertView;
		MedicationViewHolder holder;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			rowView = inflater.inflate(R.layout.medication_list_item, parent, false);
			holder = new MedicationViewHolder();
			holder.checkbox = (CheckBox) rowView.findViewById(R.id.medicationCheckbox);
			holder.name = (TextView) rowView.findViewById(R.id.medicationName);
			holder.time = (TextView) rowView.findViewById(R.id.selectedTime);
			rowView.setTag(holder);
		} else {
			holder = (MedicationViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	public void updateItem(int medicationId, int selectedHour, int selectedMinute) {
		MedicationItem item = itemsMap.get(medicationId);
		item.time = TimeOfDay.of(selectedHour, selectedMinute);
	}

	public MedicationItem[] getItems() {
		MedicationItem[] itemsArray = new MedicationItem[itemsMap.size()];
		for (int i = 0; i < itemsMap.size(); i++) {
			itemsArray[i] = itemsMap.valueAt(i);
		}
		return itemsArray;
	}

	private void setupView(final MedicationViewHolder holder, final MedicationItem item) {
		holder.name.setText(item.name);
		
		if (item.time != null) {
			holder.time.setText(item.time.toString());
			holder.time.setVisibility(View.VISIBLE);
		}

		holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean checked) {
				if (checked) {
					// TODO: custom TimePickerDialog with medicationId property
					DidYouTakeMedicationsActivity.medicationId = item.medicationId;
					context.showDialog(DidYouTakeMedicationsActivity.TIME_PICKER_DIALOG_ID);
				} else {
					item.time = null;
					holder.time.setVisibility(View.INVISIBLE);
				}
				
				item.selected = checked;
			}
		});
	}

	public static class MedicationViewHolder {
		CheckBox checkbox;
		TextView name;
		TextView time;
	}
}
