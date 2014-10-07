package pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList;

import java.util.List;

import pl.coursera.mszarlinski.symptoms.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Maciej
 *
 */
public class DoctorListAdapter extends ArrayAdapter<DoctorItem> {

	public DoctorListAdapter(Context context, List<DoctorItem> items) {
		super(context, R.layout.doctor_list_item, items);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		DoctorItem item = getItem(position);

		View rowView = convertView;
		DoctorViewHolder holder;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			rowView = inflater.inflate(R.layout.doctor_list_item, parent, false);
			holder = new DoctorViewHolder();
			holder.label = (TextView) rowView.findViewById(R.id.doctorLabel);
			rowView.setTag(holder);
		} else {
			holder = (DoctorViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	private void setupView(final DoctorViewHolder holder, final DoctorItem item) {
		holder.label.setText(item.label);
	}

	public static class DoctorViewHolder {
		TextView label;
	}
}
