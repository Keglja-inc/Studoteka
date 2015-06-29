package com.example.fragmenti;

import com.example.studoteka.OdabirPrijaveAktivnost;
import com.example.studoteka.GlavnaAktivnost;
import com.example.studoteka.R;
import com.example.studoteka.R.layout;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogOutFragment extends Fragment {
	
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.logout, container, false);
		
		AlertDialog.Builder logoutdialog=new AlertDialog.Builder(getActivity());
		
		logoutdialog.setTitle("LogOut");
		
		logoutdialog.setMessage("Do you wrealy want to logout?");
		
		logoutdialog.setCancelable(true)
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			dialog.cancel();
			Intent i=new Intent(getActivity(), GlavnaAktivnost.class);
			startActivity(i);
				
			}
		})
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				Intent i=new Intent(getActivity(), OdabirPrijaveAktivnost.class);
				startActivity(i);
				
			}
		});
		AlertDialog alertDialog = logoutdialog.create();
		 
		// show it
		alertDialog.show();

		return view;
	}

}
