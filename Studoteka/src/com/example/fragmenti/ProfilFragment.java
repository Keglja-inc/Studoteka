package com.example.fragmenti;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studoteka.R;

/**
 * Klasa u kojoj su definirane funkcionalnosti dohvaæanja, prikaza i ažuriranja
 * profila korisnika te je definirana povezanost s pripadnim layoutom
 * 
 * @author Ivan
 *
 */
public class ProfilFragment extends Fragment {
	private View view;
	private TextView txt_vrijednost_id;
	private EditText edt_ime, edt_prezime, edt_mail_adress, edt_lozinka,
			edt_potvrda_lozinke;
	private Button btn_azuriraj;
	private int idUcenik;
	private String imeUcenik, prezimeUcenik, mailUcenik;
	private String url = "http://46.101.185.15/rest/dea967a98088c17395a09180ffb441c9e935f2ce";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.profil_fragment, container, false);
		txt_vrijednost_id = (TextView) view
				.findViewById(R.id.txt_vrijednost_id);
		edt_ime = (EditText) view.findViewById(R.id.edt_ime);
		edt_prezime = (EditText) view.findViewById(R.id.edt_prezime);
		edt_mail_adress = (EditText) view.findViewById(R.id.edt_mail_adress);
		edt_lozinka = (EditText) view.findViewById(R.id.edt_lozinka);
		edt_potvrda_lozinke = (EditText) view
				.findViewById(R.id.edt_potvrda_lozinke);
		btn_azuriraj = (Button) view.findViewById(R.id.btn_azuriraj);
		dohvatiPreferences();

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		btn_azuriraj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String odg = postaviPreferences();
				ProgressDialog pd = new ProgressDialog(getActivity());
				String poruka = "";
				if (odg != null) {
					poruka = odg;
				} else {
					poruka = getResources().getString(
							R.string.msg_azuriranje_greska);
				}
				pd.setMessage(poruka);
				pd.setCancelable(true);
				pd.show();
			}
		});

		return view;
	}

	/**
	 * Dohvaæa podatke o uèeniku iz spremljenih preferenci i postavlja
	 * vrijednosti objekta tipa UcenikModel u definirane elemente tipa EditText
	 */
	public void dohvatiPreferences() {
		SharedPreferences djeljenePostavke = getActivity()
				.getSharedPreferences("UCENIK", Context.MODE_PRIVATE);
		idUcenik = djeljenePostavke.getInt(
				getResources().getString(R.string.id_ucenik), -1);
		imeUcenik = djeljenePostavke.getString(
				getResources().getString(R.string.ime_ucenik), "");
		prezimeUcenik = djeljenePostavke.getString(
				getResources().getString(R.string.prezime_ucenik), "");
		mailUcenik = djeljenePostavke.getString(
				getResources().getString(R.string.mail_ucenik), "");

		txt_vrijednost_id.setText((CharSequence) String.valueOf(idUcenik));
		edt_ime.setText((CharSequence) imeUcenik);
		edt_prezime.setText((CharSequence) prezimeUcenik);
		edt_mail_adress.setText((CharSequence) mailUcenik);

	}

	/**
	 * Dohvaæa podatke o uèeniku i postavlja njihove vrijednosti preference
	 * 
	 * @return
	 */
	public String postaviPreferences() {
		try {
			// create HttpClient
			HttpClient httpClient = new DefaultHttpClient();

			// make POST request to the given URL
			HttpPost httpPost = new HttpPost(this.url);

			String json = "";

			// build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("idUcenika", txt_vrijednost_id.getText());
			jsonObject.accumulate("ime", edt_ime.getText());
			jsonObject.accumulate("prezime", edt_prezime.getText());
			jsonObject.accumulate("email", edt_mail_adress.getText());
			jsonObject.accumulate("lozinka", edt_lozinka.getText());
			jsonObject.accumulate("ponovljenaLozinka",
					edt_potvrda_lozinke.getText());

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

				SharedPreferences preferences = getActivity()
						.getSharedPreferences("UCENIK", Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putString("ime_ucenik", edt_ime.getText().toString());
				editor.putString("prezime_ucenik", edt_prezime.getText()
						.toString());
				editor.putString("mail_ucenik", edt_mail_adress.getText()
						.toString());
				editor.putString("lozinka_ucenik", edt_lozinka.getText()
						.toString());

				editor.commit();
				return jo.getString("message").toString();
			} else {
				return jo.getString("message").toString();
			}

		} catch (Exception e) {
			Log.d("EXCEPTION LOGIN", e.toString());
		}
		return null;
	}
}
