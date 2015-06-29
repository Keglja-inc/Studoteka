package com.example.tabovi;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.adapteri.FakultetListAdapter;
import com.example.modeli.FakultetModel;
import com.example.studoteka.GoogleMapAktivnost;
import com.example.studoteka.R;

public class RezultatInteresaTabFragment extends Fragment {

	private View view;
	private ArrayList<FakultetModel> list = new ArrayList<FakultetModel>();
	private ListView lista_fakulteta;

	private FakultetListAdapter fAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.rezultat_interesi, container,
				false);
		lista_fakulteta = (ListView) view.findViewById(R.id.lista_fPOi);

		return view;
	}

	public void receiveData2(ArrayList<FakultetModel> data) {
		list.clear();
		list = data;

		fAdapter = new FakultetListAdapter(getActivity(), list);
		lista_fakulteta.setAdapter(fAdapter);

		lista_fakulteta.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				FakultetModel fModel = (FakultetModel) list.get(position);
				Intent faksi = new Intent(getActivity(),
						GoogleMapAktivnost.class);
				faksi.putExtra("fakulteti", fModel);
				startActivity(faksi);
			}
		});
	}
}
