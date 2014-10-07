package pl.coursera.mszarlinski.symptoms.activity.component.patientList;

import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapButton;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.activity.PatientCheckInsActivity;
import pl.coursera.mszarlinski.symptoms.activity.PatientMedicationsActivity;
import pl.mszarlinski.coursera.symptoms.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class PatientListAdapter extends ArrayAdapter<PatientItem> {

	private final Context context;

	public PatientListAdapter(Context context, List<PatientItem> items) {
		super(context, R.layout.patient_list_item, items);
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		PatientItem item = getItem(position);

		View rowView = convertView;
		PatientViewHolder holder;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			rowView = inflater.inflate(R.layout.patient_list_item, parent, false);
			holder = new PatientViewHolder();
			holder.label = (TextView) rowView.findViewById(R.id.patientLabel);
			holder.showCheckInsButton = (BootstrapButton) rowView.findViewById(R.id.showCheckInsButton);
			holder.selectMedicationsButton = (BootstrapButton) rowView.findViewById(R.id.selectMedicationsButton);
			rowView.setTag(holder);
		} else {
			holder = (PatientViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	private void setupView(final PatientViewHolder holder, final PatientItem item) {
		holder.label.setText(item.label);

		holder.selectMedicationsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(context, PatientMedicationsActivity.class);
				i.putExtra(Constants.PATIENT_ID_EXTRA, item.patientId);
				context.startActivity(i);
			}
		});

		holder.showCheckInsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(context, PatientCheckInsActivity.class);
				i.putExtra(Constants.PATIENT_ID_EXTRA, item.patientId);
				context.startActivity(i);
			}
		});

	}

	public static class PatientViewHolder {
		TextView label;
		BootstrapButton showCheckInsButton;
		BootstrapButton selectMedicationsButton;
	}
}
