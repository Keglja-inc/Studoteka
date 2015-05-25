package com.example.interesi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studoteka.R;

class Interes{
	public String name;
	boolean selected = false;
	public Interes(String name) {
		super();
		this.name = name;
	}
	public Interes() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}

public class InteresiAdapter extends ArrayAdapter<Interes> implements Filterable{
	
	
	
	private List<Interes> interesList;
	
	private final Activity context;
	public ArrayList<String> odabrano = new ArrayList<String>();
	public HashSet<String> hash = new HashSet<String>();
	
	public ArrayList<String> filtrirano;
	
	
	public InteresiAdapter(List<Interes> interesList, Activity context) {
		super(context, R.layout.rowbuttonlayout, interesList);
		this.context = context;
		this.interesList = interesList;
	}

	private static class InteresHolder{
		public TextView interesName;
		public CheckBox chkBox;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		InteresHolder holder = null;
		
		if(convertView == null){
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.rowbuttonlayout, null);
			holder = new InteresHolder();
			holder.interesName = (TextView)view.findViewById(R.id.label);
			holder.chkBox = (CheckBox)view.findViewById(R.id.check);
			
			
			holder.chkBox.setTag(position);
			view.setTag(holder);
			
			holder.chkBox.setOnClickListener(myListener);
			
			/*
			holder.chkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
				
					Interes interes = (Interes) holder.chkBox.getTag();
					interes.setSelected(buttonView.isChecked());
					if(holder.chkBox.isChecked()){
						odabrano.add(interes.getName());
						if(odabrano.contains(interes.getName())){
							hash.addAll(odabrano);
							odabrano.clear();
							odabrano.addAll(hash);
							
						}
						Toast.makeText(context, "POdaci "+ interes.getName(), Toast.LENGTH_SHORT).show();
					}
					else if(!holder.chkBox.isChecked()){
						if(odabrano.contains(interes.getName())){
							odabrano.remove(interes.getName());
						}
					}
					Log.d("ODABRANI PODACI", odabrano.toString());
					
				}
			});
			*/
			
			view.setTag(holder);
			holder.chkBox.setTag(interesList.get(position));
		}
		else{
			
			view = convertView;
			((InteresHolder)view.getTag()).chkBox.setTag(interesList.get(position));
			
		
		}
		
		
		InteresHolder holder2 = (InteresHolder) view.getTag();
		holder2.chkBox.setChecked(interesList.get(position).isSelected());
		holder2.interesName.setText(interesList.get(position).getName());
		
		return view;
	
	}
	
	private View.OnClickListener myListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			CheckBox ck = (CheckBox) v;
			Interes interes = (Interes) ck.getTag();
			
			if(ck.isChecked()){
				hash.add(interes.getName());
					odabrano.clear();
					odabrano.addAll(hash);		
				Toast.makeText(context, "POdaci "+ interes.getName(), Toast.LENGTH_SHORT).show();
			}
			else{
				if(odabrano.contains(interes.getName())){
					odabrano.remove(interes.getName());
				}
			}
			
			
			Log.d("ODABRANI PODACI", odabrano.toString());
			
			interes.setSelected(ck.isChecked());
		}
	};

}
