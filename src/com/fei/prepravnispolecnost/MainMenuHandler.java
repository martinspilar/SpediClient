package com.fei.prepravnispolecnost;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;

public class MainMenuHandler extends AbstractAsyncActivity {

	protected int actionId;
	protected Activity parent;

	public MainMenuHandler(int actionId, Activity context) {
		this.actionId = actionId;
		this.parent = context;
	}

	public void handleAction() {
		WifiManager wifiManager = (WifiManager)parent.getSystemService(Context.WIFI_SERVICE);
		switch (actionId) {
		case R.id.main_action_close:
			exit();
			break;
		case R.id.wifi_connection:
			wifiManager.setWifiEnabled(true);
		case R.id.mobile_connection:
			wifiManager.setWifiEnabled(false);
			break;
		default:
			break;
		}
	}

	protected void reload() {
		parent.recreate();
	}

	protected void exit() {
		if (Authorization.AUTH_KEY != null) {
			new LogoutTask().execute();
		} else {
			parent.setResult(RESULT_CLOSE_ALL);
			parent.finish();
		}
	}

	protected class LogoutTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			String url = Authorization.SERVER_URL + "/android/andr_logout.htm";

			HashMap<String,String> paramList = new HashMap<String,String>();
			paramList.put("key",Authorization.AUTH_KEY);
			String postResult = null;
			try {
				postResult = Comunication.sendRequest(url, paramList);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						RESULT_CANCELED).show();
			}
			if (postResult.equals("true")) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			parent.setResult(RESULT_CLOSE_ALL);
			parent.finish();
		}
	}
}
