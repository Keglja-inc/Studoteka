package com.example.tabovi;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ListView;

import com.example.adapteri.TabsPagerAdapter;
import com.example.modeli.FakultetModel;
import com.example.modeli.InteresModel;
import com.example.studoteka.R;
import com.example.sucelja.TabSucelje;

public class TabsInteresiAktivnost extends FragmentActivity implements TabListener,
		TabSucelje {
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private String[] tabs = { "Interesi", "Odabrani", "Rezultat" };
	private ListView lista;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_za_tabove_test);

		viewPager = (ViewPager) findViewById(R.id.pager);
		lista = (ListView) findViewById(R.id.list_osobni_interesi);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SendData(ArrayList<InteresModel> data) {
		// TODO Auto-generated method stub

		OdabraniInteresiTabFragment oi = (OdabraniInteresiTabFragment) getSupportFragmentManager()
				.findFragmentById(R.id.pager);
		oi.receiveData(data);
	}

	@Override
	public void SendData2(ArrayList<FakultetModel> data) {
		// TODO Auto-generated method stub
		RezultatInteresaTabFragment ri = (RezultatInteresaTabFragment) getSupportFragmentManager()
				.findFragmentById(R.id.pager);
		ri.receiveData2(data);
	}
}
