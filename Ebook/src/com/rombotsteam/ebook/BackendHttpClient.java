package com.rombotsteam.ebook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class BackendHttpClient {

	private static final String GAE_BACKEND_URL = "http://rbstorybook.appspot.com";
	private static final int MAX_RESULTS = 5;

	public BackendHttpClient() {
		
	}
	
	public String postPage(String pageText) {
		String respJson = "";
		
		HttpURLConnection urlConnection = null;
		
		try {
			URL url = new URL(GAE_BACKEND_URL);
			
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
		    urlConnection.setChunkedStreamingMode(0);
		    
		    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
		    post(out, pageText);

		    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		    respJson = readStream(in);
		    
		    Log.w("ebook", "RESP:\n" + respJson);
		    
		   } catch (Exception e) {
			   e.printStackTrace();
		   } finally {
		     urlConnection.disconnect();
		   }
		
		return respJson;
	}

	private void writeStream(OutputStream out, String pageText) {
		try {
	        //int MAX_BUFFER = 1024;
	        byte[] buffer = pageText.getBytes();
	        
	        Log.i("ebook", "upload " + buffer.length);
	        
	        out.write(buffer);
	        
	    } catch (Exception e1) {  e1.printStackTrace();}
	}
	
	private void post(OutputStream os, String pageText) {
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("text", pageText));
	    //params.add(new BasicNameValuePair("tagResults", ""+MAX_RESULTS));
	    
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		    writer.write(getQuery(params));
		    writer.flush();
		    writer.close();
		    os.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}


	private String readStream(InputStream is) {
		String resp = "";
		try {
	        int MAX_BUFFER = 1024;
	        byte[] buffer = new byte[MAX_BUFFER];

	        int numChar = 0;
	        while ( (numChar = is.read(buffer)) > 0) {
	        	resp = resp + new String(buffer, 0, numChar);
	        }
	        
	    } catch (Exception e1) {  e1.printStackTrace();}

		return resp;
	}
	
}
