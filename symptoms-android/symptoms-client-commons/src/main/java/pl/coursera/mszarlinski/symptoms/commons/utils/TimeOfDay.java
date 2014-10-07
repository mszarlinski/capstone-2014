package pl.coursera.mszarlinski.symptoms.commons.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Maciej
 *
 */
public class TimeOfDay implements Comparable<TimeOfDay> {
	private final int hour;
	private final int minute;

	public static TimeOfDay of(int hour, int minute) {
		return new TimeOfDay(hour, minute);
	}

	public Date supplyTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	private TimeOfDay(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d", hour, minute);
	}

	@Override
	public int compareTo(TimeOfDay another) {
		if (hour < another.hour) {
			return -1;
		} else if (hour > another.hour) {
			return 1;
		}

		if (minute < another.minute) {
			return -1;
		} else if (minute > another.minute) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + minute;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeOfDay other = (TimeOfDay) obj;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		return true;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

}
