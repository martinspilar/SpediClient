package com.fei.prepravnispolecnost;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AbstractAsyncActivity {

	private String login, password;
	private EditText etLogin, etPassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_dialog_layout);

		etLogin = (EditText) findViewById(R.id.username);
		etPassword = (EditText) findViewById(R.id.password);

		final Button submitButton = (Button) findViewById(R.id.login);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				login = etLogin.getText().toString();
				password = etPassword.getText().toString();
				new LoginTask().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MainMenuHandler handler = new MainMenuHandler(item.getItemId(), this);
		handler.handleAction();
		return true;
	}

	private class LoginTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog("Probíhá ovìøování...");
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				return Authorization.login(login, password);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						RESULT_CANCELED).show();
				return "false";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (!result.equals("false")) {
				// UpdatePositionThread thread = new UpdatePositionThread();
				// thread.init();
				Toast.makeText(getApplicationContext(), "Pøihlášení bylo úspìšné",
						RESULT_OK).show();
				Intent intent = new Intent(MainActivity.this,
						TrackListViewActivity.class);
				startActivityForResult(intent, RESULT_CLOSE_ALL);
			} else {
				dismissProgressDialog();
				Toast.makeText(getApplicationContext(),
						"Špatné jméno nebo heslo.", RESULT_CANCELED).show();
			}
		}
	}

	private class UpdatePositionThread implements Runnable {

		private Thread thread;

		private void init() {
			thread = new Thread(this);
			thread.start();
		}

		@Override
		public void run() {
			while (true) {
				new UpdateActualPositionTask().execute();
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class UpdateActualPositionTask extends AsyncTask<Void, Void, Boolean> {

		private LocationManager locationManager;

		@Override
		protected Boolean doInBackground(Void... params) {
			String url = Authorization.SERVER_URL
					+ "/android/andr_updatePosition.htm";
			String postResult = null;

			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location actualPosition = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			HashMap<String, String> paramList = new HashMap<String, String>();
			paramList.put("key", Authorization.AUTH_KEY);
			paramList.put("lat",
					Double.toString(actualPosition.getLatitude()));
			paramList.put("lon",
					Double.toString(actualPosition.getLongitude()));
			try {
				postResult = Comunication.sendRequest(url, paramList);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						RESULT_CANCELED).show();
			}
			return true;
		}

	}

}
