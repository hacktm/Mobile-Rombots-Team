package com.rombotsteam.ebook;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class BookActivity extends ActionBarActivity implements IPageSwitchListener {

	public static final String INTENT_BOOK_FROM_ASSETS = "INTENT_BOOK_FROM_ASSETS";
	public static final String INTENT_BOOK_FILE_PATH = "INTENT_BOOK_FILE_PATH";
	private static final String WORDS_IMG_DICT_FILE = "words_img.json";
	
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
		
		initWordsImagesDictionary(WORDS_IMG_DICT_FILE);
		
		//TEST
		String filenam = WordsImageDictionaryUtil.getImageFilenameForWord("wolf");
		
		ArrayList<String> files = FileUtil.getFilesMatchingName(this, "clipart", "flower");
		for (String f : files) {
			Log.i("ebook", "img: " + f);
		}
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
	
	private void initWordsImagesDictionary(String wordsImgDictFilePath) {
		String jsonContent = FileUtil.getFileContents(this, wordsImgDictFilePath, true);
		WordsImageDictionaryUtil.createWordsImageDict(jsonContent);
	}
	
}
