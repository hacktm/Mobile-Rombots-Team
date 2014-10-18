package com.rombotsteam.ebook;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initFragment();
	}

	private void initFragment() {
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.add(R.id.drawPage, new IlustrationFragment());
		
		fragmentTransaction.add(R.id.textPage, new TextFragment());
		
		fragmentTransaction.commit();
	}
	
}
