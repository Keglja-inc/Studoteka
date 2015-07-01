package com.rss;

import java.util.List;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rss.RssItem;
import com.example.rss.RssListListener;
import com.example.rss.RssReader;
import com.rssproject.rsslibraryproject.R;

public class RssFragment extends Fragment {

	private View view;
	TextView mRssFeed;
	ListView rssItems;
	List<RssItem> listItems;
	String link;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.rss_fragment, container, false);
		GetRSSDataTask rss = new GetRSSDataTask();
		rss.execute(getLink());

		return view;

	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public RssFragment(String link) {
		super();
		this.link = link;
	}

	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {

		Dialog novi = new Dialog(getActivity());
		RssReader rssReader = new RssReader(getLink());
		ListView itcItems = (ListView) view.findViewById(R.id.RssListView);

		protected void onPreExecute() {

		};

		protected void onPostExecute(List<RssItem> result) {
			novi.setTitle(getResources().getString(R.string.msg_rss_poruka));
			novi.show();

			try {
				ArrayAdapter<RssItem> adapter;
				adapter = new ArrayAdapter<RssItem>(getActivity(),
						android.R.layout.simple_list_item_1,
						rssReader.getItems());

				itcItems.setAdapter(adapter);

				itcItems.setOnItemClickListener(new RssListListener(rssReader
						.getItems(), getActivity()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			novi.cancel();
		}

		@Override
		protected List<RssItem> doInBackground(String... params) {
			return null;
		}
	}

}
