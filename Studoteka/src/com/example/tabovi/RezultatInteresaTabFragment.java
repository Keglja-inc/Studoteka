package com.example.tabovi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.adapteri.FakultetListAdapter;
import com.example.db.DBFakultet;
import com.example.modeli.FakultetModel;
import com.example.studoteka.GoogleMapAktivnost;
import com.example.studoteka.R;

/**
 * Klasa u kojoj su implementirane i prikazane funkcionalnosti za prikaz
 * fakulteta po odabranim interesima
 * 
 * @author Ivan
 *
 */
public class RezultatInteresaTabFragment extends Fragment {

	private View view;
	private ArrayList<FakultetModel> fakultetiPoInteresimaLista = new ArrayList<FakultetModel>();
	private ListView ls_fakulteti;
	private FakultetListAdapter fakultetiAdapter;
	SharedPreferences opcije;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.rezultat_interesi, container, false);
		ls_fakulteti = (ListView) view.findViewById(R.id.lista_fPOi);

		opcije = this.getActivity().getSharedPreferences("OPTIONS",
				Context.MODE_PRIVATE);

		String dohvatiIzBaze = opcije.getString("pamti_izracun", "false");
		if (dohvatiIzBaze.equals("true")) {
			// uèitati iz baze
			List<DBFakultet> popisIzBaze = DBFakultet.dohvatiSve();
			fakultetiPoInteresimaLista.clear();
			for (DBFakultet dbFakultet : popisIzBaze) {
				fakultetiPoInteresimaLista.add(new FakultetModel(dbFakultet
						.getNaziv(), dbFakultet.getUrl(), dbFakultet
						.getPostotak()));
			}
			fakultetiAdapter = new FakultetListAdapter(getActivity(),
					fakultetiPoInteresimaLista);
			ls_fakulteti.setAdapter(fakultetiAdapter);
			fakultetiAdapter.notifyDataSetChanged();
		}

		return view;
	}

	/**
	 * Dohvat podataka o fakultetima
	 * 
	 * @param data
	 *            Lista objekata tipa FakultetModel
	 */
	public void receiveData2(ArrayList<FakultetModel> data) {
		// list.clear();
		fakultetiPoInteresimaLista = data;

		fakultetiAdapter = new FakultetListAdapter(getActivity(),
				fakultetiPoInteresimaLista);
		ls_fakulteti.setAdapter(fakultetiAdapter);
		fakultetiAdapter.notifyDataSetChanged();

		ls_fakulteti.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				FakultetModel fModel = (FakultetModel) fakultetiPoInteresimaLista
						.get(position);
				Intent faksi = new Intent(getActivity(),
						GoogleMapAktivnost.class);
				faksi.putExtra("fakulteti", fModel);
				startActivity(faksi);
			}
		});

		String spremiUBazu = opcije.getString("pamti_izracun", "false");
		if (spremiUBazu.equals("true")) {
			// spremi u bazu
			ActiveAndroid.beginTransaction();
			try {
				new Delete().from(DBFakultet.class).execute();
				for (FakultetModel fakultet : fakultetiPoInteresimaLista) {
					DBFakultet dbFakultet = new DBFakultet(fakultet.getUrl(),
							fakultet.getNaziv(), fakultet.getPostotak());
					dbFakultet.save();
				}
				ActiveAndroid.setTransactionSuccessful();

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				ActiveAndroid.endTransaction();
			}
		}
	}
}
