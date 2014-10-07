package pl.coursera.mszarlinski.symptoms.commons.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

/**
 * 
 * @author Maciej
 *
 */
public abstract class AsyncActivity extends Activity {
	private final static String DEFAULT_PROGRESS_MESSAGE = "Processing request...";
	private ProgressDialog progressDialog;

	private boolean destroyed = false;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.destroyed = true;
	}

	public void showProgressDialog() {
		showProgressDialog(DEFAULT_PROGRESS_MESSAGE);
	}

	public void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	/**
	 * Callback on success.
	 * @param result
	 * @param asyncTaskId
	 */
	public void asyncJobDoneCallback(Object result, int asyncTaskId) {
		dismissProgressDialog();
		handleResult(result, asyncTaskId);
	}

	protected abstract void handleResult(Object result, int asyncTaskId);

	/**
	 * Callback on failure.
	 * @param ex
	 * @param asyncTaskId
	 */
	public void asyncJobFailureCallback(Exception ex, int asyncTaskId) {
		dismissProgressDialog();
		handleError(ex, asyncTaskId);
	}

	protected void handleError(Exception e, int asyncTaskId) {
		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
	}

	private void dismissProgressDialog() {
		if (this.progressDialog != null && !this.destroyed) {
			this.progressDialog.dismiss();
		}
	}
}
