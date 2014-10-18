package com.rombotsteam.ebook;

import android.content.Context;



public class Brush {

	public int mPosX;
	public int mPosY;
	
	public int mRadius;
	
	public int mColor;
	
	public Brush(Context context, int color, int posMouseX, int posMouseY, int radius) {
		mPosX = posMouseX ;
		mPosY = posMouseY ;
		mRadius = radius;
		mColor = color;
	}
	
}
