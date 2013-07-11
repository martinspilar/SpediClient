package com.fei.prepravnispolecnost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fei.prepravnispolecnost.maps.RoutesJSONParser;
import com.fei.prepravnispolecnost.model.AddressDTO;
import com.fei.prepravnispolecnost.model.OrderDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class OrderRouteActivity extends AbstractAsyncActivity{

	private OrderDTO order;
	private AddressDTO address;
	private GoogleMap map;
	private LocationManager locationManager;
	private LatLng origin, destination;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		Bundle b = getIntent().getExtras();
		order = (OrderDTO) b.get("orderDetail");
		address = (AddressDTO)b.get("address");

		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
		Location location = null;
		while(location==null){
		  //location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
        origin = new LatLng(location.getLatitude(), location.getLongitude());
		
	
        double destinationLat = Double.parseDouble(address.getGpsLat());
		double destinationLon = Double.parseDouble(address.getGpsLon());
		
		destination = new LatLng(destinationLat,destinationLon);
		String url = getDirectionsUrl(origin, destination);
		new DownloadRoutesTask().execute(url);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MainMenuHandler handler = new MainMenuHandler(item.getItemId(),this);
		handler.handleAction();
	    return true;
	  }
	
	private String getDirectionsUrl(LatLng origin,LatLng dest){
		String originStr = "origin="+origin.latitude+","+origin.longitude;
		String destinationStr = "destination="+dest.latitude+","+dest.longitude;		
		String sensor = "sensor=false";			
		String parameters = originStr+"&"+destinationStr+"&"+sensor;			
		String outputFormat = "json";
		
		String url = "https://maps.googleapis.com/maps/api/directions/"+outputFormat+"?"+parameters;
		return url;
	}
	
	private class DownloadRoutesTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask parserTask = new ParserTask();
			parserTask.execute(result);
		}

		private String downloadUrl(String strUrl) throws IOException {
			String data = "";
			InputStream iStream = null;
			HttpURLConnection urlConnection = null;
			try {
				URL url = new URL(strUrl);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.connect();
				iStream = urlConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						iStream));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				data = sb.toString();
				br.close();
			} catch (Exception e) {
				Log.d("Chyba pri stahovani dat", e.toString());
			} finally {
				iStream.close();
				urlConnection.disconnect();
			}
			return data;
		}
	}
	
	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
		
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			
			JSONObject jObject;	
			List<List<HashMap<String, String>>> routes = null;			           
	        try{
	        	jObject = new JSONObject(jsonData[0]);
	        	RoutesJSONParser parser = new RoutesJSONParser();
	        	
	        	routes = parser.parse(jObject);    
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        return routes;
		}
		
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			
			for(int i=0;i<result.size();i++){
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();
				
				List<HashMap<String, String>> path = result.get(i);
				
				for(int j=0;j<path.size();j++){
					HashMap<String,String> point = path.get(j);					
					
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);	
					
					points.add(position);						
				}
				
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);		
			}
			
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		    map.addPolyline(lineOptions);
		    map.addMarker(new MarkerOptions().position(destination)
		        .title("Zásilka èíslo: "+order.getId()));
		    map.addMarker(new MarkerOptions()
		        .position(origin)
		        .title("Aktuální pozice")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.small_car)));
		    
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 15));
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}			
	}   
}
