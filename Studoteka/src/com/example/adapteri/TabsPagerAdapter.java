package com.example.adapteri;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tabovi.InteresiTabFragment;
import com.example.tabovi.OdabraniInteresiTabFragment;
import com.example.tabovi.RezultatInteresaTabFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
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
			frag = new InteresiTabFragment();
			return frag;
		case 1:
			frag = new OdabraniInteresiTabFragment();
			return frag;
		case 2:
			frag = new RezultatInteresaTabFragment();
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
