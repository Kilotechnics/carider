package ca.ualberta.c466.carid;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CarIDActivity extends Activity {
	private Camera mCamera;
	private CamPreview mPreview;
	private TextView mLabel;
	private FrameLayout mCamPreviewWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create an instance of Camera
		safeCameraOpen();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CamPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.previewWindow);
		preview.addView(mPreview);

	}

	public void onResume(Bundle savedInstanceState) {
	}

	private boolean safeCameraOpen() {
		boolean qOpened = false;

		try {
			releaseCameraAndPreview();
			mCamera = Camera.open();
			qOpened = (mCamera != null);

			Camera.Parameters params = mCamera.getParameters();
			params.setRotation(180);
			mCamera.setParameters(params);
		} catch (Exception e) {
			Log.e(getString(R.string.app_name), "failed to open Camera");
			e.printStackTrace();
		}

		return qOpened;
	}

	private void releaseCameraAndPreview() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

}