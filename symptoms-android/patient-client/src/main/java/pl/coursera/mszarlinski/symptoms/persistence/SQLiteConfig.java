package pl.coursera.mszarlinski.symptoms.persistence;

import pl.coursera.mszarlinski.symptoms.persistence.entity.Alarm;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Maciej
 *
 */
public class SQLiteConfig extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "symptoms.db";
	private static final int DATABASE_VERSION = 1;

	public SQLiteConfig(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(Alarm.CREATE_DDL);
		database.execSQL(User.CREATE_DDL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Alarm.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
		onCreate(db);
	}

}
