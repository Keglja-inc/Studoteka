package com.example.fragmenti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.studoteka.R;

/**
 * Klasa u kojoj je definirana poèetna animacija te je definirana povezanost s
 * pripadnim layoutom
 * 
 * @author Deni
 *
 */
public class AnimacijaFragment extends Fragment {

	View view;
	android.view.animation.Animation anim;
	ImageView img_animacija;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.animation, container, false);

		anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

		img_animacija = (ImageView) view.findViewById(R.id.img_rotate_picture);

		this.view.setVisibility(View.VISIBLE);
		img_animacija.startAnimation(anim);

		return view;
	}

}
