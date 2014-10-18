package com.rombotsteam.ebook;

import java.util.ArrayList;

public class PageUtil {

	private static final int MAX_CHARS_PER_PAGE = 700;
	
	public static ArrayList<String> getPages(String text) {
		ArrayList<String> pagesList = new ArrayList<String>();
		
		String[] lines = text.split("\n");
		
		String currPage = "";
		
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			
			line = line.trim();
			line = line.replace("'", "");
			line = line.replace("\"", "");
			
			int numChars = line.length();
					
			if (currPage.length() + numChars < MAX_CHARS_PER_PAGE) {
				currPage = currPage  + line + "\n";
			} else {
				pagesList.add(currPage);
				
				currPage = line;
			}
		}
		
		pagesList.add(currPage);
		
		return pagesList;
	}
	

	
}
