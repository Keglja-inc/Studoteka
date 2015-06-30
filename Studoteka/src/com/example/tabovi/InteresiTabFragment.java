package com.example.tabovi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.adapteri.InteresiAdapter;
import com.example.modeli.InteresModel;
import com.example.studoteka.R;
import com.example.sucelja.TabSucelje;
import com.example.volley.AppController;

public class InteresiTabFragment extends Fragment {
	private View view;
	private ListView lv;
	private Button btn;
	private EditText inputSearch;
	private ArrayList<InteresModel> interesLista;
	private InteresiAdapter adapter;
	private InteresModel interes;
	private CheckBox ck;
	public static final String url = "http://46.101.185.15/rest/13275cf47a74867fa3d5c02d7719e1ff28e011ba";
	private List<InteresModel> inter = new ArrayList<InteresModel>();
	private SwipeRefreshLayout refreshLayout;
	private ArrayList<InteresModel> searchResult = new ArrayList<InteresModel>();
	private ArrayList<InteresModel> primljeno = new ArrayList<InteresModel>();

	TabSucelje comm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.interesi_lista, container, false);

		interesLista = new ArrayList<InteresModel>();
		lv = (ListView) view.findViewById(R.id.interesi_lista);
		ck = (CheckBox) view.findViewById(R.id.chk_check);
		inputSearch = (EditText) view.findViewById(R.id.edt_inputSearch);
		btn = (Button) view.findViewById(R.id.btn_odabrani_interesi);
		adapter = new InteresiAdapter(dohvaceniPodaci(), getActivity());
		refreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_interesi);
		refreshLayout.setColorSchemeResources(android.R.color.background_dark,
				android.R.color.holo_purple, android.R.color.holo_blue_bright,
				android.R.color.holo_green_light);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				primljeno = adapter.odabrano;

				if (primljeno == null || primljeno.isEmpty()) {
					ProgressDialog pd = new ProgressDialog(getActivity());
					pd.setMessage(getResources().getString(
							R.string.msg_neodabrani_interesi));
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
					return;

				}
				comm.SendData(primljeno);

				ProgressDialog pd = new ProgressDialog(getActivity());
				pd.setMessage(getResources().getString(
						R.string.msg_odabrani_interesi));
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

		lv.setAdapter(adapter);

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

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				dohvaceniPodaci();

			}
		});

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				// ne dela baš najbolje
				adapter.getFilter().filter(s.toString());
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

	private List<InteresModel> dohvaceniPodaci() {
		JsonObjectRequest objRq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsArray = response.getJSONArray("podaci");
							for (int i = 0; i < jsArray.length(); i++) {
								JSONObject jsObj = (JSONObject) jsArray.get(i);
								String naziv = jsObj.getString("naziv");
								interes = new InteresModel(naziv);
								interes.setName(naziv);
								interes.setId(jsObj.getInt("idInteresa"));
								inter.add(interes);
							}

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
			comm = (TabSucelje) getActivity();
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement interface");
		}
	}
}
