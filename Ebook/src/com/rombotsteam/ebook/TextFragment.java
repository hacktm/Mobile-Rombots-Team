package com.rombotsteam.ebook;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TextFragment extends Fragment implements IPageSwitchListener {
	
	
	private ImageView mInitialImage;
	private TextView mPageText;
	
	private int mCurrentPageIdx = 0;
	private ArrayList<String> mPages;
	
	private ImageButton mPrevPageBtn;
	
	private ImageButton mEnableTTSBtn;
	
	private TextToSpeech mTTS;
	private String mCurrentPageText = "";

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.text_fragment, container, false);
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setupControls();
	}

	private void setupControls() {

		mPageText = (TextView) getView().findViewById(R.id.textPage);
		mInitialImage = (ImageView) getView().findViewById(R.id.imageInitial);
		
		mCurrentPageIdx = 0;
		
		loadTextFromFile("red_riding_hood.txt");
		
		setFont("droidserif.ttf");
		
		mPrevPageBtn = (ImageButton) getView().findViewById(R.id.buttonPrevPage);
		mPrevPageBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onPrevPage();
			}
		});
		
		mEnableTTSBtn = (ImageButton) getView().findViewById(R.id.buttonTTS);
		
		mEnableTTSBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startTTS(mCurrentPageText);
				//TODO: stop TTS on btn click again
			}
		});
	}

	private void setFont(String fontFilePath) {
		Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), fontFilePath);
		mPageText.setTypeface(myTypeface);
	}

	private void loadTextFromFile(String fileName) {
		AssetManager assetManager = getActivity().getAssets();
	    
		String text = "";
		
	    InputStream is=null;
	    try {
	        is = assetManager.open(fileName);
	       
	        int MAX_BUFFER = 1024;
	        byte[] buffer = new byte[MAX_BUFFER];

	        while (is.read(buffer) > 0) {
	        	text = text + new String(buffer, "utf-8");
	        }
	        
	    } catch (Exception e1) {  e1.printStackTrace();}
	    
	    mPages = PageUtil.getPages(text);
	    
		setPage();
	    
	}

	private void setPage() {
		mCurrentPageText = mPages.get(mCurrentPageIdx);
		
	    String firstLetter = ""+ mCurrentPageText.charAt(0);
	    setInitialImg(firstLetter);	    

		String pageTextWithSpace = "      " + mCurrentPageText.substring(1);
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
	
	private void startTTS(final String pageText) {
		mTTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
		   @Override
		   public void onInit(int status) {
			   mTTS.setLanguage(Locale.UK);
			   mTTS.speak(pageText, TextToSpeech.QUEUE_FLUSH, null);
		   }
		});
	}
	
}
