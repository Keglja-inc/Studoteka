package com.example.fragmenti;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.studoteka.R;

public class ProfilFragment extends Fragment {
	private View view;
	private ImageView img_user;
	private EditText edt_korisnicko_ime, edt_email;
	private static int LOAD_IMAGE_RESULTS = 1;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.profil, container, false);
		img_user = (ImageView) view.findViewById(R.id.img_user);
		edt_korisnicko_ime = (EditText) view.findViewById(R.id.edt_korisnicko_ime);
		edt_email = (EditText) view.findViewById(R.id.edt_email);
		
		img_user.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, LOAD_IMAGE_RESULTS);
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == LOAD_IMAGE_RESULTS && resultCode == Activity.RESULT_OK && data != null){
			Uri pickedImage = data.getData();
			String [] filePath = {MediaStore.Images.Media.DATA};
			Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
			cursor.moveToFirst();
			String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
			img_user.setImageBitmap(BitmapFactory.decodeFile(imagePath));
			cursor.close();
			img_user.setImageBitmap(BitmapFactory.decodeFile(imagePath));
		}
	}
	
	public void dohvatPodataka(){
		
	}
}
