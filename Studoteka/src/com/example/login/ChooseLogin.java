package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.studoteka.LoginActivity;
import com.example.studoteka.R;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class ChooseLogin extends FragmentActivity {
	private Button btn_login_obicni;
	private LoginButton face_button;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		setContentView(R.layout.pocetni_ekran);
		
		btn_login_obicni = (Button) findViewById(R.id.btn_login_obicno);
		face_button = (LoginButton) findViewById(R.id.login_button);
		
		btn_login_obicni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
	
			}
		});
	}

}
