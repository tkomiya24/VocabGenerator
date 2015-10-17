package com.tkomiya.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.commons.collections4.CollectionUtils;

import com.tkomiya.main.Translation;

public class Vocab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<SupportedLanguage, String> vocab;
	private SupportedLanguage primaryLanguage;
	private Date lastTested;
	private HashMap<SupportedLanguage, Integer> timesTested;
	private HashMap<SupportedLanguage, Integer> timesCorrect;
	private HashMap<SupportedLanguage, Translation> translations;
	
	public static enum SupportedLanguage {
		ENGLISH("English"), KOREAN("Korean"), JAPANESE("Japanese");
		
		private String name;
		
		private SupportedLanguage(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static int size() {
			return SupportedLanguage.values().length;
		}
	}
	
	public Vocab(SupportedLanguage primaryLanguage2) {
		vocab = new HashMap<SupportedLanguage, String>();
		this.primaryLanguage = primaryLanguage2;
		timesTested = new HashMap<SupportedLanguage, Integer>();
		timesCorrect = new HashMap<SupportedLanguage, Integer>();
		translations = new HashMap<SupportedLanguage, Translation>();
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
	  if (this == obj) {
	    return true;
	  }
	  
		if (!(obj instanceof Vocab)) {
			return false;
		}
		Vocab other = (Vocab) obj;
		
		return this.translations.equals(other.translations);
	}

	/**
	 * @return the lastTested
	 */
	public Date getLastTested() {
		return lastTested;
	}

	/**
	 * @param lastTested the lastTested to set
	 */
	public void setLastTested(Date lastTested) {
		this.lastTested = lastTested;
	}
	
	public void setLastTested() {
	  Calendar cal = Calendar.getInstance();
	  cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		setLastTested(cal.getTime());
	}

  public void addTranslation(SupportedLanguage language, Translation translation) {
    translations.put(language, translation);
  }
	
}
