package pl.coursera.mszarlinski.symptoms.persistence;

import java.util.ArrayList;
import java.util.List;

import pl.coursera.mszarlinski.symptoms.commons.utils.TimeOfDay;
import pl.coursera.mszarlinski.symptoms.persistence.entity.Alarm;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Maciej
 *
 */
public class SQLiteDAO {

	private SQLiteDatabase database;
	private SQLiteConfig sqLiteConfig;

	public SQLiteDAO(Context context) {
		sqLiteConfig = new SQLiteConfig(context);
	}

	private void open() throws SQLException {
		database = sqLiteConfig.getWritableDatabase();
	}

	private void close() {
		sqLiteConfig.close();
	}

	public Alarm createAlarm(TimeOfDay time) {
		try {
			open();

			ContentValues values = new ContentValues();
			values.put(Alarm.COLUMN_HOUR, time.getHour());
			values.put(Alarm.COLUMN_MINUTE, time.getMinute());

			long insertId = database.insert(Alarm.TABLE_NAME, null, values);

			Cursor cursor = database.query(Alarm.TABLE_NAME, Alarm.ALL_COLUMNS, Alarm.COLUMN_ID + " = " + insertId,
					null, null, null, null);
			cursor.moveToFirst();

			Alarm newAlarm = cursorToAlarm(cursor);
			cursor.close();
			return newAlarm;
		} finally {
			close();
		}
	}

	public void deleteAlarm(int alarmId) {
		try {
			open();
			database.delete(Alarm.TABLE_NAME, Alarm.COLUMN_ID + " = " + alarmId, null);
		} finally {
			close();
		}
	}

	public List<Alarm> getAllAlarms() {
		try {
			open();
			List<Alarm> alarms = new ArrayList<Alarm>();

			Cursor cursor = database.query(Alarm.TABLE_NAME, Alarm.ALL_COLUMNS, null, null, null, null,
					Alarm.COLUMN_HOUR + ", " + Alarm.COLUMN_MINUTE);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Alarm alarm = cursorToAlarm(cursor);
				alarms.add(alarm);
				cursor.moveToNext();
			}
			cursor.close();
			return alarms;
		} finally {
			close();
		}
	}

	private Alarm cursorToAlarm(Cursor cursor) {
		Alarm alarm = new Alarm();
		alarm.id = cursor.getInt(0);
		alarm.time = TimeOfDay.of(cursor.getInt(1), cursor.getInt(2));
		return alarm;
	}

	public Alarm findAlarmByTime(TimeOfDay time) {
		try {
			open();
			final String whereClause = Alarm.COLUMN_HOUR + " = " + time.getHour() + " AND " + Alarm.COLUMN_MINUTE
					+ " = " + time.getMinute();

			Cursor cursor = database.query(Alarm.TABLE_NAME, Alarm.ALL_COLUMNS, whereClause, null, null, null, null);
			Alarm alarm = null;
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				alarm = cursorToAlarm(cursor);
			}
			cursor.close();
			return alarm;
		} finally {
			close();
		}
	}

	public User getUser() {
		try {
			open();
			Cursor cursor = database.query(User.TABLE_NAME, User.ALL_COLUMNS, null, null, null, null, null);

			User user = null;

			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				user = cursorToUser(cursor);
			}
			cursor.close();
			return user;
		} finally {
			close();
		}
	}

	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.id = cursor.getLong(0);
		user.username = cursor.getString(1);
		user.password = cursor.getString(2);
		return user;
	}

	public void saveUser(String username, String encryptedPass) {
		try {
			open();

			ContentValues values = new ContentValues();
			values.put(User.COLUMN_USERNAME, username);
			values.put(User.COLUMN_PASSWORD, encryptedPass);

			database.insert(User.TABLE_NAME, null, values);
		} finally {
			close();
		}

	}

}