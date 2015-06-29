package com.example.prijava;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class RegistracijaLokalna {
	
	private String imeKorisnika, prezimeKorisnika, emailKorisnika, lozinkaKorisnika;
	private String url = "http://46.101.185.15/rest/0e3a5ba1f993b60805694759ba4b882afef53281";

	public RegistracijaLokalna(String imeKorisnika, String prezimeKorisnika,
			String emailKorisnika, String lozinkaKorisnika) {
		super();
		this.imeKorisnika = imeKorisnika;
		this.prezimeKorisnika = prezimeKorisnika;
		this.emailKorisnika = emailKorisnika;
		this.lozinkaKorisnika = lozinkaKorisnika;
	}

	public String getImeKorisnika() {
		return imeKorisnika;
	}

	public void setImeKorisnika(String imeKorisnika) {
		this.imeKorisnika = imeKorisnika;
	}

	public String getPrezimeKorisnika() {
		return prezimeKorisnika;
	}

	public void setPrezimeKorisnika(String prezimeKorisnika) {
		this.prezimeKorisnika = prezimeKorisnika;
	}

	public String getEmailKorisnika() {
		return emailKorisnika;
	}

	public void setEmailKorisnika(String emailKorisnika) {
		this.emailKorisnika = emailKorisnika;
	}

	public String getLozinkaKorisnika() {
		return lozinkaKorisnika;
	}

	public void setLozinkaKorisnika(String lozinkaKorisnika) {
		this.lozinkaKorisnika = lozinkaKorisnika;
	}
	
	public boolean registracijaKorisnika (){
		
		try {
			//create HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			
			//make POST request to the given URL
			HttpPost httpPost = new HttpPost(this.url);
			
			String json = "";
			
			//build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("ime", this.imeKorisnika);
            jsonObject.accumulate("prezime", this.prezimeKorisnika);
            jsonObject.accumulate("email", this.emailKorisnika);
            jsonObject.accumulate("lozinka", this.lozinkaKorisnika);
 
            //convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            //set json to StringEntity
            StringEntity se = new StringEntity(json);
 
            //set httpPost Entity
            httpPost.setEntity(se);
            
            //Execute POST request to the given URL
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
            //convert response to String
            String responseString = EntityUtils.toString(httpResponse.getEntity());
			Log.d("Http Post Response:", responseString);
			
			JSONObject jo = new JSONObject(responseString.toString());
			if(jo.get("status").equals(true)){
				return true;
			}
		} catch (Exception e) {
			Log.d("EXCEPTION LOGIN", "JEBEŠ ME"+e.toString());
		}
		return false;
	}	
}
