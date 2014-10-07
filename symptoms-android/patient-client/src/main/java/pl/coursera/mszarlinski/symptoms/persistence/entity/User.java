package pl.coursera.mszarlinski.symptoms.persistence.entity;

/**
 * Entity representing User data used to authenticate client in a server.
 * 
 * @author Maciej
 *
 */
public class User {
	public static final String TABLE_NAME = "symptoms_user";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";

	public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD };

	public static final String CREATE_DDL = "create table " + TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME + " varchar(100) not null, " + COLUMN_PASSWORD
			+ " varchar(100) not null);";

	public long id;
	public String username;
	public String password; // encrypted
}
