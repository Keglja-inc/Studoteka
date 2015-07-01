package com.example.adapteri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modeli.InteresModel;
import com.example.studoteka.R;

/**
 * Adapter u kojemu je definiran prikaz interesa u fragmentu InteresiTabFragment
 * 
 * @author Ivan
 *
 */
public class InteresiAdapter extends ArrayAdapter<InteresModel> {

	private List<InteresModel> interesList;
	private List<InteresModel> listaInteresa;
	private InteresiFilter filter;
	private ArrayList<InteresModel> filtriraniInteresi = new ArrayList<InteresModel>();
	private final Activity context;
	public ArrayList<InteresModel> odabrano = new ArrayList<InteresModel>();
	private HashSet<InteresModel> hash = new HashSet<InteresModel>();

	public InteresiAdapter(List<InteresModel> interesList, Activity context) {
		super(context, R.layout.rowbuttonlayout, interesList);
		this.context = context;
		this.interesList = interesList;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if (filter == null) {
			filter = new InteresiFilter();
		}
		return filter;
	}

	private static class InteresHolder {
		public TextView interesName;
		public CheckBox chkBox;

	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		InteresHolder holder = null;

		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.rowbuttonlayout, null);
			holder = new InteresHolder();
			holder.interesName = (TextView) view
					.findViewById(R.id.txt_interesi);
			holder.chkBox = (CheckBox) view.findViewById(R.id.chk_check);

			holder.chkBox.setTag(position);
			view.setTag(holder);

			holder.chkBox.setOnClickListener(myListener);

			view.setTag(holder);
			holder.chkBox.setTag(interesList.get(position));
		} else {

			view = convertView;
			((InteresHolder) view.getTag()).chkBox.setTag(interesList
					.get(position));

		}

		InteresHolder holder2 = (InteresHolder) view.getTag();
		holder2.chkBox.setChecked(interesList.get(position).isSelected());
		holder2.interesName.setText(interesList.get(position).getNaziv());

		return view;

	}

	private View.OnClickListener myListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			CheckBox ck = (CheckBox) v;
			InteresModel interes = (InteresModel) ck.getTag();

			if (ck.isChecked()) {
				hash.add(interes);
				odabrano.clear();
				odabrano.addAll(hash);
				Toast.makeText(context, "POdaci " + interes.getNaziv(),
						Toast.LENGTH_SHORT).show();
			} else {
				if (odabrano.contains(interes.getNaziv())) {
					odabrano.remove(interes.getNaziv());
				}
			}

			interes.setSelected(ck.isChecked());
		}
	};

	@SuppressLint("DefaultLocale")
	private class InteresiFilter extends Filter {

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			listaInteresa = (List<InteresModel>) results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0, k = listaInteresa.size(); i < k; i++) {
				add(listaInteresa.get(i));
				notifyDataSetChanged();
			}
		}

		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				for (int i = 0, l = interesList.size(); i < l; i++) {
					InteresModel interes = interesList.get(i);
					if (interes.toString().toLowerCase().contains(constraint)) {
						filtriraniInteresi.add(interes);
					}
				}
				result.count = filtriraniInteresi.size();
				result.values = filtriraniInteresi;
			} else {
				synchronized (this) {
					result.values = interesList;
					result.count = interesList.size();
				}
			}
			return result;
		}
	};
}
