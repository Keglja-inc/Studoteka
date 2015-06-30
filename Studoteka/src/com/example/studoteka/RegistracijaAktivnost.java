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
import com.example.modeli.UcenikModel;
import com.example.prijava.RegistracijaLokalna;

public class RegistracijaAktivnost extends FragmentActivity {
	private View view;
	private EditText edt_imeKorisnika, edt_prezimeKorisnika, edt_mailKorisnika,
			edt_lozinkaKorisnika, edt_potvrdaLozinke;
	private String imeKorisnika, prezimeKorisnika, emailKorisnika,
			lozinkaKorisnika, potvrdaLozinke;
	private Button spremi, odustani;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.registracija_fragment);
		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		spremi = (Button) findViewById(R.id.btn_spremi_registracija);
		odustani = (Button) findViewById(R.id.btn_odustani_registracija);
		edt_imeKorisnika = (EditText) findViewById(R.id.edt_ime);
		edt_prezimeKorisnika = (EditText) findViewById(R.id.edt_prezime);
		edt_mailKorisnika = (EditText) findViewById(R.id.edt_mail_adress);
		edt_lozinkaKorisnika = (EditText) findViewById(R.id.edt_lozinka);
		edt_potvrdaLozinke = (EditText) findViewById(R.id.edt_potvrda_lozinke);

		spremi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imeKorisnika = (String) edt_imeKorisnika.getText().toString();
				prezimeKorisnika = (String) edt_prezimeKorisnika.getText()
						.toString();
				emailKorisnika = (String) edt_mailKorisnika.getText()
						.toString();
				lozinkaKorisnika = (String) edt_lozinkaKorisnika.getText()
						.toString();
				potvrdaLozinke = (String) edt_potvrdaLozinke.getText()
						.toString();

				Log.d("ime", imeKorisnika.toString());
				Log.d("prezime", prezimeKorisnika);
				Log.d("email", emailKorisnika);
				Log.d("lozinka", lozinkaKorisnika);

				RegistracijaLokalna registracija = new RegistracijaLokalna(
						imeKorisnika, prezimeKorisnika, emailKorisnika,
						lozinkaKorisnika);
				if (registracija.registracijaKorisnika()) {

					if (lozinkaKorisnika.equals(potvrdaLozinke)) {

						try {
							DBAdapter.addUserData(new UcenikModel(imeKorisnika,
									prezimeKorisnika, emailKorisnika,
									lozinkaKorisnika));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Toast.makeText(
								RegistracijaAktivnost.this,
								getResources().getString(
										R.string.msg_uspjesna_registracija),
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(RegistracijaAktivnost.this,
								PrijavaAktivnost.class);
						startActivity(i);
					}

					else {
						Toast.makeText(
								RegistracijaAktivnost.this,
								getResources().getString(
										R.string.msg_neuspjesna_registracija),
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		odustani.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(RegistracijaAktivnost.this,
						PrijavaAktivnost.class);
				startActivity(i);
			}
		});
	}
}
