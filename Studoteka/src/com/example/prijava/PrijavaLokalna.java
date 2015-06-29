package com.example.prijava;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class PrijavaLokalna implements PrijavaSucelje {

	private String email, password, url;

	public PrijavaLokalna(String email, String password, String url) {
		super();
		this.email = email;
		this.password = password;
		this.url = url;
	}

	@Override
	public boolean prijava() {
		try {
			// create HttpClient
			HttpClient httpClient = new DefaultHttpClient();

			// make POST request to the given URL
			HttpPost httpPost = new HttpPost(this.url);

			String json = "";

			// build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("email", this.email);
			jsonObject.accumulate("lozinka", this.password);

			// convert JSONObject to JSON to String
			json = jsonObject.toString();

			// set json to StringEntity
			StringEntity se = new StringEntity(json);

			// set httpPost Entity
			httpPost.setEntity(se);

			// Execute POST request to the given URL
			HttpResponse httpResponse = httpClient.execute(httpPost);

			// convert response to String
			String responseString = EntityUtils.toString(httpResponse
					.getEntity());
			Log.d("Http Post Response:", responseString);

			JSONObject jo = new JSONObject(responseString.toString());

			if (jo.get("status").equals(true)) {
				return true;
			}

		} catch (Exception e) {
			Log.d("EXCEPTION LOGIN", "JEBEŠ ME" + e.toString());
		}
		return false;

	}

}
