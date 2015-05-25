package com.example.studoteka;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.db.DBAdapter;

public class LoginActivity extends FragmentActivity {
	private EditText username = null;
	private EditText password = null;
	private Button login, signup;
	private TextView attempts;
	int counter = 3;
	ProgressDialog  d ;
	private View view;
	
	
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
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username1 = username.getText().toString();
				String password1 = password.getText().toString();
				try {
					if(username1.length() > 0 && password1.length() > 0){
						if(DBAdapter.checkUser(username1, password1)){
							Intent i = new Intent(getApplicationContext(), GlavnaActivity.class);
							startActivity(i);
							cancelProgressDialog("Loading...", "Please wait...", "Cancel");
						}
						else{
							attempts.setBackgroundColor(Color.RED);	
							counter--;
							attempts.setText(Integer.toString(counter));
							cancelProgressDialog("Warning!!", "Wrong credentials...", "Cancel");
							if(counter==0){
							   login.setEnabled(false);
							}
							
						}
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					cancelProgressDialog("ERROR", "Something has gone terrible wrong!!!Try again later...", "Cancel");
					//d = ProgressDialog.show(getApplicationContext(), "ERROR", "Something has gone terrible wrong!!!Try again later...");
					//d.setCancelable(true);
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
	
/*	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.login_screen, container, false);
		
		username = (EditText)view.findViewById(R.id.edit_txt_user_name);
		password = (EditText)view.findViewById(R.id.edit_txt_password);
		attempts = (TextView)view.findViewById(R.id.textView5);
		attempts.setText(Integer.toString(counter));
		login = (Button)view.findViewById(R.id.login_button);
		signup = (Button)view.findViewById(R.id.btn_signup);
		DBAdapter.init(getActivity());
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username1 = username.getText().toString();
				String password1 = password.getText().toString();
				try {
					if(username1.length() > 0 && password1.length() > 0){
						if(DBAdapter.checkUser(username1, password1)){
							Intent i = new Intent(getActivity(), GlavnaActivity.class);
							startActivity(i);
							cancelProgressDialog("Login new user", "Please wait...", "Cancel");
						}
						else{
							attempts.setBackgroundColor(Color.RED);	
							counter--;
							attempts.setText(Integer.toString(counter));
							cancelProgressDialog("User does not exists", "Please signup or try again...", "Cancel");
							
							if(counter==0){
							   login.setEnabled(false);
							}
							
						}
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					cancelProgressDialog("ERROR", "Something has gone terrible wrong!!!Try again later...", "Cancel");
				}
			}
		});
		
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), SignupFragment.class);
				startActivity(i);
				
			}
		});
		
		
		return view;
	}
*/	
	
	@SuppressWarnings("deprecation")
	private void cancelProgressDialog(String title, String message, String buttonText){
		ProgressDialog cancelDialog = new ProgressDialog(this);
		cancelDialog.setTitle(title);
		cancelDialog.setMessage(message);
		cancelDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				dialog.dismiss();
				
				
			}
		});
		cancelDialog.show();
	}
	
}
