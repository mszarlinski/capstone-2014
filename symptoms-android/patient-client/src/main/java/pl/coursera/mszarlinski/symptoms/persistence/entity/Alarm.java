package pl.coursera.mszarlinski.symptoms.persistence.entity;

import java.util.Comparator;

import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;

/**
 * 
 * @author Maciej
 *
 */
public class Alarm {
	public static final String TABLE_NAME = "symptoms_alarm";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_HOUR = "hour";
	public static final String COLUMN_MINUTE = "minute";

	public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_HOUR, COLUMN_MINUTE };

	public static final String CREATE_DDL = "create table " + TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_HOUR + " int not null, " + COLUMN_MINUTE
			+ " int not null);";

	public static final Comparator<? super Alarm> COMPARATOR = new Comparator<Alarm>() {
		@Override
		public int compare(Alarm lhs, Alarm rhs) {
			return lhs.time.compareTo(rhs.time);
		}
	};

	public int id;
	public TimeOfDay time;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Alarm)) {
			return false;
		}
		Alarm other = (Alarm) obj;
		if (time == null) {
			if (other.time != null) {
				return false;
			}
		} else if (!time.equals(other.time)) {
			return false;
		}
		return true;
	}

}
