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

/**
 * Klasa u kojoj je definirana funkcionalnost dohvata liste fakulteta te je
 * definirana povezanost s pripadnim layoutom
 * 
 * @author Ivan
 *
 */
public class FakultetiFragment extends Fragment {

	private View view;
	private FakultetModel fakultetiModel;
	private List<FakultetModel> fakultetiLista = new ArrayList<FakultetModel>();
	private ArrayAdapter<FakultetModel> fakultetiAdapter;
	private ListView ls_fakulteti;
	private EditText edt_input_search;
	private SwipeRefreshLayout srl_refresh_layout;
	public static final String url = "http://46.101.185.15//rest/cfddc7067c795d46f676c358dc6aacfcd20c195c";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fakulteti_fragment, container, false);
		ls_fakulteti = (ListView) view.findViewById(R.id.fakulteti_lista);
		edt_input_search = (EditText) view.findViewById(R.id.edt_inputSearch);
		srl_refresh_layout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh);
		srl_refresh_layout.setColorSchemeResources(
				android.R.color.background_dark, android.R.color.holo_purple,
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light);

		fakultetiAdapter = new ArrayAdapter<FakultetModel>(getActivity(),
				android.R.layout.simple_list_item_1, prepareListData());
		ls_fakulteti.setAdapter(fakultetiAdapter);

		ls_fakulteti.setOnItemClickListener(new OnItemClickListener() {

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

		/**
		 * rerfresh dela jedino ak je vidljiv prvi fakultet u listi jer bi inaèe
		 * refrešal kad god potegneš prema dolje
		 */
		ls_fakulteti.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem == 0)
					srl_refresh_layout.setEnabled(true);
				else
					srl_refresh_layout.setEnabled(false);
			}
		});

		/**
		 * Funkcionalan refresh fakulteta
		 */
		srl_refresh_layout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub

				prepareListData();
			}
		});

		/**
		 * pretraživanje fakulteta
		 */
		edt_input_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				((Filterable) FakultetiFragment.this.fakultetiAdapter)
						.getFilter().filter(s);
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

	/**
	 * Dohvaæa listu fakulteta prema proslijeðenom url - u
	 * 
	 * @return
	 */
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
								fakultetiModel = new FakultetModel();
								fakultetiModel.setNaziv(naziv);
								fakultetiModel.SetUrl(url);
								fakultetiLista.add(fakultetiModel);

							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						fakultetiAdapter.notifyDataSetChanged();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
		AppController.getInstance().addToRequestQueue(objRq);
		return fakultetiLista;
	}
}
