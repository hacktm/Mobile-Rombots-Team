package com.rombotsteam.ebook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class FileUtil {

	static public String getFileContents(Context context, String filePath, boolean isAsset) {
		String fileContent = "";
		
		if (isAsset) {
			Log.d("ebook", "getFileContents from assets: " + filePath);
			
			AssetManager assetManager = context.getAssets();
		    
		    InputStream is=null;
		    try {
		        is = assetManager.open(filePath);
		       
		        int MAX_BUFFER = 1024;
		        byte[] buffer = new byte[MAX_BUFFER];
	
		        while (is.read(buffer) > 0) {
		        	fileContent = fileContent + new String(buffer, "utf-8");
		        }
		        
		    } catch (Exception e1) {  e1.printStackTrace();}
		} else {
			Log.d("ebook", "getFileContents from file: " + filePath);
		    try {
		    	FileInputStream fs = new FileInputStream(filePath);
		    	
		        int MAX_BUFFER = 1024;
		        byte[] buffer = new byte[MAX_BUFFER];
	
		        while (fs.read(buffer) > 0) {
		        	fileContent = fileContent + new String(buffer, "utf-8");
		        }
		        
		        fs.close();
		        
		    } catch (Exception e1) {  e1.printStackTrace();}
		}
		
		return fileContent;
	}
	
	static public ArrayList<String> getFilesMatchingName(Context context, String subDir, String matchStr) {
		ArrayList<String> matchingFileList = new ArrayList<String>(); 
		
		AssetManager assetManager = context.getAssets();
		String[] files;
		try {
			files = assetManager.list(subDir);
			
			if(files != null) {
				for (String file : files) {
					if (file.toLowerCase().contains(matchStr.toLowerCase())) {
						Log.i("ebook", "FILE " + file + " matched " + matchStr);
						matchingFileList.add(subDir+"/"+file);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return matchingFileList;
	}
}
