package com.rombotsteam.ebook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TextFragment extends Fragment {
	
	private ImageView mInitialImage;
	private TextView mPageText;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.text_fragment, container, false);
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setupControls();
	}

	private void setupControls() {

		mPageText = (TextView) getView().findViewById(R.id.textPage);
		mInitialImage = (ImageView) getView().findViewById(R.id.imageInitial);
		
		setText();
	}

	private void setText() {
		String fileName = "red_riding_hood.txt";
		
		AssetManager assetManager = getActivity().getAssets();
	    
		String text = "";
		String firstLetter = "";
		
	    InputStream is=null;
	    try {
	        is = assetManager.open(fileName);
	       
	        int MAX_BUFFER = 1024;
	        byte[] buffer = new byte[MAX_BUFFER];

	        int firstChar = is.read();
	        firstLetter = new String(new byte[]{(byte) firstChar}, "utf-8");
	        
	        while (is.read(buffer) > 0) {
	        	text = text + new String(buffer, "utf-8");
	        }
	        
	    } catch (Exception e1) {  e1.printStackTrace();}
	    
	    setInitialImg(firstLetter);
	    
	    mPageText.setText(text);
	    
	}

	private void setInitialImg(String firstLetter) {
		AssetManager assetManager = getActivity().getAssets();
	    
		firstLetter = firstLetter.toLowerCase();
	    InputStream is=null;
	    try {
	        is = assetManager.open(firstLetter + ".png");
	        Bitmap mBmp = BitmapFactory.decodeStream(is);
	        mInitialImage.setImageBitmap(mBmp);
	    } catch (Exception e1) {  e1.printStackTrace();}
	}
	
}
