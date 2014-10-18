package com.rombotsteam.ebook;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity implements IPageSwitchListener {

	private IPageSwitchListener mPageSwitchListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initFragment();
		
		setupActionBar();
	}

	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
		
		actionBar.hide();
	}

	private void initFragment() {
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		IlustrationFragment ilustrFrag = new IlustrationFragment();
		ilustrFrag.setPageSwitchListener(this);
		fragmentTransaction.add(R.id.drawPage, ilustrFrag);
		
		TextFragment textFrag = new TextFragment();
		mPageSwitchListener = textFrag;
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
