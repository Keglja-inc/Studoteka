package hr.foi.air.adapteri;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import hr.foi.air.modeli.FakultetModel;
import hr.foi.air.studoteka.R;

/**
 * Adapter u kojem je definiran prikaz fakulteta i postotka podudarnosti
 * interesa tog fakulteta
 * 
 * @author Ivan
 *
 */
public class FakultetListAdapter extends ArrayAdapter<FakultetModel> {

	private Activity activity;
	private ArrayList<FakultetModel> fakultetiModel;

	public FakultetListAdapter(Activity activity,
			ArrayList<FakultetModel> fakultetiModel) {
		super(activity, R.layout.row_f_po_i, fakultetiModel);
		this.activity = activity;
		this.fakultetiModel = fakultetiModel;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row_f_po_i, parent, false);

		TextView txt_nazivFakulteta = (TextView) rowView
				.findViewById(R.id.txt_naziv_fakulteta);
		TextView txt_postotak = (TextView) rowView
				.findViewById(R.id.txt_postotak);

		txt_nazivFakulteta.setText(fakultetiModel.get(position).getNaziv());
		txt_postotak.setText(fakultetiModel.get(position).getPostotak() + " %");

		return rowView;
	}

}
