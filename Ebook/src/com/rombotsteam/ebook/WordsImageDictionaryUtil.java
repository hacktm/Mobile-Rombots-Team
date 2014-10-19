package com.rombotsteam.ebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WordsImageDictionaryUtil {

	public static HashMap<String, ArrayList<String>> mWordsDict; 
	
	public static String getImageFilenameForWord(String word) {
		String filename = "";
		
		ArrayList<String> imgFileList = mWordsDict.get(word);
		if (imgFileList != null) {
			
			Random rand = new Random(System.currentTimeMillis());
			int randIdx = rand.nextInt(imgFileList.size());
			
			/*for (String imgFilename : imgFileList) {
				Log.e("ebook", "Word " + word + " file " + imgFilename);
			}*/
			
			filename = imgFileList.get(randIdx);
		}
		
		return filename;
	}
	
	@SuppressWarnings("unchecked")
	public static void createWordsImageDict(String jsonContent) {
		mWordsDict = new HashMap<String, ArrayList<String>>();
		try {
			JSONObject jsonObj = new JSONObject(jsonContent);
			Iterator<String> it = jsonObj.keys();
			while (it.hasNext()) {
				String image = it.next();				
				JSONArray words = jsonObj.getJSONArray(image);
				for (int i=0 ; i< words.length(); i++) {
					String word = words.getString(i);
					addToDict(image, word);
				}				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static void addToDict(String image, String word) {
		ArrayList<String> imgFileList = mWordsDict.get(word);
		if (imgFileList == null) {
			imgFileList = new ArrayList<String>(); 
		}
		
		imgFileList.add(image);
		mWordsDict.put(word, imgFileList);
	}
	
	@SuppressWarnings("unchecked")
	static public ArrayList<String> getWordsFromJSONResp(String jsonResp) {
		ArrayList<String> wordList = new ArrayList<String>();
		
		try {
			JSONArray jsonArray = new JSONArray(jsonResp);
			
			JSONObject jsonObj = (JSONObject) jsonArray.get(0);
			Iterator<String> it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next();			
				JSONArray words = jsonObj.getJSONArray(key);
				for (int i=0 ; i< words.length(); i++) {
					String word = words.getString(i);
					wordList.add(word);
				}				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return wordList;
	}
	
}
