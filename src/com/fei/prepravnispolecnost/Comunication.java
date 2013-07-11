package com.fei.prepravnispolecnost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;

public class Comunication {
	
	public static String sendRequest(String url, HashMap<String,String> params) throws Exception{
		String postResult = null;
		for (int i = 0; i < Authorization.MAX_AUTHORIZATIONS; i++) {
			postResult = Comunication.executePost(url, params);
			if (postResult.equals(Authorization.NOT_AUTHORIZED)) {
					Authorization.authorize();
			}else{
				break;
			}
		}
		return postResult;
	}

	public static String executePost(String url, HashMap<String,String> params) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient(
				new BasicHttpParams());
		HttpPost httppost = new HttpPost(url);
		if (params != null) {
			try {
				httppost.setEntity(new UrlEncodedFormEntity(createParamList(params), HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				throw new UnsupportedEncodingException("Parametry neodpovídají UTF8.");
			}
		}

		InputStream inputStream = null;
		String result = null;
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"), 8);
			
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			return result;
		} catch (IOException e) {
			throw new IOException("Chyba komunikace se serverem.");
		} catch (Exception e) {
			throw new Exception("Nastala neoèekávaná chyba.");
		}
	}
	
	private static List<NameValuePair> createParamList(HashMap<String,String> params){
		if(params==null || params.keySet().isEmpty()){
			return null;
		}
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			result.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return result;
	}
}
