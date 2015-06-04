package com.example.studoteka;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class ChooseLogin extends FragmentActivity implements OnClickListener{
	private Button btn_login_obicni;
	private LoginButton face_button;
	private FacebookLog fl;
	private LoginActivity lg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		setContentView(R.layout.pocetni_ekran);
		
		
		btn_login_obicni = (Button) findViewById(R.id.btn_login_obicno);
		face_button = (LoginButton) findViewById(R.id.login_button);
		
		btn_login_obicni.setOnClickListener(this);
		face_button.setOnClickListener(this);
		
		
		
		/*
		btn_login_obicni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
	
			}
		});
	*/
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login_obicno: 
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);

			
			
			
			/*
			lg = new LoginActivity();
			android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(android.R.id.content, lg).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null);
			ft.commit();break;
			*/
		case R.id.login_button:
			fl = new FacebookLog();
			android.support.v4.app.FragmentManager fm2 = getSupportFragmentManager();
			FragmentTransaction ft2 = fm2.beginTransaction();
			ft2.replace(R.id.frame_login, fl);
			ft2.commit();break;

		default:
			break;
		}
	}
	

}
