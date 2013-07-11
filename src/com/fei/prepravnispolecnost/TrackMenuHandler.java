package com.fei.prepravnispolecnost;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fei.prepravnispolecnost.model.OrderDTO;
import com.fei.prepravnispolecnost.model.OrderStatus;
import com.fei.prepravnispolecnost.model.TrackDTO;
import com.fei.prepravnispolecnost.model.TrackStatus;
import com.fei.prepravnispolecnost.model.TrackType;

public class TrackMenuHandler extends MainMenuHandler {

	private TrackDTO track;
	private Activity parent;

	public TrackMenuHandler(int actionId, Activity context) {
		super(actionId, context);
		this.parent = context;
	}

	public TrackMenuHandler(int actionId, Activity context, TrackDTO track) {
		super(actionId, context);
		this.track = track;
		this.parent = context;
	}

	@Override
	public void handleAction() {
		switch (actionId) {
		case R.id.track_action_close:
			exit();
			break;
		case R.id.track_finish_track:
			if (track != null) {
				new FinishTrackTask().execute();
			}
			break;
		default:
			break;
		}
	}

	private class FinishTrackTask extends AsyncTask<Void, Void, String> {
		
		public static final String TRACK_NOT_FINISHED = "!finished";
		public static final String TRACK_FINISHED = "true";
		public static final String TRACK_UPDATE_FAIL = "false";

		@Override
		protected String doInBackground(Void... params) {

			final String url = Authorization.SERVER_URL + "/android/andr_updateTrack.htm";
			String postResult = null;

			boolean isFinished = false;
			int trackType = track.getTrackType().getId();
			int testedOrderStatus = -1;
			switch (trackType) {
			case TrackType.GATHER:
				testedOrderStatus = OrderStatus.LOADED;
				break;
			case TrackType.DELIVER:
				testedOrderStatus = OrderStatus.DELIVERED;
				break;
			case TrackType.MOVE:
				testedOrderStatus = OrderStatus.AT_DEPO;
				break;
			default:
				return null;
			}

			for (OrderDTO order : track.getOrderses()) {
				if (order.getOrderStatus().getId() != testedOrderStatus) {
					isFinished = false;
					break;
				} else {
					isFinished = true;
				}
			}
			if (isFinished) {
				HashMap<String, String> paramList = new HashMap<String, String>();
				paramList.put("key", Authorization.AUTH_KEY);
				paramList.put("trackId", Integer.toString(track.getId()));
				paramList.put("newStatus", Integer.toString(TrackStatus.FINISHED));
				try {
					postResult = Comunication.sendRequest(url, paramList);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							RESULT_CANCELED).show();
				}
				return postResult;
			} else {
				return TRACK_NOT_FINISHED;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			
			if (result.equals(TRACK_FINISHED)) {
				Toast.makeText(parent.getApplicationContext(),
						"Trasa byla ukonèena.", RESULT_OK).show();
				reload();
			} else if (result.equals(TRACK_UPDATE_FAIL)) {
				Toast.makeText(parent, "Aktualizace stavu se nezdaøila.",
						RESULT_CANCELED).show();
			} else if (result.equals(TRACK_NOT_FINISHED)) {
				Toast.makeText(parent, "V trase jsou nezpracované zakázky.",
						RESULT_CANCELED).show();
			} else if(result.equals(Authorization.NOT_AUTHORIZED)){
				Toast.makeText(parent,
						"Nepodaøilo se ovìøit uživatele.", RESULT_CANCELED)
						.show();
			}else{
				Toast.makeText(parent, "Chyba pøi ovìøování uživatele.",
						RESULT_CANCELED).show();
			}
		}
	}
}
