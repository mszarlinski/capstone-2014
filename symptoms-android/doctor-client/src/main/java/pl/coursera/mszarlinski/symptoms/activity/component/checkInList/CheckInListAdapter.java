package pl.coursera.mszarlinski.symptoms.activity.component.checkInList;

import java.util.List;

import pl.mszarlinski.coursera.symptoms.R;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class CheckInListAdapter extends ArrayAdapter<CheckInItem> {

	public CheckInListAdapter(Activity context, List<CheckInItem> items) {
		super(context, R.layout.checkin_list_item, items);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		CheckInItem item = getItem(position);

		View rowView = convertView;
		CheckInViewHolder holder;

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			rowView = inflater.inflate(R.layout.checkin_list_item, parent, false);
			holder = new CheckInViewHolder();

			holder.painIntensityText = (TextView) rowView.findViewById(R.id.painIntensityText);
			holder.problemWithEatingText = (TextView) rowView.findViewById(R.id.problemWithEatingText);
			holder.checkInDateText = (TextView) rowView.findViewById(R.id.checkInDate);
			holder.okStatusIcon = (BootstrapButton) rowView.findViewById(R.id.okStatusIcon);
			holder.moderateStatusIcon = (BootstrapButton) rowView.findViewById(R.id.moderateStatusIcon);
			holder.dangerStatusIcon = (BootstrapButton) rowView.findViewById(R.id.dangerStatusIcon);
			rowView.setTag(holder);
		} else {
			holder = (CheckInViewHolder) rowView.getTag();
		}

		setupView(holder, item);

		return rowView;
	}

	private void setupView(final CheckInViewHolder holder, final CheckInItem item) {
		holder.checkInDateText.setText(DateFormat.format("yyyy-MM-dd hh:mm", item.checkInDate).toString());

		holder.okStatusIcon.setVisibility(View.GONE);
		holder.moderateStatusIcon.setVisibility(View.GONE);
		holder.dangerStatusIcon.setVisibility(View.GONE);

		switch (item.itemStyle) {
		case DANGER:
			holder.dangerStatusIcon.setVisibility(View.VISIBLE);
			break;
		case MODERATE:
			holder.moderateStatusIcon.setVisibility(View.VISIBLE);
			break;
		case OK:
			holder.okStatusIcon.setVisibility(View.VISIBLE);
			break;
		default:
			throw new IllegalArgumentException("Unknown checkInItem style: " + item.itemStyle);
		}

		switch (item.painIntensity) {
		case WELL_CONTROLLED:
			holder.painIntensityText.setText(R.string.well_controlled);
			break;
		case MODERATE:
			holder.painIntensityText.setText(R.string.moderate);
			break;
		case SEVERE:
			holder.painIntensityText.setText(R.string.severe);
			break;
		default:
			throw new IllegalArgumentException("Unknown pain intensity: " + item.painIntensity);
		}

		switch (item.problemWithEating) {
		case NONE:
			holder.problemWithEatingText.setText(R.string.none);
			break;
		case SOME:
			holder.problemWithEatingText.setText(R.string.some);
			break;
		case CANT_EAT:
			holder.problemWithEatingText.setText(R.string.cant_eat);
			break;
		default:
			throw new IllegalArgumentException("Unknown problem with eating: " + item.problemWithEating);
		}
	}

	public static class CheckInViewHolder {
		TextView checkInDateText;
		TextView painIntensityText;
		TextView problemWithEatingText;

		BootstrapButton okStatusIcon;
		BootstrapButton moderateStatusIcon;
		BootstrapButton dangerStatusIcon;
	}
}
