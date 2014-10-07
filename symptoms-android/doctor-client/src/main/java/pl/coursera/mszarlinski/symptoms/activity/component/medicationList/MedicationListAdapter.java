package pl.coursera.mszarlinski.symptoms.activity.component.medicationList;

import java.util.List;

import pl.mszarlinski.coursera.symptoms.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * 
 * @author Maciej
 *
 */
public class MedicationListAdapter extends ArrayAdapter<MedicationItem> {

	public MedicationListAdapter(Activity context, List<MedicationItem> items) {
		super(context, R.layout.medication_list_item, items);
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
			rowView.setTag(holder);
		} else {
			holder = (MedicationViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	private void setupView(final MedicationViewHolder holder, final MedicationItem item) {
		holder.name.setText(item.name);
		holder.checkbox.setChecked(item.selected);
		System.out.println("########### item.selected = " + item.selected);

		holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton button, boolean checked) {
				item.selected = checked;
			}
		});
	}

	public static class MedicationViewHolder {
		CheckBox checkbox;
		TextView name;
	}
}
