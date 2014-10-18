package com.rombotsteam.ebook;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class IllustrationSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	  
	private ArrayList<Clipart> mClipartList = new ArrayList<Clipart>();
	
    public IllustrationSurfaceView(Context context, AttributeSet attSet) {
    	super(context, attSet);
	    
	    sh = getHolder();
	    sh.addCallback(this);
	    paint.setColor(Color.BLUE);
	    paint.setStyle(Style.FILL);
	}
	  
	public void surfaceCreated(SurfaceHolder holder) {
	    Canvas canvas = sh.lockCanvas();
	    canvas.drawColor(Color.BLACK);
	    canvas.drawCircle(100, 200, 50, paint);
	    sh.unlockCanvasAndPost(canvas);
	    
	    test();
	    
	    Log.e("ebook", "created");
	    
	    setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				onTouchHandler(event);
				return true;
			}

		});
	}
	  
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	public void test() {
		Canvas canvas  = sh.lockCanvas();
		
		canvas.restore();
		
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		canvas.drawBitmap(b, 10, 10, new Paint());
		
		sh.unlockCanvasAndPost(canvas);		
	}
	
	private void update() {
		Canvas canvas  = sh.lockCanvas();
		
		canvas.drawColor(Color.WHITE);
		
		for (Clipart clipart : mClipartList) {
			if (clipart.mBmp != null) {
				canvas.drawBitmap(clipart.mBmp, clipart.mPosX, clipart.mPosY, new Paint());
			}
		}
		
		sh.unlockCanvasAndPost(canvas);
	}
	  
	private void onTouchHandler(MotionEvent event) {
		int eventAction = event.getAction();
		
		if (eventAction == MotionEvent.ACTION_DOWN) {
			
			boolean clipartExistsAtPos = false;
			for (Clipart clipart : mClipartList) {
				if (clipart.isInBounds((int) event.getX(), (int) event.getY())) {
					clipartExistsAtPos = true;
					break;
				}
			}
			
			if (!clipartExistsAtPos) {
				addClipart(event);
				
				update();
			} else {
				selectClipart(event);
			}
			
			Log.i("ebook", "down");
		}
		
		if (eventAction == MotionEvent.ACTION_MOVE) {
		
			Log.i("ebook", "move " + (int) event.getX() + " / " + (int) event.getY());
			
			moveSelectedClipart(event);
			
			update();
		}

		if (eventAction == MotionEvent.ACTION_UP) {
			deselectAllCliparts();
		}
	}

	private void addClipart(MotionEvent event) {
		Clipart clipArt = new Clipart(this.getContext(), R.drawable.tree1, (int) event.getX(), (int) event.getY());
		mClipartList.add(clipArt);
	}

	private void selectClipart(MotionEvent event) {
		for (Clipart clipart : mClipartList) {
			if (clipart.isInBounds((int) event.getX(), (int) event.getY())) {
				clipart.mIsSelected = true;
				break;
			}
		}
	}

	private void deselectAllCliparts() {
		for (Clipart clipart : mClipartList) {
			clipart.mIsSelected = false;
		}
	}

	private void moveSelectedClipart(MotionEvent event) {
		for (Clipart clipart : mClipartList) {
			if (clipart.mIsSelected) {
				clipart.setPos((int) event.getX(), (int) event.getY());
				break;
			}
		}
	}

}
