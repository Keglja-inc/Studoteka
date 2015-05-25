package com.example.fakulteti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.adapters.ExpandableListAdapter;
import com.example.listacheckbox.Model;
import com.example.studoteka.R;
import com.example.volley.AppController;

public class Fakulteti extends Fragment {
	
	private View view;
	private ExpandableListAdapter ex_list_adapter;
	private ExpandableListView ex_list_view;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private Model model, model2;

	public static final String url ="http://stufacjoint.me/rest/27543538596b07668329373594a3baaf886420a4";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.ex_lis_view, container, false);
		ex_list_view = (ExpandableListView) view.findViewById(R.id.lvExp);
		
		prepareListData();
		ex_list_adapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
		ex_list_view.setAdapter(ex_list_adapter);
		
		
		return view;
	}
	
	private void prepareListData(){
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		final List<String> test = new ArrayList<String>();
		
		JsonObjectRequest objRq = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					
					JSONArray jsArray = response.getJSONArray("podaci");
					Log.d("podaci", jsArray.toString());
					for(int i=0; i<jsArray.length(); i++){
						JSONObject jsObject = (JSONObject) jsArray.get(i);
						String ucilista = jsObject.getString("uciliste");
						//model = new Model(ucilista);
						//model.setName(ucilista);
						listDataHeader.add(ucilista);	
						//ucilista šljaka
						
						JSONArray fakulteti = (JSONArray) jsObject.get("fakulteti");
						test.clear();

						for(int j=i; j<fakulteti.length();j++){
							JSONObject naziv_faksa = (JSONObject) fakulteti.get(j);
							String f = naziv_faksa.getString("naziv");
						//	model2 = new Model(f);
						//	model2.setName(f);
							test.add(f);
							//fakulteti šljakaju
							listDataChild.put(listDataHeader.get(i),  test);
							
						}
						
					}
					
					Log.d("UCILISTA", listDataHeader.toString());
					Log.d("FAKULTETI", test.toString());
				
					
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				ex_list_adapter.notifyDataSetChanged();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		AppController.getInstance().addToRequestQueue(objRq);
	}

}
