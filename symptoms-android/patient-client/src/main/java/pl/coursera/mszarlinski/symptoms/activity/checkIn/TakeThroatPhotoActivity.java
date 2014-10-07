package pl.coursera.mszarlinski.symptoms.activity.checkIn;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.coursera.mszarlinski.symptoms.Constants;
import pl.coursera.mszarlinski.symptoms.R;
import pl.coursera.mszarlinski.symptoms.commons.constants.BootstrapConstants;
import pl.coursera.mszarlinski.symptoms.commons.utils.IOUtils;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * 
 * @author Maciej
 *
 */
public class TakeThroatPhotoActivity extends Activity {

	private static final String TAG = TakeThroatPhotoActivity.class.getSimpleName();
	private static final int CAMERA_PIC_REQUEST = 1;

	@InjectView(R.id.cancelButton) BootstrapButton cancelButton;
	
	private CheckIn checkIn;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_throat_photo);

		ButterKnife.inject(this);

		checkIn = (CheckIn) getIntent().getExtras().getSerializable(Constants.CHECK_IN_EXTRA);
		imageUri = null;
	}

	@OnClick(R.id.cameraButton)
	public void launchCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUri = getOutputMediaFileUri();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, CAMERA_PIC_REQUEST);
	}

	private static Uri getOutputMediaFileUri() {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		// For future implementation: store videos in a separate directory
		File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Symptoms");

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.e(TAG, "Failed to create directory!");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

		return Uri.fromFile(mediaFile);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				try {
					checkIn.throatPhoto = 
							IOUtils.toByteArray(getContentResolver().openInputStream(imageUri));
					enableCancelButton();
				} catch (IOException ex) {
					Log.e(TAG, "Error while converting photo to byte array", ex);
				}
			} else {
				// Image capture failed
				Log.e(TAG, "Image capture failed");
			}
		}
	}

	private void enableCancelButton() {
		cancelButton.setEnabled(true);
		cancelButton.setBootstrapType(BootstrapConstants.WARNING);
	}
	
	private void disableCancelButton() {
		cancelButton.setEnabled(false);
		cancelButton.setBootstrapType(BootstrapConstants.DEFAULT);
	}
	
	@OnClick(R.id.cancelButton)
	public void cancelPhoto() {
		checkIn.throatPhoto = null;
		imageUri = null;
		disableCancelButton();
	}
	
	@OnClick(R.id.nextButton)
	public void clickNext() {
		Intent i = new Intent(this, FinishCheckInActivity.class);
		i.putExtra(Constants.CHECK_IN_EXTRA, checkIn);
		startActivity(i);
	}

}
