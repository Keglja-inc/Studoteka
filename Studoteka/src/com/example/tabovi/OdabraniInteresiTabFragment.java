package com.example.tabovi;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.modeli.FakultetModel;
import com.example.modeli.InteresModel;
import com.example.studoteka.R;
import com.example.sucelja.TabSucelje;

public class OdabraniInteresiTabFragment extends Fragment {
	private View view;
	private ListView list_osobni_int;
	private Button btn_izracunaj_interese;
	private ArrayAdapter<InteresModel> adapter;
	private String mail;
	private ArrayList<InteresModel> list = new ArrayList<InteresModel>();
	private FakultetModel fm;
	private ArrayList<FakultetModel> listaFM = new ArrayList<FakultetModel>();
	private String url = "http://46.101.185.15/rest/37ecc108d4142d31b0bac403328b644ab2cf6b9d";

	TabSucelje com2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.odabrani_interesi, container, false);
		list_osobni_int = (ListView) view
				.findViewById(R.id.list_osobni_interesi);
		btn_izracunaj_interese = (Button) view
				.findViewById(R.id.btn_odabrani_interesi);

		SharedPreferences pref = this.getActivity().getSharedPreferences(
				"EMAIL", getActivity().MODE_PRIVATE);
		mail = pref.getString("POSLANI_MAIL", null);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		btn_izracunaj_interese.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SendInterese();
				ProgressDialog pd = new ProgressDialog(getActivity());
				pd.setMessage("Popis fakulteta možete vidjeti na slijedeæem tabu...");
				pd.setCancelable(true);
				pd.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				pd.show();
			}
		});

		return view;
	}

	public void receiveData(ArrayList<InteresModel> data) {
		list.clear();
		list = data;
		adapter = new ArrayAdapter<InteresModel>(getActivity(),
				android.R.layout.simple_list_item_1, list);
		list_osobni_int.setAdapter(adapter);
	}

	private void SendInterese() {
		Log.d("ODABRANI INTERESI", list.toString());
		Log.d("EMAIL", mail);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(this.url);

			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (InteresModel i : list) {
				temp.add(i.getId());
			}

			// build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("email", mail);
			jsonObject.accumulate("interesi", temp);

			// set json to StringEntity
			StringEntity se = new StringEntity(jsonObject.toString());

			// set httpPost Entity
			httpPost.setEntity(se);

			// Execute POST request to the given URL
			HttpResponse httpResponse = httpClient.execute(httpPost);

			// convert response to String
			String responseString = EntityUtils.toString(httpResponse
					.getEntity());
			Log.d("responseString", responseString);

			JSONObject jsObject = new JSONObject(responseString);

			if (jsObject.getBoolean("status") == true) {
				JSONArray jsArrayPoslano = jsObject.getJSONArray("podaci");

				for (int i = 0; i < jsArrayPoslano.length(); i++) {
					JSONObject jso = (JSONObject) jsArrayPoslano.get(i);
					fm = new FakultetModel();
					fm.setNaziv(jso.getString("nazivFakulteta"));
					fm.setPostotak(jso.getString("postotak"));
					fm.setUrl(jso.getString("url"));
					listaFM.add(fm);

				}
			}

			com2.SendData2(listaFM);
			Log.d("Http Post Response:", responseString.toString());

		} catch (Exception e) {
			// TODO: handle exception
			Log.d("OVO JE E", e.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			com2 = (TabSucelje) getActivity();
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ "must implement interface!");
		}
	}
}
