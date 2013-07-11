package com.fei.prepravnispolecnost;

import java.util.HashMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.prepravnispolecnost.design.OrdersArrayAdapter;
import com.fei.prepravnispolecnost.model.OrderDTO;
import com.fei.prepravnispolecnost.model.TrackDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class TrackListViewActivity extends AbstractAsyncActivity {

	private TrackDTO track = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		TextView tv = (TextView) findViewById(R.id.textTrackType);
		tv.setText("Typ trasy");

		new FetchTrackTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.trackmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		TrackMenuHandler handler = new TrackMenuHandler(item.getItemId(), this,
				track);
		handler.handleAction();
		return true;
	}

	private class FetchTrackTask extends AsyncTask<Void, Void, TrackDTO> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog("Probíhá naèítání trasy...");
		}

		@Override
		protected TrackDTO doInBackground(Void... params) {

			final String url = Authorization.SERVER_URL
					+ "/android/andr_gettrack.htm";

			HashMap<String, String> paramList = new HashMap<String, String>();
			paramList.put("key", Authorization.AUTH_KEY);
			String postResult = null;
			try {
				postResult = Comunication.sendRequest(url, paramList);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						RESULT_CANCELED).show();
			}
			if (postResult.equals(Authorization.NOT_AUTHORIZED)) {
				Toast.makeText(getApplicationContext(),
						"Nepodaøilo se ovìøit uživatele.", RESULT_CANCELED)
						.show();
				return null;
			}

			Gson gson = new Gson();
			try {
				track = gson.fromJson(postResult, TrackDTO.class);
			} catch (JsonSyntaxException e) {
				Toast.makeText(getApplicationContext(),
						"Nepodaøilo se naèíst trasu.", RESULT_CANCELED).show();
			}
			return track;
		}

		@Override
		protected void onPostExecute(TrackDTO result) {
			dismissProgressDialog();
			showTrack(result);
		}

		private void showTrack(final TrackDTO track) {
			if (track != null) {
				final ListView listview = (ListView) findViewById(R.id.listview);
				OrdersArrayAdapter adapter = new OrdersArrayAdapter(
						TrackListViewActivity.this, track);
				listview.setAdapter(adapter);
				listview.setLongClickable(true);
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {
						final OrderDTO item = (OrderDTO) parent
								.getItemAtPosition(position);
						Intent intent = new Intent(
								TrackListViewActivity.this,
								DetailActivity.class);
						Bundle b = new Bundle();
						b.putParcelable("orderDetail", item);
						b.putString("trackType", Integer.toString(track.getTrackType()
								.getId()));
						intent.putExtras(b);
						startActivityForResult(intent, RESULT_CLOSE_ALL);
					}
				});
				
				TextView tv = (TextView) findViewById(R.id.textTrackType);
				tv.setText(track.getTrackType().getTrackType() + ", "
						+ track.getLength() / 1000 + "km");
				
				final Button showMapButton = (Button) findViewById(R.id.btn_show_track);
				showMapButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(
								TrackListViewActivity.this,
								TrackMapActivity.class);
						Bundle b = new Bundle();
						b.putSerializable("track", track);
						intent.putExtras(b);
						startActivityForResult(intent, RESULT_CLOSE_ALL);
					}
				});
			} else {
				Toast.makeText(getApplicationContext(),
						"Není k dispozici žádná trasa.",
						RESULT_CANCELED).show();
			}
		}

	}
}
