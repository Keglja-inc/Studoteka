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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFakulteti extends FragmentActivity implements OnMapReadyCallback{

	MapFragment mapFragment;
	public String Glvrijednost="";
	private Button btn;
	GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps);
		
		
		String vrijednost = getIntent().getStringExtra("fakulteti");
		Glvrijednost=vrijednost;
		
		addListenerOnButton();
		
		try{
			if(googleMap == null){
				googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
				//MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
				//mapFragment.getMapAsync(this);
				
				LatLng lokacija = getLocationFromAddress(Glvrijednost);
				Log.d("LOGLAT", lokacija.toString());
				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lokacija, 17);
				Marker marker = googleMap.addMarker(new MarkerOptions().position(lokacija).title(Glvrijednost).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				googleMap.animateCamera(update);
				
				//Toast.makeText(getApplicationContext(), "Lokacija: "+lokacija, Toast.LENGTH_SHORT).show();
				
				
				
				if(googleMap == null){
				Toast.makeText(getApplicationContext(), "Sorry! Unable to create map...", Toast.LENGTH_SHORT).show();	
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		
		
	}
	
	public void onClickRestorani(View v){
		Uri intentRestorani = Uri.parse("geo:0,0?q=restaurants,"+ Glvrijednost);
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentRestorani);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}
	
	public void onClickKulturniSadrzaj(View v){
		Uri intentRestorani = Uri.parse("geo:0,0?q=theaters,"+ Glvrijednost);
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentRestorani);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}
	
	public void addListenerOnButton(){
		btn = (Button)findViewById(R.id.web_stranica);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.hr"));
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
	
}
