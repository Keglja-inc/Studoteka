package hr.foi.air.tabovi;

import java.util.ArrayList;
import java.util.List;

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
import android.content.Context;
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

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import hr.foi.air.db.DBInteres;
import hr.foi.air.modeli.FakultetModel;
import hr.foi.air.modeli.InteresModel;
import hr.foi.air.studoteka.R;
import hr.foi.air.sucelja.TabSucelje;

/**
 * Klasa u kojoj su implementirane funkcionalnosti i prikaz odabranih interesa
 * 
 * @author Ivan
 *
 */
public class OdabraniInteresiTabFragment extends Fragment {
	private View view;
	private ListView ls_odabrani_interesi;
	private Button btn_izracunaj_interese;
	private ArrayAdapter<InteresModel> interesiAdapter;
	private String mail;
	private ArrayList<InteresModel> odabraniInteresiLista = new ArrayList<InteresModel>();
	private FakultetModel fakultetiModel;
	private ArrayList<FakultetModel> dohvaceniFakultetiLista = new ArrayList<FakultetModel>();
	private String url = "http://46.101.185.15/rest/37ecc108d4142d31b0bac403328b644ab2cf6b9d";
	SharedPreferences pref, opcije;

	TabSucelje com2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.odabrani_interesi, container, false);
		ls_odabrani_interesi = (ListView) view
				.findViewById(R.id.list_osobni_interesi);
		btn_izracunaj_interese = (Button) view
				.findViewById(R.id.btn_odabrani_interesi);

		pref = this.getActivity().getSharedPreferences("UCENIK",
				Context.MODE_PRIVATE);
		mail = pref.getString(getResources().getString(R.string.mail_ucenik),
				"");

		opcije = this.getActivity().getSharedPreferences("OPTIONS",
				Context.MODE_PRIVATE);

		String dohvatiIzBaze = opcije.getString("pamti_izracun", "false");
		if (dohvatiIzBaze.equals("true")) {
			// uèitati iz baze
			List<DBInteres> popisIzBaze = DBInteres.dohvatiSve();
			odabraniInteresiLista.clear();
			for (DBInteres dbInteres : popisIzBaze) {
				odabraniInteresiLista.add(new InteresModel(dbInteres
						.getIdInteresa(), dbInteres.getNaziv()));
			}
			interesiAdapter = new ArrayAdapter<InteresModel>(getActivity(),
					android.R.layout.simple_list_item_1, odabraniInteresiLista);
			ls_odabrani_interesi.setAdapter(interesiAdapter);
		}

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		btn_izracunaj_interese.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				posaljiInterese();
				ProgressDialog pd = new ProgressDialog(getActivity());
				pd.setMessage(getResources().getString(
						R.string.msg_rezultat_fakulteta));
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

	/**
	 * Dohvat odabranih interesa i njihovo spremanje u lokalnu bazu
	 * 
	 * @param data
	 *            Lista objekata tipa InteresModel
	 */
	public void receiveData(ArrayList<InteresModel> data) {

		odabraniInteresiLista.clear();
		odabraniInteresiLista = data;
		interesiAdapter = new ArrayAdapter<InteresModel>(getActivity(),
				android.R.layout.simple_list_item_1, odabraniInteresiLista);
		ls_odabrani_interesi.setAdapter(interesiAdapter);

		String spremiUBazu = opcije.getString("pamti_izracun", "false");
		if (spremiUBazu.equals("true")) {
			// spremi u bazu
			ActiveAndroid.beginTransaction();
			try {
				new Delete().from(DBInteres.class).execute();
				for (InteresModel interes : odabraniInteresiLista) {

					DBInteres dbInteres = new DBInteres(interes.getId(),
							interes.getNaziv());
					dbInteres.save();
				}
				ActiveAndroid.setTransactionSuccessful();

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				ActiveAndroid.endTransaction();
			}

		}
	}

	/**
	 * Dohvaæanje podataka o fakultetu i njihovo proslijeðivanje fragmentu
	 * RezultatiInteresaTabFragment
	 */
	private void posaljiInterese() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(this.url);

			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (InteresModel i : odabraniInteresiLista) {
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
					fakultetiModel = new FakultetModel();
					fakultetiModel.setNaziv(jso.getString("nazivFakulteta"));
					fakultetiModel.setPostotak(jso.getString("postotak"));
					fakultetiModel.setUrl(jso.getString("url"));
					dohvaceniFakultetiLista.add(fakultetiModel);

				}
			}

			com2.SendData2(dohvaceniFakultetiLista);
			Log.d("Http Post Response:", responseString.toString());

		} catch (Exception e) {
			// TODO: handle exception
			Log.d("EXCEPTION REQUEST", e.toString());
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
