package com.example.studoteka;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.modeli.FakultetModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Klasa u kojoj su implementirane funkcionalnosti vezane uz prikaz i korištenje
 * google mapa
 * 
 * @author Ivan
 *
 */
public class GoogleMapAktivnost extends FragmentActivity {

	private Button btn;
	private GoogleMap googleMap;
	private FakultetModel fakultetModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps_fragment);

		fakultetModel = (FakultetModel) getIntent().getSerializableExtra(
				"fakulteti");

		SharedPreferences opcije = getSharedPreferences("OPTIONS",
				Context.MODE_PRIVATE);

		addListenerOnButton();

		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();

				String tip = opcije.getString("tip_mape", "");

				if (tip.equals("Normalna")) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				} else if (tip.equals("Hibridna")) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				} else if (tip.equals("Satelitska")) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				} else if (tip.equals("Zemljišna")) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				}

				// MapFragment
				// mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
				// mapFragment.getMapAsync(this);

				LatLng lokacija = getLocationFromAddress(fakultetModel
						.getNaziv());
				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
						lokacija, 17);
				googleMap
						.addMarker(new MarkerOptions()
								.position(lokacija)
								.title(fakultetModel.getNaziv())
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				googleMap.animateCamera(update);

				if (googleMap == null) {
					Toast.makeText(
							getApplicationContext(),
							getResources().getString(
									R.string.msg_kreiraj_mapu_error),
							Toast.LENGTH_SHORT).show();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Omoguæuje prikaz obližnjih restorana odabranog fakulteta na mapi
	 * 
	 * @param v
	 */
	public void onClickRestorani(View v) {
		Uri intentRestorani = Uri.parse("geo:0,0?q=restaurants,"
				+ fakultetModel.getNaziv());
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentRestorani);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}

	/**
	 * Omoguæuje prikaz obližnjih kazališta odabranog fakulteta na mapi
	 * 
	 * @param v
	 */
	public void onClickKulturniSadrzaj(View v) {
		Uri intentRestorani = Uri.parse("geo:0,0?q=theaters,"
				+ fakultetModel.getNaziv());
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentRestorani);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}

	public void addListenerOnButton() {
		btn = (Button) findViewById(R.id.btn_web_stranica);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(fakultetModel.getUrl()));
				startActivity(intent);

			}
		});
	}

	/**
	 * Na temelju imena lokacije raèuna geografsku dužinu i širinu
	 * 
	 * @param strAddress
	 *            Lokacija za koju želimo dobiti geogravsku širinu i dužinu
	 * @return Geogravsku širinu i dužinu lokacije
	 */
	public LatLng getLocationFromAddress(String strAddress) {

		Geocoder coder = new Geocoder(this, Locale.ENGLISH);
		List<Address> address;
		LatLng p1 = null;

		try {
			address = coder.getFromLocationName(strAddress, 5);
			if (address == null) {
				return null;
			}
			Address location = address.get(0);
			location.getLatitude();
			location.getLongitude();

			p1 = new LatLng(location.getLatitude(), location.getLongitude());

		} catch (IOException ioEx) {

			// return null;
			ioEx.printStackTrace();
		}

		return p1;
	}
}
