package com.example.studoteka;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookLog extends android.support.v4.app.Fragment {

	private View view;
	private LoginButton btn_facebook;
	private AccessTokenTracker mTokenTracker;
	private ProfileTracker mProfileTracker;
	private CallbackManager callbackManager;
	private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {

		@Override
		public void onSuccess(LoginResult result) {
			// TODO Auto-generated method stub
			AccessToken accessToken = result.getAccessToken();
			Profile profile = Profile.getCurrentProfile();
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(FacebookException error) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public FacebookLog(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		mTokenTracker = new AccessTokenTracker() {
			
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
					AccessToken currentAccessToken) {
				// TODO Auto-generated method stub
				
			}
		};
		mProfileTracker = new ProfileTracker() {
			
			@Override
			protected void onCurrentProfileChanged(Profile oldProfile,
					Profile currentProfile) {
				// TODO Auto-generated method stub
				
			}
		};
		mTokenTracker.startTracking();
		mProfileTracker.startTracking();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.pocetni_ekran, container, false);
		
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		btn_facebook = (LoginButton) view.findViewById(R.id.login_button);
		btn_facebook.setFragment(this);
		btn_facebook.registerCallback(callbackManager, callback);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mTokenTracker.stopTracking();
		mProfileTracker.stopTracking();
	}
}
