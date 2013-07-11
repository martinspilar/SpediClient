package com.fei.prepravnispolecnost;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Authorization {

	public static final String SERVER_URL = "http://192.168.0.103:8080/Prepravka";
	public static final String AUTHORIZED = "authorized";
	public static final String NOT_AUTHORIZED = "!authorized";
	public static final int MAX_AUTHORIZATIONS = 3;
	
	public static String LOGIN = null;
	public static String PASSWORD = null;
	public static String AUTH_KEY = "default";

	public static String login(String login, String password)
			throws Exception {
		if(login==null || password==null){
			return null;
		}
		setLoginInfo(login, password);
		
		final String url = SERVER_URL + "/android/andr_login.htm";

		HashMap<String,String> params = new HashMap<String,String>();	
		params.put("login", LOGIN);
		params.put("pass", PASSWORD);
		String postResult = Comunication.executePost(url, params);
		
		try {
			JSONObject jObj = new JSONObject(postResult);
			AUTH_KEY = jObj.getString(AUTHORIZED);
			return AUTH_KEY;
		} catch (JSONException e) {
			throw new JSONException("Chybnì pøijatá data.");
		}
	}

	public static boolean authorize() throws Exception {
		if (LOGIN != null && PASSWORD != null) {
			final String url = SERVER_URL + "/android/andr_login.htm";
			
			HashMap<String,String> params = new HashMap<String,String>();	
			params.put("login", LOGIN);
			params.put("pass", PASSWORD);
			String postResult = Comunication.executePost(url, params);
			
			try {
				JSONObject jObj = new JSONObject(postResult);
				AUTH_KEY = jObj.getString(AUTHORIZED);
				return true;
			} catch (JSONException e) {
				throw new JSONException("Chybnì pøijatá data");
			}
		} else {
			return false;
		}
	}
	
	private static void setLoginInfo(String login, String password) {
		LOGIN = login;
		PASSWORD = password;
	}
}
