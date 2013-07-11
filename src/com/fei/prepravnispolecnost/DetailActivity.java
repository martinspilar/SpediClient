package com.fei.prepravnispolecnost;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fei.prepravnispolecnost.model.AddressDTO;
import com.fei.prepravnispolecnost.model.CustomerDTO;
import com.fei.prepravnispolecnost.model.OrderDTO;
import com.fei.prepravnispolecnost.model.OrderStatus;
import com.fei.prepravnispolecnost.model.TrackType;

public class DetailActivity extends AbstractAsyncActivity {

	private OrderDTO order;
	private int trackType;
	private AddressDTO customerAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);

		Bundle b = getIntent().getExtras();
		order = (OrderDTO) b.get("orderDetail");
		trackType = Integer.parseInt((String) b.get("trackType"));

		TextView orderId = (TextView) findViewById(R.id.orderId);
		TextView received = (TextView) findViewById(R.id.textReceived);
		TextView address = (TextView) findViewById(R.id.textAdresa);
		TextView orderType = (TextView) findViewById(R.id.textOrderType);
		TextView orderPrice = (TextView) findViewById(R.id.textOrderPrice);

		orderId.setText("Detail objednávky èíslo: " + order.getId());
		String receivedString = new SimpleDateFormat("dd.MM.yyyy HH:mm")
				.format(order.getReceived());
		received.setText("Pøijato: " + receivedString);
		orderType
				.setText("Typ zásilky: " + order.getOrderType().getOrderType());
		orderPrice.setText("Cena: " + order.getOrderType().getPrice());

		address.setText("");
		CustomerDTO cust = null;
		switch (trackType) {
		case TrackType.DELIVER:
			address.append("Pøíjemce:\n");
			cust = order.getCustomerByIdReceiver();
			break;
		case TrackType.MOVE:
		case TrackType.GATHER:
			address.append("Odesilatel:\n");
			cust = order.getCustomerByIdSender();
			break;
		default:
			return;
		}
		
		customerAddress = cust.getAddress();
		address.append(cust.getName() + " " + cust.getSurname() + "\n");
		address.append(cust.getAddress().getStreet() + " "
				+ cust.getAddress().getStreetnum() + "\n");
		address.append(cust.getAddress().getCity() + "\n");
		address.append(Integer.toString(cust.getAddress().getPsc()));

		final Button submitButton = (Button) findViewById(R.id.updateOrder);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new OrderUpdateTask().execute();
			}
		});

		final Button showMapButton = (Button) findViewById(R.id.showMap);
		showMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DetailActivity.this,
						OrderRouteActivity.class);
				Bundle b = new Bundle();
				b.putParcelable("orderDetail", order);
				b.putParcelable("address",customerAddress);
				intent.putExtras(b);
				startActivityForResult(intent, RESULT_CLOSE_ALL);
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

	private class OrderUpdateTask extends AsyncTask<Void, Void, String> {

		private static final String UPDATED = "true";
		private static final String NOT_UPDATED = "false";

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog("Probíhá aktualizace stavu objednávky...");
		}

		@Override
		protected String doInBackground(Void... params) {

			final String url = Authorization.SERVER_URL
					+ "/android/andr_updateOrder.htm";
			String postResult = null;

			HashMap<String,String> paramList = new HashMap<String, String>();
				paramList.put("key",Authorization.AUTH_KEY);
				paramList.put("orderId", Integer.toString(order.getId()));
				
				Integer newStatus = null;
				switch (trackType) {
				case TrackType.GATHER:
					newStatus = OrderStatus.LOADED;
					break;
				case TrackType.DELIVER:
					newStatus = OrderStatus.DELIVERED;
					break;
				case TrackType.MOVE:
					newStatus = OrderStatus.AT_DEPO;
					break;
				default:
					return null;
				}
				paramList.put("newStatus", Integer.toString(newStatus));
				
				try {
					postResult = Comunication.sendRequest(url, paramList);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							RESULT_CANCELED).show();
				}
				return postResult;
		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			result.trim();
			if (result.equals(UPDATED)) {
				Intent myIntent = new Intent(DetailActivity.this,
						TrackListViewActivity.class);
				startActivityForResult(myIntent, RESULT_CLOSE_ALL);
			} else if (result.equals(NOT_UPDATED)) {
				Toast.makeText(getApplicationContext(),
						"Aktualizace stavu se nezdaøila.", RESULT_CANCELED)
						.show();
			} else if(result.equals(Authorization.NOT_AUTHORIZED))
				Toast.makeText(getApplicationContext(),
						"Chyba pøi ovìøování uživatele.", RESULT_CANCELED)
						.show();
		}
	}
}
