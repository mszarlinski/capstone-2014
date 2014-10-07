package pl.coursera.mszarlinski.symptoms.commons.utils;

import android.widget.EditText;

/**
 * 
 * @author Maciej
 *
 */
public final class UIUtils {
	public static boolean isEmpty(EditText editText) {
		return editText == null || editText.getText() == null || editText.getText().toString() == null
				|| editText.getText().toString().trim().isEmpty();
	}
}
