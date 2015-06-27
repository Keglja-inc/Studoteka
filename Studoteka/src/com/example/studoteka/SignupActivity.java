package com.example.studoteka;

import java.sql.SQLException;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.DBAdapter;
import com.example.db.Profil;
import com.example.login.RegistracijaLocal;

public class SignupActivity extends FragmentActivity {
	private View view;
	private EditText edt_imeKorisnika, edt_prezimeKorisnika, edt_mailKorisnika, edt_lozinkaKorisnika, edt_potvrdaLozinke;
	private String imeKorisnika, prezimeKorisnika, emailKorisnika, lozinkaKorisnika, potvrdaLozinke;
	private Button spremi, odustani;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.signup_fragment);
		super.onCreate(savedInstanceState);
		
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		spremi = (Button)findViewById(R.id.spremi);
		odustani = (Button) findViewById(R.id.odustanigumb);
		edt_imeKorisnika=(EditText)findViewById(R.id.ime);
		edt_prezimeKorisnika=(EditText)findViewById(R.id.prezime);
		edt_mailKorisnika=(EditText)findViewById(R.id.mailadress);
		edt_lozinkaKorisnika=(EditText)findViewById(R.id.password);
		edt_potvrdaLozinke=(EditText)findViewById(R.id.password2);
		
		
		spremi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imeKorisnika=(String)edt_imeKorisnika.getText().toString();
				prezimeKorisnika=(String)edt_prezimeKorisnika.getText().toString();
				emailKorisnika=(String)edt_mailKorisnika.getText().toString();
				lozinkaKorisnika=(String)edt_lozinkaKorisnika.getText().toString();
				potvrdaLozinke=(String)edt_potvrdaLozinke.getText().toString();
				
				Log.d("ime", imeKorisnika.toString());
				Log.d("prezime", prezimeKorisnika);
				Log.d("email", emailKorisnika);
				Log.d("lozinka", lozinkaKorisnika);
				
				RegistracijaLocal registracija = new RegistracijaLocal(imeKorisnika, prezimeKorisnika, emailKorisnika, lozinkaKorisnika);
				if(registracija.registracijaKorisnika()){
					Log.d("USPJEŠNO STE SE REGISTRIRALI", "FALA KURCU DA DELA NEKAJ NAPOKON");
					
					if(lozinkaKorisnika.equals(potvrdaLozinke)){
						
						try {
							DBAdapter.addUserData(new Profil(imeKorisnika, prezimeKorisnika, emailKorisnika, lozinkaKorisnika));
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
			}
		});
		
		odustani.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SignupActivity.this, "Uspješno ste se regristrirali...", Toast.LENGTH_LONG).show();
				Intent i=new Intent(SignupActivity.this, LoginActivity.class);
				startActivity(i);
			}
		});
	}
}
