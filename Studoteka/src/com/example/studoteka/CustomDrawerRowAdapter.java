package com.example.studoteka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends BaseAdapter{
	private Context contex;
	String [] customDrawerItems;
	//JAKO BITNO INAÈE APP CRKNE JER NEMA SLIÈICE ZA NEKI REDAK
	int images[]={R.drawable.ic_action_about, R.drawable.ic_action_group, R.drawable.ic_action_labels, 
			R.drawable.ic_action_cloud, R.drawable.ic_action_help, R.drawable.ic_action_settings, 
			R.drawable.ic_action_email};
	public CustomAdapter(Context contex) {
		this.contex=contex;
		customDrawerItems = contex.getResources().getStringArray(R.array.nav_drawer_items);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customDrawerItems.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return customDrawerItems[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View custom_row = null;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) contex.getSystemService(contex.LAYOUT_INFLATER_SERVICE);
			custom_row = inflater.inflate(R.layout.custom_row_drawer, parent, false);
		}
		else {
			custom_row = convertView;
		}
		ImageView imgRow = (ImageView)custom_row.findViewById(R.id.imageView1);
		TextView txtRow = (TextView)custom_row.findViewById(R.id.textView1);
		imgRow.setImageResource(images[position]);
		txtRow.setText(customDrawerItems[position]);
		return custom_row;
	}
	
}