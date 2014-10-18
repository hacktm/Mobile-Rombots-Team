package com.rombotsteam.ebook;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class IlustrationFragment extends Fragment {

	private IllustrationSurfaceView mPageSurface;
	
	private Button mShowClipartListButton;
	private GridView mClipartGrid;
	
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
		
		mShowClipartListButton = (Button) getView().findViewById(R.id.button1);
		
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
				
				Log.w("ebook", "Selected " + position + " " + selClipart);
				mClipartGrid.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	private void initClipartList() {
		mClipartList = new ArrayList<Clipart>();
		
		Clipart clipArt = new Clipart(getActivity(), R.drawable.tree1, 0,0);		
		mClipartList.add(clipArt);
		
		 clipArt = new Clipart(getActivity(), R.drawable.tree1, 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), R.drawable.tree1, 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), R.drawable.tree1, 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), R.drawable.tree1, 0,0);		
		mClipartList.add(clipArt);
		 clipArt = new Clipart(getActivity(), R.drawable.tree1, 0,0);		
		mClipartList.add(clipArt);
	}
	
	public void onResume() {
		super.onResume();
		
		
	}
	
	

	
}
