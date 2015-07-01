package com.example.fragmenti;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.example.studoteka.R;

/**
 * Klasa u kojoj su definirane postavke koje korisnik može mijenjati za
 * personalizirano korištenje aplikacije
 * 
 * @author Deni
 *
 */
public class SettingsFragment extends PreferenceFragment {

	private SharedPreferences opcije;
	private Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);

		PreferenceManager.setDefaultValues(getActivity(),
				R.xml.main_preferences, false);

		ListPreference lp = (ListPreference) findPreference("lista_mapa");
		opcije = getActivity().getSharedPreferences("OPTIONS",
				Context.MODE_PRIVATE);
		editor = opcije.edit();

		lp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				editor.putString("tip_mape", newValue.toString());
				editor.commit();
				return true;
			}
		});

		CheckBoxPreference chp = (CheckBoxPreference) findPreference("pref_pamti_lozinku");
		chp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				editor.putString("pamti_lozinku", newValue.toString());
				editor.commit();
				return true;
			}
		});

		CheckBoxPreference chp2 = (CheckBoxPreference) findPreference("pref_pamti_izracun");
		chp2.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				editor.putString("pamti_izracun", newValue.toString());
				editor.commit();
				return true;
			}
		});
	}
}