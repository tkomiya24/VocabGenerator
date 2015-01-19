package com.tkomiya.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Vocab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, String> vocab;
	private int primaryLanguage;
	public static final int ENGLISH = 0;
	public static final int KOREAN = 1;
	public static final int JAPANESE = 2;
	public static final int VOCAB_SUPPORT_SIZE = 3;
	public static final String[] SUPPORTED_LANGUAGES = {"English", "Korean", "Japanese"};
	
	public Vocab(int primaryLanguage){
		
		vocab = new HashMap<Integer, String>();
		this.primaryLanguage = primaryLanguage;
	}
	
	public void addLanguage(int language, String definitions){
		
		vocab.put(language, definitions);
	}
	
	public String getTranslation(int language){
		
		return vocab.get(language);
	}

	public boolean means(String word) {
		
		Iterator<String> itr = vocab.values().iterator();
		while(itr.hasNext()){
			String next = itr.next();
			if(next.toLowerCase().equals(word.toLowerCase())){
				return true;
			}
		}
		return false;
	}

	public int getPrimaryLanguage() {
		return this.primaryLanguage;
	}

}
