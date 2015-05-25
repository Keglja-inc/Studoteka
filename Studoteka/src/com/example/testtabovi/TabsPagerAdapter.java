package com.example.testtabovi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.interesi.Interesi;
import com.example.interesi.OsobniInteresi;
import com.example.interesi.RezultatInteresa;

public class TabsPagerAdapter extends FragmentPagerAdapter{
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		Fragment frag = null;
		switch (index) {
		case 0:
			frag = new Interesi();
			return frag;
		case 1:
			frag = new OsobniInteresi();
			return frag;
		case 2:
			frag = new RezultatInteresa();
			return frag;
		}
		return frag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
}
