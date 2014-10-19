package com.rombotsteam.ebook;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class ExportIllustrationUtil {
	
	public static final String EXPORT_IMG_TYPE = ".png";

	public static void saveImage(Bitmap bmp) {
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    String fileName = generateFileName();
		File file = new File(path, fileName);

	    Log.i("ebook", "saving file " + path + " " + fileName);
	    
	    try {
	        // Make sure the Pictures directory exists.
	        path.mkdirs();
	        
	        FileOutputStream fOut = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
	        fOut.flush();
	        fOut.close();

	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static String generateFileName() {
		String filename = "";
		
		filename = "img-" + System.currentTimeMillis() + EXPORT_IMG_TYPE;
		
		return filename;
	}
	
}
