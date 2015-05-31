package com.example.interesi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dodaci.TabCommunication;
import com.example.studoteka.R;
import com.example.volley.AppController;

public class Interesi extends Fragment {
	View view;
	ListView lv;
	Button btn;
	private EditText inputSearch;
	ArrayList<Interes> interesLista;
	InteresiAdapter adapter;
	Interes interes;
	public CheckBox ck;
	public static final String url ="http://46.101.185.15/rest/13275cf47a74867fa3d5c02d7719e1ff28e011ba";
	final List<Interes> inter = new ArrayList<Interes>();
	
	private ArrayList<String> primljeno = new ArrayList<String>();
	
	
	TabCommunication comm;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.custom_lista, container, false);
		
		interesLista = new ArrayList<Interes>();
		lv = (ListView)view.findViewById(R.id.custom_ls);
		ck = (CheckBox)view.findViewById(R.id.check);
		inputSearch = (EditText) view.findViewById(R.id.inputSearch);
		btn = (Button) view.findViewById(R.id.btn_odabrani_interesi);
		adapter = new InteresiAdapter(dohvaceniPodaci(), getActivity());
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				primljeno = adapter.odabrano;
				
				comm.SendData(primljeno);
				
				Log.d("PODACI", primljeno.toString());
			
			}
		});
		
		lv.setAdapter(adapter);
		
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Interesi.this.adapter.getFilter().filter(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}
	

	private List<Interes> dohvaceniPodaci(){
		JsonObjectRequest objRq = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					JSONArray jsArray = response.getJSONArray("podaci");
					for(int i=0; i<jsArray.length(); i++){
						JSONObject jsObj = (JSONObject) jsArray.get(i);
						String naziv = jsObj.getString("naziv");
						interes = new Interes(naziv);
						interes.setName(naziv);
						//interesLista.add(interes);
						inter.add(interes);
					}
					Log.d("PODACI", interesLista.toString());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});

		AppController.getInstance().addToRequestQueue(objRq);
		return inter;
	}

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			comm = (TabCommunication) getActivity();
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()+ " must implement interface");
		}
	}
}	
	
