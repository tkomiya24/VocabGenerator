package com.tkomiya.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Vocab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<SupportedLanguage, String> vocab;
	private SupportedLanguage primaryLanguage;
	private HashMap<SupportedLanguage, Integer> timesTested;
	private HashMap<SupportedLanguage, Integer> timesCorrect;
	
	public static enum SupportedLanguage {
		ENGLISH, KOREAN, JAPANESE
	}
	
	public Vocab(SupportedLanguage primaryLanguage2) {
		vocab = new HashMap<SupportedLanguage, String>();
		this.primaryLanguage = primaryLanguage2;
		timesTested = new HashMap<SupportedLanguage, Integer>();
		timesCorrect = new HashMap<SupportedLanguage, Integer>();
	}
	
	public void addLanguage(SupportedLanguage language, String definitions) {
		vocab.put(language, definitions);
		timesTested.put(language, 0);
		timesCorrect.put(language, 0);
	}
	
	public String getTranslation(SupportedLanguage language) {
		return vocab.get(language);
	}

	public void editTranslation(SupportedLanguage language, String definition) {
		if (vocab.containsKey(language)) {
			vocab.put(language, definition);
		}
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

	public SupportedLanguage getPrimaryLanguage() {
		return this.primaryLanguage;
	}

	public void incrementTimesTested(SupportedLanguage language) {
		int timesTestedForThisLanguage = timesTested.get(language);
		timesTested.put(language, ++timesTestedForThisLanguage);
	}
	
	public void incrementTimesCorrect(SupportedLanguage language) {
		int timesCorrectForThisLanguage = timesCorrect.get(language);
		timesCorrect.put(language, ++timesCorrectForThisLanguage);
	}

	public int getTimesTested(SupportedLanguage language) {
		return timesTested.get(language);
	}
	
	public int getTimesCorrect(SupportedLanguage language) {
		return timesCorrect.get(language);
	}
	
	public void setScore(SupportedLanguage language, int timesTested, int timesCorrect) {
		if (this.getTranslation(language) == null) {
			return;
		} else {
			this.timesCorrect.put(language, timesCorrect);
			this.timesTested.put(language, timesTested);
		}
	}
	
	public boolean contains(String searchTerm) {
		Collection<String> translations = vocab.values();
		for (String translation : translations) {
			if (translation.toLowerCase().contains(searchTerm.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vocab)) {
			return false;
		}
		Vocab other = (Vocab) obj;
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (!translationEquals(other, language)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean translationEquals(Vocab other, SupportedLanguage language) {
		boolean different = other.vocab.containsKey(language) 
				^ this.vocab.containsKey(language);
		if (different) {
			return false;
		}
		boolean bothContain = other.vocab.containsKey(language) 
				&& this.vocab.containsKey(language);
		if (!bothContain) {
			return true;
		}
		return other.vocab.get(language).equals(this.vocab.get(language))
				&& other.getTimesCorrect(language) == this.getTimesCorrect(language)
				&& other.getTimesTested(language) == this.getTimesTested(language);
	}
	
}
