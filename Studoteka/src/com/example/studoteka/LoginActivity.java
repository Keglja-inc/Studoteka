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
import com.example.login.PrijavaLocal;

public class LoginActivity extends Activity{
	private EditText username = null;
	private EditText password = null;
	private Button login, signup;
	private TextView attempts;
	private int counter = 3;
	private String username1, password1, email;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.login_screen);
		
		username = (EditText)findViewById(R.id.edit_txt_user_name);
		password = (EditText)findViewById(R.id.edit_txt_password);
		attempts = (TextView)findViewById(R.id.textView5);
		attempts.setText(Integer.toString(counter));
		login = (Button)findViewById(R.id.login_button);
		signup = (Button)findViewById(R.id.btn_signup);
		DBAdapter.init(this);
		
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 username1 = username.getText().toString();
				 password1 = password.getText().toString();
				
				PrijavaLocal prijavaLocal = new PrijavaLocal(username1, password1);
				if(prijavaLocal.prijava()){
					//nastavi s app
					Log.d("USPJEŠNA PRIJAVA", "prijavil sam se");
					
					if(username1.length() > 0 && password1.length() > 0){
						try {
							if(DBAdapter.checkUser(username1, password1)){
								
								email = DBAdapter.getProfilData(password1);
								
								SharedPreferences preferences = getSharedPreferences("EMAIL", getApplicationContext().MODE_PRIVATE);
								Editor editor = preferences.edit();
								editor.putString("POSLANI_MAIL", email);
								editor.commit();
								
								
									ProgressDialog pd = new ProgressDialog(LoginActivity.this);
									pd.setMessage("Loading... please wait");
									pd.setCancelable(true);
									pd.show();
							}
							
							Intent i = new Intent(getApplicationContext(), GlavnaActivity.class);
							
							startActivity(i);
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						else{
							attempts.setBackgroundColor(Color.RED);	
							counter--;
							attempts.setText(Integer.toString(counter));
								ProgressDialog pd = new ProgressDialog(LoginActivity.this);
								pd.setMessage("Wrong credentials...Please try again!");
								pd.setCancelable(true);
								pd.show();	
							if(counter==0){
							   login.setEnabled(false);
							}
						}
					}
			
				else{
					//javi da nekaj ne valja
					Log.d("NEUSPJEŠNA PRIJAVA", "sjebali smo nekaj");
					ProgressDialog pd = new ProgressDialog(LoginActivity.this);
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
				Intent i = new Intent(getApplicationContext(), SignupActivity.class);
				startActivity(i);
			}
		});
	}
}
