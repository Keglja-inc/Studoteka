package com.example.interesi;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studoteka.R;

public class OsobniInteresi extends Fragment {
	private View view;
	private ListView list_osobni_int;
	private TextView txt_info;
	
	private ArrayAdapter<String> adapter;
	private InteresiAdapter adp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.odabrani_interesi, container, false);
		list_osobni_int = (ListView)view.findViewById(R.id.list_osobni_interesi);
		txt_info  =(TextView)view.findViewById(R.id.txt_osobni_interesi);

		return view;
	}
	
	public void receiveData(ArrayList<String> data){
		ArrayList<String> list = new ArrayList<String>();
		list.clear();
		list = data;

		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
		list_osobni_int.setAdapter(adapter);
		
	}
	
}
