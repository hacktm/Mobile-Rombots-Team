package com.rombotsteam.ebook;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ClipartGridAdapter extends ArrayAdapter<Clipart> {

	Context mContext;
	private int mResLayoutId;
	
	public ClipartGridAdapter(Context context, int resource, int textViewResourceId, List<Clipart> objects) {
		super(context, resource, textViewResourceId, objects);
		
		mContext = context;
		mResLayoutId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//View v = super.getView(position, convertView, parent);
		
		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
	        v = inflater.inflate(mResLayoutId, parent, false);
		}
		
		Clipart clipart = getItem(position);
		
		ImageView img = (ImageView) v.findViewById(R.id.image);
		img.setImageBitmap(clipart.mBmp);
        
		return v;
	}
	
	

}
