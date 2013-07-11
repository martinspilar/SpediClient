package com.fei.prepravnispolecnost;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fei.prepravnispolecnost.model.AddressDTO;
import com.fei.prepravnispolecnost.model.OrderDTO;
import com.fei.prepravnispolecnost.model.TrackDTO;
import com.fei.prepravnispolecnost.model.TrackType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class TrackMapActivity extends AbstractAsyncActivity {

	private TrackDTO track;
	private int trackType;
	private GoogleMap map;
	private LatLng origin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		Bundle b = getIntent().getExtras();
		track = (TrackDTO) b.get("track");
		trackType = track.getTrackType().getId();
		origin = locationFromAddress(track.getDepoByIdDepoStart().getAddress());
		
		boolean breakCycle = false;
		ArrayList<LatLng> points = new ArrayList<LatLng>();
		for (OrderDTO order : track.getOrderses()) {
			AddressDTO help = null;
			switch (trackType) {
			case TrackType.GATHER:
				help = order.getCustomerByIdSender().getAddress();
				break;
			case TrackType.DELIVER:
				help = order.getCustomerByIdReceiver().getAddress();
				break;
			case TrackType.MOVE:
				help = order.getDepoByIdDepoEnd().getAddress();
				breakCycle = true;
				break;
			default:
				return;
			}
			points.add(locationFromAddress(help));
			if(breakCycle){
				break;
			}
		}
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		PolylineOptions lineOptions = new PolylineOptions();
		lineOptions.addAll(points);
		map.addPolyline(lineOptions);
		
		map.addMarker(new MarkerOptions()
        .position(origin)
        .title("Start")
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.small_car)));
		
		List<OrderDTO> orderses = track.getOrderses();
		for (int i = 0; i < points.size(); i++) {
			map.addMarker(new MarkerOptions().position(points.get(i))
			        .title("Zásilka èíslo: "+orderses.get(i).getId()));
		}
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		
	}

	private LatLng locationFromAddress(AddressDTO address) {
		double lat = Double.parseDouble(address.getGpsLat());
		double lon = Double.parseDouble(address.getGpsLon());
		return new LatLng(lat, lon);
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
}
