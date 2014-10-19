package com.rombotsteam.ebook;

import java.io.File;
import java.io.FilenameFilter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

public class FileChooserDialog {

	private String[] mFileList;
	private String mChosenFile;
	private static final String FTYPE = ".txt";    

	private File mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

	
	private void loadFileList() {
	    try {
	    	mPath.mkdirs();
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	    if(mPath.exists()) {
	        FilenameFilter filter = new FilenameFilter() {
	            @Override
	            public boolean accept(File dir, String filename) {
	                File sel = new File(dir, filename);
	                return filename.contains(FTYPE);
	            }
	        };
	        mFileList = mPath.list(filter);
	    }
	    else {
	        mFileList = new String[0];
	    }
	    
	    for (String s : mFileList) {
	    	Log.i("ebook", "file: " + s);
	    }
	}

	protected Dialog showFileChooserDialog(Context context, final IFileChooserListener listener) {
	    Dialog dialog = null;
	    AlertDialog.Builder builder = new Builder(context);
	    
	    loadFileList();

        builder.setTitle("Choose your file");
        if(mFileList == null) {
            dialog = builder.create();
            return dialog;
        }
        
        builder.setItems(mFileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mChosenFile = mFileList[which];
                
                mChosenFile = mPath + "/" + mChosenFile; 
                
                Log.i("ebook", "File chosen: "  + mChosenFile);
                
                if (listener != null) {
                	listener.onFileChosen(mChosenFile);
                }
            }
        });
        
	    dialog = builder.show();
	    return dialog;
	}
	
}
