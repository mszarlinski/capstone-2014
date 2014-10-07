package pl.coursera.mszarlinski.symptoms.commons.async;

import pl.coursera.mszarlinski.symptoms.commons.activity.AsyncActivity;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author Maciej
 */
public abstract class SymptomsAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private static final String TAG = SymptomsAsyncTask.class.getSimpleName();

	protected final AsyncActivity context;
	private Exception exception;

	public SymptomsAsyncTask(AsyncActivity context) {
		this.context = context;
	}

	protected void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	protected void onPostExecute(Result result) {
		if (exception == null) {
			context.asyncJobDoneCallback(result, getTaskId());
		} else {
			Log.e(TAG, exception.getMessage(), exception);
			context.asyncJobFailureCallback(exception, getTaskId());
		}
	}

	protected abstract int getTaskId();
}
