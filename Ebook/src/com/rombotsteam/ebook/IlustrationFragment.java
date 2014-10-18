package com.rombotsteam.ebook;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class IlustrationFragment extends Fragment {

	private IllustrationSurfaceView mPageSurface;

	
	private ImageButton mShowClipartListButton;
	private GridView mClipartGrid;
	private ImageButton mShowBushListButton;
	
	private ImageButton mNextPageBtn;

	private View mColorsLayout;
	private ImageView mColorYellowImage;
	private ImageView mColorRedImage;
	
	private ClipartGridAdapter mAdapter;

	private List<Clipart> mClipartList;


	private View mColorOrangeImage;
	private View mColorCyanImage;
	private View mColorGreenImage;
	private View mColorMagentaImage;


	private IPageSwitchListener mPageSwitchListener;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ilustration_fragment, container, false);
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setupControls();
	}

	private void setupControls() {
		
		mPageSurface = (IllustrationSurfaceView) getView().findViewById(R.id.surfacePage);
	
		mClipartGrid = (GridView) getView().findViewById(R.id.gridView1);
	
		initClipartList();
		mAdapter = new ClipartGridAdapter(getActivity(), R.layout.clipart_grid_item, R.id.textPage, mClipartList);
		mClipartGrid.setAdapter(mAdapter);
		
		mShowClipartListButton = (ImageButton) getView().findViewById(R.id.button1);
		
		mShowClipartListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mClipartGrid.getVisibility() == View.INVISIBLE) {
					mClipartGrid.setVisibility(View.VISIBLE);
					mColorsLayout.setVisibility(View.INVISIBLE);
					resetBrushBtnImage();
				} else {
					mClipartGrid.setVisibility(View.INVISIBLE);
					resetBrushBtnImage();
					
					setEraserBrush();
				}
			}
		});
		
		mClipartGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View v, int position, long id) {
				
				Clipart selClipart = mAdapter.getItem(position);
				
				mClipartGrid.setVisibility(View.INVISIBLE);
				
				mPageSurface.setCurrentClipart(selClipart.mFilePath);
				mPageSurface.setClipartSelected(true);
				
			}
		});
		
		mColorsLayout = getView().findViewById(R.id.layoutColors);
		mColorsLayout.setVisibility(View.INVISIBLE);
		
		mShowBushListButton = (ImageButton) getView().findViewById(R.id.button2);
		mShowBushListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (mColorsLayout.getVisibility() == View.VISIBLE) {
					resetBrushBtnImage();
					mColorsLayout.setVisibility(View.INVISIBLE);
					setEraserBrush();
				} else {
					mColorsLayout.setVisibility(View.VISIBLE);
					mClipartGrid.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		
		mColorYellowImage = (ImageView) getView().findViewById(R.id.imageYellow);
		mColorYellowImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectBrushColor(Color.rgb(255,255,0));
				
				setBrushBtnImage(v);
			}
		});
		
		mColorOrangeImage = (ImageView) getView().findViewById(R.id.imageOrange);
		mColorOrangeImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				selectBrushColor(Color.rgb(247,0,0));
				
				setBrushBtnImage(v);
			}
		});
		
		mColorRedImage = (ImageView) getView().findViewById(R.id.imageRed);
		mColorRedImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectBrushColor(Color.rgb(218,28,92));
				
				setBrushBtnImage(v);
			}
		});
		
		mColorCyanImage = (ImageView) getView().findViewById(R.id.imageCyan);
		mColorCyanImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//selectBrushColor(Color.rgb(39,170,225));
				
				selectBrushColor(Color.rgb(39,250,255));
				
				setBrushBtnImage(v);
			}
		});
		
		mColorGreenImage = (ImageView) getView().findViewById(R.id.imageGreen);
		mColorGreenImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//selectBrushColor(Color.rgb(57,181,74));
				
				selectBrushColor(Color.rgb(0,250,0));
				
				setBrushBtnImage(v);
			}
		});
		
		mColorMagentaImage = (ImageView) getView().findViewById(R.id.imageMagenta);
		mColorMagentaImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectBrushColor(Color.rgb(146,39,143));
				
				setBrushBtnImage(v);
			}
		});
		
		mNextPageBtn = (ImageButton) getView().findViewById(R.id.buttonNextPage);
		mNextPageBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notifyNextPage();
				
				clearCanvas();
			}
		});
	}
	
	protected void clearCanvas() {
		mPageSurface.clearAll();
	}

	private void initClipartList() {
		mClipartList = new ArrayList<Clipart>();
		
		Clipart clipArt = new Clipart(getActivity(), "hood.png", 0,0);		
		mClipartList.add(clipArt);
		
		 clipArt = new Clipart(getActivity(), "tree.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "wolf1.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "tree.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "hunter.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "hood.png", 0,0);		
		mClipartList.add(clipArt);
	}
	
	public void onResume() {
		super.onResume();
		
		
	}

	public void setPageSwitchListener(IPageSwitchListener listener) {
		mPageSwitchListener = listener;
	}

	private void notifyNextPage() {
		if (mPageSwitchListener != null) {
			mPageSwitchListener.onNextPage();
		}
	}
	
	private void notifyPrevPage() {
		if (mPageSwitchListener != null) {
			mPageSwitchListener.onPrevPage();
		}
	}
	
	private void setEraserBrush() {
		// erase brush selected
		mPageSurface.setClipartSelected(false);
		mPageSurface.setCurrentBrushColor(Color.TRANSPARENT);
	}
	
	private void selectBrushColor(int color) {
		mPageSurface.setClipartSelected(false);
		mColorsLayout.setVisibility(View.INVISIBLE);
		mPageSurface.setCurrentBrushColor(color);
	}

	private void setBrushBtnImage(View v) {
		ImageView iv = (ImageView)v;
		Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
		mShowBushListButton.setImageBitmap(bitmap);
	}
	
	private void resetBrushBtnImage() {
		mShowBushListButton.setImageResource(R.drawable.crayon_btn);
	}
	
}
