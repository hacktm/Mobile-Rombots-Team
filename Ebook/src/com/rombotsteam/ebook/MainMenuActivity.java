package com.rombotsteam.ebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainMenuActivity extends Activity implements IFileChooserListener {

	protected static final String RRH_ASSET_FILENAME = "red_riding_hood.txt";
	protected static final String GOLDIELOCKS_ASSET_FILENAME = "goldielocks.txt";
	private ImageButton mOpenBookBtn;
	private ImageButton mOpenRRHBtn;
	private ImageButton mOpenGoldielocksBtn;

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
				FileChooserDialog fileDlg = new FileChooserDialog();
				fileDlg.showFileChooserDialog(MainMenuActivity.this, MainMenuActivity.this);
			}
		});
		
		mOpenRRHBtn = (ImageButton) findViewById(R.id.buttonOpenRedRidingHoodBook);
		mOpenRRHBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startBookActivity(RRH_ASSET_FILENAME, true);
			}
		});
		
		mOpenGoldielocksBtn = (ImageButton) findViewById(R.id.buttonOpenGoldielocksBook);
		mOpenGoldielocksBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startBookActivity(GOLDIELOCKS_ASSET_FILENAME, true);
			}
		});
		
	}

	protected void startBookActivity(String filePath, boolean isBookFromAssets) {
		Intent i = new Intent(this, BookActivity.class);
		i.putExtra(BookActivity.INTENT_BOOK_FILE_PATH, filePath);
		i.putExtra(BookActivity.INTENT_BOOK_FROM_ASSETS, isBookFromAssets);
		
		startActivity(i);
	}

	@Override
	public void onFileChosen(String filePath) {
		startBookActivity(filePath, false);
	}

	
}
