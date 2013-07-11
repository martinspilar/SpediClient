package com.fei.prepravnispolecnost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public abstract class AbstractAsyncActivity extends Activity {

	protected static final int RESULT_CLOSE_ALL = 111;

	private ProgressDialog progressDialog;
	private boolean destroyed = false;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destroyed = true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_CLOSE_ALL:
			setResult(RESULT_CLOSE_ALL);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void showLoadingProgressDialog(String message) {
		this.showProgressDialog(message);
	}

	public void showProgressDialog(CharSequence message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}

		progressDialog.setMessage(message);
		progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (progressDialog != null && !destroyed) {
			progressDialog.dismiss();
		}
	}

}
