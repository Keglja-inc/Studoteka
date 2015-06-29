package com.example.fragmenti;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rss.RssItem;
import com.example.rss.RssListListener;
import com.example.rss.RssReader;
import com.example.studoteka.R;
import com.example.studoteka.R.id;
import com.example.studoteka.R.layout;

public class RssFragment extends Fragment{
	private View view;
	TextView mRssFeed;
//	RssTask rss;
	ListView rssItems;
	private RssFragment rssFrg;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub	

			view = inflater.inflate(R.layout.rss_fragment, container, false);
			rssItems = (ListView)view.findViewById(R.id.RssListView);
			rssFrg = this;
			RssTask rss = new RssTask();

		
			rss.execute("http://www.mojamatura.net/index.php?format=feed&type=rss");
			
			Log.d("RSSDretva", Thread.currentThread().getName());

		return view;

	}
	
	public class RssTask extends AsyncTask<String, Void, List<RssItem>> {
		
		@Override
		protected List<RssItem> doInBackground(String... urls) {
			// TODO Auto-generated method stub
			Log.d("RSSDretva", Thread.currentThread().getName());
			try {
				RssReader rssReader = new RssReader(urls[0]);
	Log.d("PORUKA", rssReader.getItems().toString());

				return rssReader.getItems();
				
			} catch (Exception e) {
				Log.e("RSSREADER", e.getMessage());
				// TODO: handle exception
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<RssItem> result) {
			// TODO Auto-generated method stub
			
			final ArrayAdapter<RssItem> rssAdapter = new ArrayAdapter<RssItem>
				(rssFrg.getActivity(), android.R.layout.simple_list_item_1, result);
			rssItems.setAdapter(rssAdapter);
			rssItems.setOnItemClickListener(new RssListListener(result, rssFrg.getActivity()));
		
		}
	}
}