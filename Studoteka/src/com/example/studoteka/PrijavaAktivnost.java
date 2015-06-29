package com.example.studoteka;

import java.sql.SQLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.db.DBAdapter;
import com.example.prijava.PrijavaSucelje;
import com.example.prijava.PrijavaLokalna;

public class PrijavaAktivnost extends Activity {
	private EditText username = null;
	private EditText password = null;
	private Button login, signup;
	private String username1, password1, email;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.login_screen);

		username = (EditText) findViewById(R.id.edit_txt_email);
		password = (EditText) findViewById(R.id.edit_txt_lozinka);

		login = (Button) findViewById(R.id.login_button);
		signup = (Button) findViewById(R.id.btn_signup);
		DBAdapter.init(this);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username1 = username.getText().toString();
				password1 = password.getText().toString();

				// moraš ruèno dopisati new PrijavaLocal jer ti inaèe neda
				PrijavaSucelje pi = (PrijavaSucelje) new PrijavaLokalna(
						username1, password1,
						"http://46.101.185.15/rest/3982bb6a86fec50fd20ee0c3cd6ff474f4ceb78e");

				if (pi.prijava()) {
					// nastavi s app
					Log.d("USPJEŠNA PRIJAVA", "prijavil sam se");

					if (username1.length() > 0 && password1.length() > 0) {
						try {
							if (DBAdapter.checkUser(username1, password1)) {

								email = DBAdapter.getProfilData(password1);

								SharedPreferences preferences = getSharedPreferences(
										"EMAIL",
										getApplicationContext().MODE_PRIVATE);
								Editor editor = preferences.edit();
								editor.putString("POSLANI_MAIL", email);
								editor.commit();

								ProgressDialog pd = new ProgressDialog(
										PrijavaAktivnost.this);
								pd.setMessage("Loading... please wait");
								pd.setCancelable(true);
								pd.show();
							}

							Bundle novi = new Bundle();
							novi.putString("prijenos", username1);

							Intent i = new Intent(getApplicationContext(),
									GlavnaAktivnost.class);
							i.putExtras(novi);
							startActivity(i);

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ProgressDialog pd = new ProgressDialog(
								PrijavaAktivnost.this);
						pd.setMessage("Wrong credentials...Please try again!");
						pd.setCancelable(true);
						pd.show();
					}
				}

				else {
					// javi da nekaj ne valja
					Log.d("NEUSPJEŠNA PRIJAVA", "sjebali smo nekaj");
					ProgressDialog pd = new ProgressDialog(
							PrijavaAktivnost.this);
					pd.setMessage("Something has gone terrible wrong!!!Try again later...");
					pd.setCancelable(true);
					pd.show();
				}

			}
		});
		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						RegistracijaAktivnost.class);
				startActivity(i);
			}
		});
	}

}
