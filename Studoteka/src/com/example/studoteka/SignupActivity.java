package com.example.studoteka;

import java.sql.SQLException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.DBAdapter;
import com.example.db.Profil;
import com.facebook.login.LoginFragment;

public class SignupActivity extends FragmentActivity {
	private View view;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.signup_fragment);
		super.onCreate(savedInstanceState);
		Button spremi = (Button)findViewById(R.id.spremi);
		spremi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText _ime=(EditText)findViewById(R.id.ime);
				String __ime=(String)_ime.getText().toString();
				EditText _prezime=(EditText)findViewById(R.id.prezime);
				String __prezime=(String)_prezime.getText().toString();
				EditText _mail=(EditText)findViewById(R.id.mailadress);
				String __mail=(String)_mail.getText().toString();
				EditText _lozinka=(EditText)findViewById(R.id.password);
				String __lozinka=(String)_lozinka.getText().toString();
				EditText _lozinka2=(EditText)findViewById(R.id.password2);
				String __lozinka2=(String)_lozinka2.getText().toString();
				
				
				if(__lozinka.equals(__lozinka2)){
					
					try {
						DBAdapter.addUserData(new Profil(__ime, __prezime, __mail, __lozinka));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Toast.makeText(SignupActivity.this, "Uspješno ste se regristrirali...", Toast.LENGTH_LONG).show();
					Intent i=new Intent(SignupActivity.this, LoginActivity.class);
					startActivity(i);
				}
				else
				{	
					Toast.makeText(SignupActivity.this, "Niste unijeli dobru lozinku, ponovite...", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
}
