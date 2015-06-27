package com.example.settings;

import java.sql.SQLException;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.db.DBAdapter;
import com.example.studoteka.R;


public class SettingsFragment extends PreferenceFragment{
		DBAdapter baza=new DBAdapter();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
		Bundle b=getActivity().getIntent().getExtras();
		String dolazni=b.getString("prijenos");
		
		String izbaze = null;
		
		try {
			izbaze=DBAdapter.getProfilDataFUll2(dolazni);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] razdioba=izbaze.split(",");
		
		String prvi=razdioba[3].toString();
		String name=razdioba[1].toString()+" "+razdioba[2].toString();
		
		Preference novi=(Preference)findPreference("mail");
		novi.setTitle("Email: "+prvi);
		
		Preference drugi=(Preference)findPreference("ime_prezime");
		drugi.setTitle("Name: "+name);
		
	}
}