package com.rombotsteam.ebook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TextFragment extends Fragment implements IPageSwitchListener {
	
	
	private ImageView mInitialImage;
	private TextView mPageText;
	
	private int mCurrentPageIdx = 0;
	private ArrayList<String> mPages;
	
	private ImageButton mPrevPageBtn;
	
	private ToggleButton mEnableTTSBtn;
	
	private TextToSpeech mTTS;
	private String mCurrentPageText = "";
	private boolean mIsTTSPlaying = false;
	
	private String mFilePath = "";
	private boolean mIsBookFromAssets = true;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.text_fragment, container, false);
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setupControls();
		
		initTTS();
	}

	private void setupControls() {

		mPageText = (TextView) getView().findViewById(R.id.textPage);
		mInitialImage = (ImageView) getView().findViewById(R.id.imageInitial);
		
		mCurrentPageIdx = 0;
		
		loadTextFromFile(mFilePath, mIsBookFromAssets);
		
		setFont("droidserif.ttf");
		
		mPrevPageBtn = (ImageButton) getView().findViewById(R.id.buttonPrevPage);
		mPrevPageBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onPrevPage();
			}
		});
		
		mEnableTTSBtn = (ToggleButton) getView().findViewById(R.id.buttonTTS);
		
		mEnableTTSBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				enableTTS(mCurrentPageText, isChecked);
			}
		});
	}

	private void setFont(String fontFilePath) {
		Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), fontFilePath);
		mPageText.setTypeface(myTypeface);
	}

	private void loadTextFromFile(String fileName, boolean isBookFromAssets) {
		String text = FileUtil.getFileContents(getActivity(), fileName, isBookFromAssets);	
	    
	    mPages = PageUtil.getPages(text);
	    
		setPage();
	}

	private void setPage() {
		mCurrentPageText = mPages.get(mCurrentPageIdx);
		
	    String firstLetter = ""+ mCurrentPageText.charAt(0);
	    setInitialImg(firstLetter);	    

		String pageTextWithSpace = "               " + mCurrentPageText.substring(1);
	    mPageText.setText(pageTextWithSpace);
	    
	    getWordsFromBackend(mCurrentPageText );
	}

	private void getWordsFromBackend(final String pageText) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
			    BackendHttpClient client = new BackendHttpClient();
			    client.postPage(pageText);
			}    	
	    });
	    t.start();
	}

	private void setInitialImg(String firstLetter) {
		AssetManager assetManager = getActivity().getAssets();
	    
		firstLetter = firstLetter.toLowerCase();
	    InputStream is=null;
	    try {
	        is = assetManager.open(firstLetter + ".png");
	        Bitmap mBmp = BitmapFactory.decodeStream(is);
	        mInitialImage.setImageBitmap(mBmp);
	    } catch (Exception e1) {  e1.printStackTrace();}
	}

	@Override
	public void onNextPage() {
		if (mCurrentPageIdx < mPages.size()-1) {
			mCurrentPageIdx++;
			
			setPage();
		}
	}

	@Override
	public void onPrevPage() {
		if (mCurrentPageIdx > 0) {
			mCurrentPageIdx--;
			
			setPage();
		}
	}
	
	private void initTTS() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				mTTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
					   @Override
					   public void onInit(int status) {
						   mTTS.setLanguage(Locale.UK);
						   Log.i("ebook", "TTS init done");
					   }
					});
			}
		});
		t.start();
	}
	
	private void enableTTS(final String pageText, boolean enable) {
		mIsTTSPlaying = enable;
		if (enable) {
			mTTS.speak(pageText, TextToSpeech.QUEUE_FLUSH, null);
		} else {
			if (mTTS.isSpeaking()) {
				mTTS.stop();
			}
		}
	}
	
	public void setEbookFile(String filePath, boolean isBookFromAssets) {
		mFilePath = filePath;
		mIsBookFromAssets = isBookFromAssets;
	}
	
}
