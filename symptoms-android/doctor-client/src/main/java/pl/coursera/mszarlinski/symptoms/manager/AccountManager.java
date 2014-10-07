package pl.coursera.mszarlinski.symptoms.manager;

import pl.coursera.mszarlinski.symptoms.async.FetchDoctorTask;
import pl.coursera.mszarlinski.symptoms.async.RegisterDoctorTask;
import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import pl.coursera.mszarlinski.symptoms.persistence.SQLiteDAO;
import pl.coursera.mszarlinski.symptoms.persistence.entity.User;
import pl.coursera.mszarlinski.symptoms.rest.resource.UserRegistration;
import android.content.Context;
import android.util.Base64;

/**
 * 
 * @author Maciej
 *
 */
public class AccountManager {

	/**
	 * Load basic user data from device db.
	 */
	public User loadUser(Context context) {
		return new SQLiteDAO(context).getUser();
	}

	public void registerDoctor(UserRegistration registration, CharSequence rawPassword, AsyncActivity context) {
		// encoding password
		// TODO: is toString() safe here?
		registration.password = Base64.encodeToString(rawPassword.toString().getBytes(), Base64.NO_WRAP);
		new RegisterDoctorTask(context).execute(registration);
	}

	/**
	 * Fetch doctor data from server.
	 */
	public void fetchDoctor(AsyncActivity context) {
		User user = loadUser(context);
		new FetchDoctorTask(user, context).execute();
	}

	// SINGLETON
	private static final AccountManager INSTANCE = new AccountManager();

	private AccountManager() {
	}

	public static AccountManager getInstance() {
		return INSTANCE;
	}
}
