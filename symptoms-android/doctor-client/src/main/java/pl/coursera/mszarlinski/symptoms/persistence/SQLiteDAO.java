package pl.coursera.mszarlinski.symptoms.persistence;

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