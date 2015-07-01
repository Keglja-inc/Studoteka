package hr.foi.air.adapteri;

import hr.foi.air.tabovi.InteresiTabFragment;
import hr.foi.air.tabovi.OdabraniInteresiTabFragment;
import hr.foi.air.tabovi.RezultatInteresaTabFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter kojim definiramo prikaz fragmenata u tab layoutu
 * @author Ivan
 *
 */
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
