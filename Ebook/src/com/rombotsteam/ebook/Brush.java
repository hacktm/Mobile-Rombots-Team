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
	
	public boolean isInBounds(int posMouseX, int posMouseY) {
		int boundLeft = mPosX - mRadius;
		int boundRight = mPosX + mRadius;
		int boundTop = mPosY - mRadius;
		int boundBottom = mPosY + mRadius;
		
		return (posMouseX >= boundLeft && posMouseX <= boundRight &&
			posMouseY >= boundTop && posMouseY <= boundBottom);
	}
	
}
