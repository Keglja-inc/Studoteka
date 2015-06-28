package com.example.studoteka;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.modeli.FakultetiModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFakulteti extends FragmentActivity{

	private Button btn;
	private GoogleMap googleMap;
	private FakultetiModel fakultet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps);

		fakultet = (FakultetiModel) getIntent().getSerializableExtra("fakulteti");
		//Log.d("KAJ SAM DOBIL", fakultet.getName());
			
		addListenerOnButton();
		
		try{
			if(googleMap == null){
				googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
				//MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
				//mapFragment.getMapAsync(this);
				
				LatLng lokacija = getLocationFromAddress(fakultet.getName());
				Log.d("LOGLAT", lokacija.toString());
				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lokacija, 17);
				googleMap.addMarker(new MarkerOptions().position(lokacija).title(fakultet.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				googleMap.animateCamera(update);
				
				if(googleMap == null){
				Toast.makeText(getApplicationContext(), "Sorry! Unable to create map...", Toast.LENGTH_SHORT).show();	
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void onClickRestorani(View v){
		Uri intentRestorani = Uri.parse("geo:0,0?q=restaurants,"+ fakultet.getName());
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentRestorani);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}
	
	public void onClickKulturniSadrzaj(View v){
		Uri intentRestorani = Uri.parse("geo:0,0?q=theaters,"+ fakultet.getName());
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentRestorani);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}
	
	public void addListenerOnButton(){
		btn = (Button)findViewById(R.id.web_stranica);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fakultet.getUrl()));
				startActivity(intent);
				
			}
		});
	}
	
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

	        p1 = new LatLng(location.getLatitude(), location.getLongitude() );

	    } catch (IOException ioEx) {

	    	//return null;
	        ioEx.printStackTrace();
	    }
	    
	    return p1;
	}
	
	public void PromjeniTipMape (String tip){
		googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

		if(tip == "Normalna"){
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
		else if (tip == "Hibridna") {
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		}
		else if (tip == "Satelitska") {
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		}
		else if (tip == "Zemlji�na") {
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		}
	}
}
