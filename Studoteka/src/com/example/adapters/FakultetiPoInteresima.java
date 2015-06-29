package com.example.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.modeli.FakultetiModel;
import com.example.studoteka.R;

public class FakultetiPoInteresima extends ArrayAdapter<FakultetiModel> {

	private Activity activity;
	// private ArrayList<String> fakultetiModel, postotak;
	private ArrayList<FakultetiModel> fakultetiModel;

	public FakultetiPoInteresima(Activity activity,
			ArrayList<FakultetiModel> fakultetiModel) {
		super(activity, R.layout.row_f_po_i, fakultetiModel);
		this.activity = activity;
		this.fakultetiModel = fakultetiModel;
	}

	private class ViewHolder {
		private TextView txt_nazivFakulteta, txt_postotak;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row_f_po_i, parent, false);

		TextView txt_nazivFakulteta = (TextView) rowView
				.findViewById(R.id.txt_nazivFakulteta);
		TextView txt_postotak = (TextView) rowView
				.findViewById(R.id.txt_postotak);

		txt_nazivFakulteta.setText(fakultetiModel.get(position).getName());
		txt_postotak.setText(fakultetiModel.get(position).getPostotak());

		FakultetiModel fm = fakultetiModel.get(position);

		return rowView;
	}

}
