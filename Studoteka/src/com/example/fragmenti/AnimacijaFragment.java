package com.example.fragmenti;

import com.example.studoteka.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class AnimacijaFragment extends Fragment{
	
	View view;
	android.view.animation.Animation anim;
	ImageView image;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view=inflater.inflate(R.layout.animation, container, false);
		
		anim=AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
		
		image=(ImageView)view.findViewById(R.id.img_rotate_picture);
		
		this.view.setVisibility(View.VISIBLE);
		image.startAnimation(anim);
		
		return view;
	}
	


	

}
