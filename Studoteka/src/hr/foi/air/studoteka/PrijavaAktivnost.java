package hr.foi.air.studoteka;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.FacebookSdk;

import hr.foi.air.modeli.UcenikModel;
import hr.foi.air.prijava.PrijavaLokalna;
import hr.foi.air.prijava.PrijavaSucelje;
import hr.foi.air.studoteka.R;

/**
 * Klasa u kojoj su implementirane funkcionalnosti lokalne prijave korisnika i
 * njihovo povezivanje i prikaz na definiranom layoutu
 * 
 * @author Ivan
 *
 */
public class PrijavaAktivnost extends Activity {
	private EditText edt_email = null;
	private EditText edt_lozinka = null;
	private Button btn_prijava, btn_registracija;
	private String password1, email;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.login_screen);
		FacebookSdk.sdkInitialize(getApplicationContext());

		edt_email = (EditText) findViewById(R.id.edit_txt_email);
		edt_lozinka = (EditText) findViewById(R.id.edit_txt_lozinka);

		btn_prijava = (Button) findViewById(R.id.btn_prijava);
		btn_registracija = (Button) findViewById(R.id.btn_registracija);

		SharedPreferences djeljenePostavke = getSharedPreferences("UCENIK",
				Context.MODE_PRIVATE);

		String lozinka = djeljenePostavke.getString(
				getResources().getString(R.string.lozinka_ucenik), "");
		String mailUcenik = djeljenePostavke.getString(getResources()
				.getString(R.string.mail_ucenik), "");

		edt_email.setText((CharSequence) mailUcenik);
		edt_lozinka.setText((CharSequence) lozinka);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		btn_prijava.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = edt_email.getText().toString();
				password1 = edt_lozinka.getText().toString();

				// moraš ruèno dopisati new PrijavaLocal jer ti inaèe neda
				PrijavaSucelje pi = (PrijavaSucelje) new PrijavaLokalna(email,
						password1,
						"http://46.101.185.15/rest/3982bb6a86fec50fd20ee0c3cd6ff474f4ceb78e");

				UcenikModel um = pi.prijava();
				if (um != null) {
					// nastavi s app
					SharedPreferences preferences = getSharedPreferences(
							"UCENIK", Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putInt("id_ucenik", um.getId());
					editor.putString("ime_ucenik", um.getIme());
					editor.putString("prezime_ucenik", um.getPrezime());
					editor.putString("mail_ucenik", um.getMail());
					editor.putString("lozinka_ucenik", password1);

					editor.commit();

					ProgressDialog pd = new ProgressDialog(
							PrijavaAktivnost.this);
					pd.setMessage(getResources().getString(
							R.string.msg_prijava_ucitavanje));
					pd.setCancelable(true);
					pd.show();

					Intent i = new Intent(getApplicationContext(),
							GlavnaAktivnost.class);
					startActivity(i);
				} else {
					ProgressDialog pd = new ProgressDialog(
							PrijavaAktivnost.this);
					pd.setMessage(getResources().getString(
							R.string.msg_prijava_neuspjeh));
					pd.setCancelable(true);
					pd.show();
				}

			}
		});

		btn_registracija.setOnClickListener(new OnClickListener() {

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
