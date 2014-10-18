package com.rombotsteam.ebook;

import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

public class Clipart {

	public int mPosX;
	public int mPosY;
	
	public int mWidth;
	public int mHeight;
	
	public String mFilePath;
	
	public Bitmap mBmp;
	
	public boolean mIsSelected = false;
	
	public Clipart(Context context, String resFilename, int posMouseX, int posMouseY) {
		if (!TextUtils.isEmpty(resFilename)) {
		    AssetManager assetManager = context.getAssets();
		    
		    InputStream is=null;
		    try {
		        is = assetManager.open(resFilename);
		        mBmp = BitmapFactory.decodeStream(is);
		    } catch (Exception e1) {  e1.printStackTrace();}	    
		    
		    mFilePath = resFilename;
			
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
