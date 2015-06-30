package com.example.fragmenti;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.modeli.FakultetModel;
import com.example.studoteka.GoogleMapAktivnost;
import com.example.studoteka.R;
import com.example.volley.AppController;

public class FakultetiFragment extends Fragment {

	private View view;
	private FakultetModel model;
	private List<FakultetModel> fakulteti = new ArrayList<FakultetModel>();
	private List<FakultetModel> url_fakulteti = new ArrayList<FakultetModel>();
	private ArrayAdapter<FakultetModel> adapter;
	private ListView lv;
	private EditText inputSearch;
	private SwipeRefreshLayout refreshLayout;
	public static final String url = "http://46.101.185.15//rest/cfddc7067c795d46f676c358dc6aacfcd20c195c";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fakulteti_fragment, container, false);
		lv = (ListView) view.findViewById(R.id.fakulteti_lista);
		inputSearch = (EditText) view.findViewById(R.id.edt_inputSearch);
		refreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh);
		refreshLayout.setColorSchemeResources(android.R.color.background_dark,
				android.R.color.holo_purple, android.R.color.holo_blue_bright,
				android.R.color.holo_green_light);

		adapter = new ArrayAdapter<FakultetModel>(getActivity(),
				android.R.layout.simple_list_item_1, prepareListData());
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FakultetModel test_model = (FakultetModel) parent
						.getItemAtPosition(position);
				Intent faksi = new Intent(getActivity(),
						GoogleMapAktivnost.class);
				faksi.putExtra("fakulteti", test_model);
				startActivity(faksi);

			}
		});

		// rerfresh dela jedino ak je vidljiv prvi fakultet u listi jer bi inaèe
		// refrešal kad god potegneš prema dolje
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem == 0)
					refreshLayout.setEnabled(true);
				else
					refreshLayout.setEnabled(false);
			}
		});

		// složeni refresh fakulteta
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub

				prepareListData();
			}
		});

		// pretraživanje fakulteta
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				((Filterable) FakultetiFragment.this.adapter).getFilter()
						.filter(s);
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

	private List<FakultetModel> prepareListData() {

		JsonObjectRequest objRq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsArray = response.getJSONArray("podaci");
							Log.d("PODACI", jsArray.toString());
							for (int i = 0; i < jsArray.length(); i++) {
								JSONObject jsObject = (JSONObject) jsArray
										.get(i);
								String naziv = jsObject.getString("naziv");
								String url = jsObject.getString("url");
								model = new FakultetModel();
								model.setNaziv(naziv);
								model.SetUrl(url);
								fakulteti.add(model);

							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						adapter.notifyDataSetChanged();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
		AppController.getInstance().addToRequestQueue(objRq);
		return fakulteti;
	}
}
