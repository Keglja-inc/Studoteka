package hr.foi.air.tabovi;

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
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import hr.foi.air.adapteri.InteresiAdapter;
import hr.foi.air.modeli.InteresModel;
import hr.foi.air.studoteka.R;
import hr.foi.air.sucelja.TabSucelje;
import hr.foi.air.volley.AppController;

/**
 * Klasa u kojoj su implementirane i prikazane funkionalnosti za dohvat i prikaz
 * interesa svih fakulteta
 * 
 * @author Ivan
 *
 */
public class InteresiTabFragment extends Fragment {
	private View view;
	private ListView lv_interesi;
	private Button btn_proslijedi_interese;
	private EditText edt_input_search;
	private InteresiAdapter interesiAdapter;
	private InteresModel interesModel;
	public static final String url = "http://46.101.185.15/rest/13275cf47a74867fa3d5c02d7719e1ff28e011ba";
	private List<InteresModel> dohvaceniInteresiLista = new ArrayList<InteresModel>();
	private SwipeRefreshLayout srl_refresh_layout;
	private ArrayList<InteresModel> odabraniInteresiLista = new ArrayList<InteresModel>();

	TabSucelje comm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.interesi_lista, container, false);

		lv_interesi = (ListView) view.findViewById(R.id.interesi_lista);
		edt_input_search = (EditText) view.findViewById(R.id.edt_inputSearch);
		btn_proslijedi_interese = (Button) view
				.findViewById(R.id.btn_odabrani_interesi);
		interesiAdapter = new InteresiAdapter(dohvaceniPodaci(), getActivity());
		srl_refresh_layout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_interesi);
		srl_refresh_layout.setColorSchemeResources(
				android.R.color.background_dark, android.R.color.holo_purple,
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light);
		btn_proslijedi_interese.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				odabraniInteresiLista = interesiAdapter.odabrano;

				if (odabraniInteresiLista == null
						|| odabraniInteresiLista.isEmpty()) {
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
				comm.SendData(odabraniInteresiLista);

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

		lv_interesi.setAdapter(interesiAdapter);

		lv_interesi.setOnScrollListener(new OnScrollListener() {

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

		srl_refresh_layout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				dohvaceniPodaci();

			}
		});

		edt_input_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				// ne dela baš najbolje
				interesiAdapter.getFilter().filter(s.toString());
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
	 * Dohvaæanje naziva i ID - a interesa
	 * 
	 * @return Listu objekata tipa InteresModel
	 */
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
								interesModel = new InteresModel(naziv);
								interesModel.setNaziv(naziv);
								interesModel.setId(jsObj.getInt("idInteresa"));
								dohvaceniInteresiLista.add(interesModel);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						interesiAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

		AppController.getInstance().addToRequestQueue(objRq);
		return dohvaceniInteresiLista;
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
