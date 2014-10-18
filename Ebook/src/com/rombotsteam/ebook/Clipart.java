package com.rombotsteam.ebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Clipart {

	public int mPosX;
	public int mPosY;
	
	public int mWidth;
	public int mHeight;
	
	public String mName;
	
	public Bitmap mBmp;
	
	public boolean mIsSelected = false;
	
	public Clipart(Context context, int resId, int posMouseX, int posMouseY) {
		if (resId > 0) {
			mBmp = BitmapFactory.decodeResource(context.getResources(), resId);
			
			mWidth = mBmp.getWidth();
			mHeight = mBmp.getHeight();
			
			mPosX = posMouseX - mWidth / 2;
			mPosY = posMouseY - mHeight / 2;
		}
	}
	
	public Clipart(Context context, String filePath) {
		
	}
	
	public boolean isInBounds(int posMouseX, int posMouseY) {
		int boundLeft = mPosX;
		int boundRight = mPosX + mWidth;
		int boundTop = mPosY;
		int boundBottom = mPosY + mHeight;
		
		Log.i("ebook", "bounds " + boundLeft + " " + boundRight + " " + boundTop + " " + boundBottom);
		
		return (posMouseX >= boundLeft && posMouseX <= boundRight &&
			posMouseY >= boundTop && posMouseY <= boundBottom);
	}
	
	public void setPos(int posMouseX, int posMouseY) {
		mPosX = posMouseX - mWidth / 2;
		mPosY = posMouseY - mHeight / 2;
		
		Log.i("ebook", "pos " + mPosX + " " + mPosY);

	}
	
	public String toString() {
		return "px " + mPosX + " px "+ mPosY;
	}
}
