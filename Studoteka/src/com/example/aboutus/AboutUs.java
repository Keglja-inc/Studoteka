package com.example.aboutus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studoteka.R;

public class AboutUs extends Fragment{
	
	private View view;
	@SuppressWarnings("unused")
	private ImageView logo;
	@SuppressWarnings("unused")
	private TextView opis,dev;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.aboutus, container, false);
		
		logo=(ImageView)view.findViewById(R.id.imageView1);
		
		opis=(TextView)view.findViewById(R.id.opis_aplikacije);
		dev=(TextView)view.findViewById(R.id.developeri);
		
		
		return view;
	}

}
