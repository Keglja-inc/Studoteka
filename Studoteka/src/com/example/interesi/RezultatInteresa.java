package com.example.interesi;

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

import com.example.adapters.FakultetiPoInteresima;
import com.example.modeli.FakultetiModel;
import com.example.studoteka.GoogleMapFakulteti;
import com.example.studoteka.R;

public class RezultatInteresa extends Fragment {

	private View view;
	private ArrayList<FakultetiModel> list = new ArrayList<FakultetiModel>();
	private ListView lista_fakulteta;

	private FakultetiPoInteresima fAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fakulteti_po_interesima, container,
				false);
		lista_fakulteta = (ListView) view.findViewById(R.id.lista_fPOi);

		return view;
	}

	public void receiveData2(ArrayList<FakultetiModel> data) {
		list.clear();
		list = data;

		fAdapter = new FakultetiPoInteresima(getActivity(), list);
		lista_fakulteta.setAdapter(fAdapter);

		lista_fakulteta.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				FakultetiModel fModel = (FakultetiModel) list.get(position);
				Intent faksi = new Intent(getActivity(),
						GoogleMapFakulteti.class);
				faksi.putExtra("fakulteti", fModel);
				startActivity(faksi);
			}
		});
	}
}
