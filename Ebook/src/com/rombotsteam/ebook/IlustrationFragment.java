package com.rombotsteam.ebook;

import java.util.ArrayList;
import java.util.List;

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

public class IlustrationFragment extends Fragment {

	private IllustrationSurfaceView mPageSurface;

	
	private ImageButton mShowClipartListButton;
	private GridView mClipartGrid;
	private ImageButton mShowBushListButton;

	
	private ClipartGridAdapter mAdapter;

	private List<Clipart> mClipartList;
	
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
				mClipartGrid.setVisibility(View.VISIBLE);
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
		
		mShowBushListButton = (ImageButton) getView().findViewById(R.id.button2);
		mShowBushListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPageSurface.setClipartSelected(false);	
			}
		});
	}
	
	private void initClipartList() {
		mClipartList = new ArrayList<Clipart>();
		
		Clipart clipArt = new Clipart(getActivity(), "dragon.png", 0,0);		
		mClipartList.add(clipArt);
		
		 clipArt = new Clipart(getActivity(), "rabbit.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "dragon.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "tree.png", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "tree1.jpg", 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), "dragon.png", 0,0);		
		mClipartList.add(clipArt);
	}
	
	public void onResume() {
		super.onResume();
		
		
	}
	
	

	
}
