package pl.coursera.mszarlinski.symptoms.activity.app.doctors.component.doctorList;

import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapButton;

import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.activity.checkIn.DidYouTakeMedicationsActivity;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Maciej
 *
 */
public class SelectedDoctorListAdapter extends ArrayAdapter<DoctorItem> {

	public SelectedDoctorListAdapter(Context context, List<DoctorItem> items) {
		super(context, R.layout.selected_doctor_list_item, items);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		DoctorItem item = getItem(position);

		View rowView = convertView;
		SelectedDoctorViewHolder holder;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			rowView = inflater.inflate(R.layout.selected_doctor_list_item, parent, false);
			holder = new SelectedDoctorViewHolder();
			holder.label = (TextView) rowView.findViewById(R.id.selectedDoctorLabel);
			holder.removeButton = (BootstrapButton) rowView.findViewById(R.id.removeDoctorButton);
			rowView.setTag(holder);
		} else {
			holder = (SelectedDoctorViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	private void setupView(final SelectedDoctorViewHolder holder, final DoctorItem item) {
		holder.label.setText(item.label);
		holder.removeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SelectedDoctorListAdapter.this.remove(item);
				SelectedDoctorListAdapter.this.notifyDataSetChanged();
			}
		});
	}

	public static class SelectedDoctorViewHolder {
		TextView label;
		BootstrapButton removeButton;
	}
}
