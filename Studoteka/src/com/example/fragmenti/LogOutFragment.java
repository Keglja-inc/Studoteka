package com.example.fragmenti;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studoteka.GlavnaAktivnost;
import com.example.studoteka.OdabirPrijaveAktivnost;
import com.example.studoteka.R;

/**
 * Klasa u kojoj je definirana funkcionalnost fragmenta Odjava te je definirana
 * povezanost s pripadnim layoutom
 * 
 * @author Deni
 *
 */
public class LogOutFragment extends Fragment {

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.logout_fragment, container, false);

		AlertDialog.Builder logoutdialog = new AlertDialog.Builder(
				getActivity());

		logoutdialog.setTitle("LogOut");

		logoutdialog.setMessage(getResources().getString(R.string.msg_odjava));

		logoutdialog
				.setCancelable(true)
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
						Intent i = new Intent(getActivity(),
								GlavnaAktivnost.class);
						startActivity(i);

					}
				})
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								SharedPreferences options = getActivity()
										.getSharedPreferences("OPTIONS",
												Context.MODE_PRIVATE);

								if (options.getString("pamti_lozinku", "false")
										.equals("false")) {
									SharedPreferences djeljenePostavke = getActivity()
											.getSharedPreferences("UCENIK",
													Context.MODE_PRIVATE);

									Editor editor = djeljenePostavke.edit();
									editor.clear();
									editor.commit();

								}

								Intent i = new Intent(getActivity(),
										OdabirPrijaveAktivnost.class);
								startActivity(i);

							}
						});
		AlertDialog alertDialog = logoutdialog.create();

		// show it
		alertDialog.show();

		return view;
	}

}
