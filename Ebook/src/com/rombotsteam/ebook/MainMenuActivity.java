package com.rombotsteam.ebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainMenuActivity extends Activity {

	private ImageButton mOpenBookBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		
		setupControls();
	}

	private void setupControls() {
		
		mOpenBookBtn = (ImageButton) findViewById(R.id.buttonOpenBook);
		mOpenBookBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startBookActivity();
			}
		});
	}

	protected void startBookActivity() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	
}
