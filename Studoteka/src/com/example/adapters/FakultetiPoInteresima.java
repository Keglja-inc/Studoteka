package com.example.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.modeli.FakultetiModel;
import com.example.studoteka.R;

public class FakultetiPoInteresima extends BaseAdapter {

	private Activity activity;
//	private ArrayList<String> fakultetiModel, postotak;
	private ArrayList<FakultetiModel> fakultetiModel;

	
	
	public FakultetiPoInteresima(Activity activity,
		ArrayList<FakultetiModel> fakultetiModel) {
	super();
	this.activity = activity;
	this.fakultetiModel = fakultetiModel;
}

	/*
	public FakultetiPoInteresima(Activity activity,
			ArrayList<String> fakultetiModel, ArrayList<String> postotak) {
		super();
		this.activity = activity;
		this.fakultetiModel = fakultetiModel;
		this.postotak = postotak;
	}
	*/

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fakultetiModel.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
		private TextView txt_nazivFakulteta, txt_postotak;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_f_po_i, null);
			holder = new ViewHolder();
			holder.txt_nazivFakulteta = (TextView) convertView
					.findViewById(R.id.txt_nazivFakulteta);
			holder.txt_postotak = (TextView) convertView
					.findViewById(R.id.txt_postotak);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		FakultetiModel fm = fakultetiModel.get(position);
		holder.txt_nazivFakulteta.setText(fm.getName());
		holder.txt_postotak.setText(fm.getPostotak());
		
//		holder.txt_nazivFakulteta.setText(fakultetiModel.get(position));
//		holder.txt_postotak.setText(postotak.get(position));

		return convertView;
	}

}
