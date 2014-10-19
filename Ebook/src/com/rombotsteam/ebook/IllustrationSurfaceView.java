package com.rombotsteam.ebook;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class IllustrationSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private static final int BRUSH_DEFAULT_SIZE = 20;
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	  
	private ArrayList<Clipart> mClipartList = new ArrayList<Clipart>();
	
	private ArrayList<Brush> mBrushstrokeList = new ArrayList<Brush>();
	
	private Bitmap mBackgroudBmp;
	
	private String mCurrentClipartFilePath;
	private boolean mIsClipartSelected;
	
	private int mCurrentBrushColor = Color.TRANSPARENT;
	
    public IllustrationSurfaceView(Context context, AttributeSet attSet) {
    	super(context, attSet);
	    
	    sh = getHolder();
	    sh.addCallback(this);
	    
	    mBackgroudBmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	}
	  
	public void surfaceCreated(SurfaceHolder holder) {
	    Log.e("ebook", "created");
	    
	    setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				onTouchHandler(event);
				return true;
			}

		});
	    
	    updateCanvas();
	}
	  
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
		    
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawEntities(canvas);
	}

	private void drawEntities(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);
		
		Rect srcRect = new Rect(mBackgroudBmp.getWidth()/2, 0, mBackgroudBmp.getWidth(), mBackgroudBmp.getHeight());
		canvas.drawBitmap(mBackgroudBmp, srcRect, new Rect(0,0,canvas.getWidth(), canvas.getHeight()), paint);
		
		
		Paint paint = new Paint();
		
		for (Clipart clipart : mClipartList) {
			if (clipart.mBmp != null) {
				canvas.drawBitmap(clipart.mBmp, clipart.mPosX, clipart.mPosY, paint);
			}
		}
		
		paint = new Paint();
		//paint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY));
		
		for (Brush brushStroke : mBrushstrokeList) {
			paint.setColor(brushStroke.mColor);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(brushStroke.mPosX, brushStroke.mPosY, brushStroke.mRadius, paint);
		}
	}

	private void updateCanvas() {
		/*Canvas canvas  = sh.lockCanvas();
		drawEntities(canvas);
		sh.unlockCanvasAndPost(canvas);*/
		
		this.invalidate();
	}
	  
	private void onTouchHandler(MotionEvent event) {
		int eventAction = event.getAction();
		
		if (eventAction == MotionEvent.ACTION_DOWN) {
			
			if (mIsClipartSelected) {
				boolean clipartExistsAtPos = false;
				for (Clipart clipart : mClipartList) {
					if (clipart.isInBounds((int) event.getX(), (int) event.getY())) {
						clipartExistsAtPos = true;
						break;
					}
				}
				
				if (!clipartExistsAtPos) {
					addClipart(event);
					
					updateCanvas();
				} else {
					selectClipart(event);
				}
			}
			
		}
		
		if (eventAction == MotionEvent.ACTION_MOVE) {
		
			if (mIsClipartSelected) {
				moveSelectedClipart(event);
			} else {
				if (mCurrentBrushColor != Color.TRANSPARENT) {
					addBrushStroke(event);
				} else {
					eraseBrushStrokes(event);
				}
			}
			
			updateCanvas();
		}

		if (eventAction == MotionEvent.ACTION_UP) {
			deselectAllCliparts();
		}
	}

	private void eraseBrushStrokes(MotionEvent event) {
		Iterator<Brush> it = mBrushstrokeList.iterator();
		while (it.hasNext()) {
			Brush brushStroke = it.next();
			if (brushStroke.isInBounds((int) event.getX(), (int) event.getY())) {
				it.remove();
			}
		}
	}

	private void addBrushStroke(MotionEvent event) {
		int radius = BRUSH_DEFAULT_SIZE;
		Brush brushstroke = new Brush(getContext(), mCurrentBrushColor, (int) event.getX(), (int) event.getY(), radius );
		mBrushstrokeList.add(brushstroke);
	}

	private void addClipart(MotionEvent event) {
		if (!TextUtils.isEmpty(mCurrentClipartFilePath)) {
			Clipart clipArt = new Clipart(this.getContext(), mCurrentClipartFilePath, (int) event.getX(), (int) event.getY());
			mClipartList.add(clipArt);
		}
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
	
	public void setCurrentClipart(String filePath) {
		mCurrentClipartFilePath = filePath;
	}

	public void setClipartSelected(boolean clipartEnabled) {
		mIsClipartSelected = clipartEnabled;
	}

	public void setCurrentBrushColor(int color) {
		mCurrentBrushColor = color;
	}

	public void clearAll() {
		mBrushstrokeList.clear();
		mClipartList.clear();
		updateCanvas();
	}
	
	public Bitmap getContent() {
		Bitmap canvasContentBmp = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvasOut= new Canvas(canvasContentBmp);
		draw(canvasOut);
		
		return canvasContentBmp;		
	}

}
