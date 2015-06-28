package com.example.settings;

import java.sql.SQLException;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.example.db.DBAdapter;
import com.example.studoteka.GoogleMapFakulteti;
import com.example.studoteka.R;

public class SettingsFragment extends PreferenceFragment implements
		OnPreferenceChangeListener {
	DBAdapter baza = new DBAdapter();

	private GoogleMapFakulteti gmf = new GoogleMapFakulteti();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
		
		SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
		
		PreferenceManager.setDefaultValues(getActivity(),
				R.xml.main_preferences, false);

		Bundle b = getActivity().getIntent().getExtras();
		String dolazni = b.getString("prijenos");

		String izbaze = null;

		try {
			izbaze = DBAdapter.getProfilDataFUll2(dolazni);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] razdioba = izbaze.split(",");

		String prvi = razdioba[3].toString();
		String name = razdioba[1].toString() + " " + razdioba[2].toString();

		Preference novi = (Preference) findPreference("mail");
		novi.setTitle("Email: " + prvi);

		Preference drugi = (Preference) findPreference("ime_prezime");
		drugi.setTitle("Name: " + name);

		ListPreference lp = (ListPreference) findPreference("lista_mapa");
		
		lp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub

				if (preference.getKey().equals("Normalna")) {
					gmf.PromjeniTipMape("Normalna");
				} else if (preference.getKey().equals("Hibridna")) {
					gmf.PromjeniTipMape("Hibridna");
				} else if (preference.getKey().equals("Satelitska")) {
					gmf.PromjeniTipMape("Satelitska");
				} else if (preference.getKey().equals("Zemljišna")) {
					gmf.PromjeniTipMape("Zemljišna");
				}

				return true;
			}
		});
		
		CheckBoxPreference chp = (CheckBoxPreference) findPreference("pref_rotate_screen");
		chp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				// TODO Auto-generated method stub
				if(newValue.toString().equals("true")){
					setOrientation();
				}
				return true;
			}
		});

	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
	}


	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		
		
		return false;
	}
	
	protected void setOrientation(){
		int currentOrientation = getActivity().getRequestedOrientation();
		if(currentOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
}