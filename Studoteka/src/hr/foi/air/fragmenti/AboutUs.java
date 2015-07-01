package hr.foi.air.fragmenti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hr.foi.air.studoteka.R;

/**
 * Klasa u kojemu je definirana funkcionalnost fragmenta "O nama" te je definirana povezanost s pripadnim layoutom
 * @author Deni
 *
 */
public class AboutUs extends Fragment{
	
	private View view;
	@SuppressWarnings("unused")
	private ImageView logo;
	@SuppressWarnings("unused")
	private TextView txt_opis,txt_developeri;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.o_nama_fragment, container, false);
		
		logo=(ImageView)view.findViewById(R.id.img_ikona);
		
		txt_opis=(TextView)view.findViewById(R.id.txt_opis_aplikacije);
		txt_developeri=(TextView)view.findViewById(R.id.txt_developeri	);
		
		
		return view;
	}

}
