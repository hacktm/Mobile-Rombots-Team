package com.rombotsteam.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class BookActivity extends ActionBarActivity implements IPageSwitchListener {

	public static final String INTENT_BOOK_FROM_ASSETS = "INTENT_BOOK_FROM_ASSETS";
	public static final String INTENT_BOOK_FILE_PATH = "INTENT_BOOK_FILE_PATH";
	
	private IPageSwitchListener mPageSwitchListener;
	
	private String mFilePath = "";
	private boolean mIsFileFromAssets = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i = getIntent();
		mFilePath = i.getStringExtra(INTENT_BOOK_FILE_PATH);
		mIsFileFromAssets = i.getBooleanExtra(INTENT_BOOK_FROM_ASSETS, true);

		initFragment();
	}


	private void initFragment() {
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		IlustrationFragment ilustrFrag = new IlustrationFragment();
		ilustrFrag.setPageSwitchListener(this);
		fragmentTransaction.add(R.id.drawPage, ilustrFrag);
		
		TextFragment textFrag = new TextFragment();
		mPageSwitchListener = textFrag;
		textFrag.setEbookFile(mFilePath, mIsFileFromAssets);
		fragmentTransaction.add(R.id.textPage, textFrag);
		
		fragmentTransaction.commit();
	}

	@Override
	public void onNextPage() {
		if (mPageSwitchListener != null) {
			mPageSwitchListener.onNextPage();
		}		
	}

	@Override
	public void onPrevPage() {
		if (mPageSwitchListener != null) {
			mPageSwitchListener.onPrevPage();
		}
	}
	
}
