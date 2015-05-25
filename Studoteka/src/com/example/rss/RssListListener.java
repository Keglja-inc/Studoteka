package com.example.rss;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class RssListListener implements OnItemClickListener {

	List<RssItem> listItems;
	Activity activity;
//	Intent intent;
	
	public RssListListener(List<RssItem> listItems, Activity activity) {
		// TODO Auto-generated constructor stub
		this.listItems = listItems;
		this.activity = activity;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(listItems.get(position).getLink()));
		activity.startActivity(intent);
	}
}
