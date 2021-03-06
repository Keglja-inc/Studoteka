package hr.foi.air.studoteka;

import hr.foi.air.adapteri.CustomDrawerRowAdapter;
import hr.foi.air.fragmenti.AboutUs;
import hr.foi.air.fragmenti.AnimacijaFragment;
import hr.foi.air.fragmenti.FakultetiFragment;
import hr.foi.air.fragmenti.LogOutFragment;
import hr.foi.air.fragmenti.ProfilFragment;
import hr.foi.air.fragmenti.SettingsFragment;
import hr.foi.air.studoteka.R;
import hr.foi.air.tabovi.TabsInteresiAktivnost;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;

/**
 * Klasa u kojoj je implementirana funkcionalnost navigacijske ladice
 * 
 * @author Ivan
 *
 */
public class GlavnaAktivnost extends ActionBarActivity implements
		OnItemClickListener {
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] nav_drawer_items;
	private ActionBarDrawerToggle drawerListenerToogle;
	private CustomDrawerRowAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActiveAndroid.initialize(this);

		AnimacijaFragment a = new AnimacijaFragment();
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.addToBackStack(null).replace(R.id.main_content, a).commit();

		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.example.studoteka", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHashPRAVI:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		nav_drawer_items = getResources().getStringArray(
				R.array.nav_drawer_items);
		listView = (ListView) findViewById(R.id.drawer_list);

		// za dobivanje hash keya za facebook sdk
		printKeyHash(this);

		myAdapter = new CustomDrawerRowAdapter(this);
		listView.setAdapter(myAdapter);

		listView.setOnItemClickListener(this);

		drawerListenerToogle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_closed) {
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
			}
		};
		// drawer layoutu ka�emo da je draverListenerToogle objekt koji prima
		// evente onDrawerClosed i Opened
		drawerLayout.setDrawerListener(drawerListenerToogle);
		// mo�emo kliknuti na home button u action baru
		getSupportActionBar().setHomeButtonEnabled(true);
		// dobijemo malu strelicu kraj home ikone te sada mo�emo, ukoliko imamo
		// vi�e ekrana klikom na Home ikonu
		// vratiti se na po�etni ekran
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		// tek tu se pojavljuje ikona za ladicu (3 horizontalne crtice) koju se
		// nalazi u res-drawable-mdpi te
		// kada otvorimo ladicu tada se ona malo smanji
		drawerListenerToogle.syncState();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectItem(position);

	}

	// kada kliknemo na item u listviewu u ladici, u actionbaru nam se kao
	// naslov prika�e ime tog itema
	public void selectItem(int position) {
		// TODO Auto-generated method stub
		listView.setItemChecked(position, true);
		// setTitle(nav_drawer_items[position]);

		Fragment fragment = null;
		switch (position) {
		case 0:
			com.rss.RssFragment rssFragment = new com.rss.RssFragment(
					"http://www.mojamatura.net/index.php?format=feed&type=rss");
			fragment = rssFragment;
			break;
		case 1:
			Intent i2 = new Intent(this, TabsInteresiAktivnost.class);
			startActivity(i2);
			break;

		case 2:
			fragment = new FakultetiFragment();
			break;
		case 3:
			fragment = new ProfilFragment();
			break;
		case 4:
			fragment = new AboutUs();
			break;
		case 5:
			fragment = new SettingsFragment();
			break;
		case 6:
			fragment = new LogOutFragment();
			break;
		default:
			break;
		}
		if (fragment != null) {
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.addToBackStack(null).replace(R.id.main_content, fragment)
					.commit();

			listView.setItemChecked(position, true);
			listView.setSelection(position);
			setTitle(nav_drawer_items[position]);
			drawerLayout.closeDrawer(listView);
		}

		else {
			Log.e("Main Activity", "Error in creating fragments!!!");
		}
	}

	public void setTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	// slu�i da kada se promjeni konfiguracija (npr. veli�ina ekrana) da se
	// paralelno s tim promjene i postavke ladice
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListenerToogle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
		super.onBackPressed();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			SettingsFragment fragment = new SettingsFragment();
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.addToBackStack(null).replace(R.id.main_content, fragment)
					.commit();
			return true;
		}

		// kada kliknemo naHome ikonu sada se otvara ladica ako je bila
		// zatvorena i obrnuto
		if (drawerListenerToogle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// --------------------------------------------------------------------------------------------------------------------
	// funkcije za facebook integraciju
	/**
	 * Vra�a KeyHash aplikacije za registraciju aplikacije za implementaciju
	 * Facebook prijave
	 * 
	 * @param context
	 * @return
	 */
	public static String printKeyHash(Activity context) {
		PackageInfo packageInfo;
		String key = null;
		try {
			// getting application package name, as defined in manifest
			String packageName = context.getApplicationContext()
					.getPackageName();

			// Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_SIGNATURES);

			Log.e("Package Name=", context.getApplicationContext()
					.getPackageName());

			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));

				// String key = new String(Base64.encodeBytes(md.digest()));
				Log.e("Key Hash=", key);
			}
		} catch (NameNotFoundException e1) {
			Log.e("Name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}

}
