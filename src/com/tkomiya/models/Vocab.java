package com.tkomiya.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.commons.collections4.CollectionUtils;

import com.tkomiya.main.Translation;

public class Vocab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SupportedLanguage primaryLanguage;
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
		this.primaryLanguage = primaryLanguage2;
		translations = new HashMap<SupportedLanguage, Translation>();
	}
	
	public Translation getTranslation(SupportedLanguage language) {
		return translations.get(language);
	}

	public SupportedLanguage getPrimaryLanguage() {
		return this.primaryLanguage;
	}

	public void incrementTimesTested(SupportedLanguage language) {
		if (translations.containsKey(language)) {
	    translations.get(language).setTimesTested(translations.get(language).getTimesTested() + 1);
		}
	}
	
	public void incrementTimesCorrect(SupportedLanguage language) {
    if (translations.containsKey(language)) {
      translations.get(language).setTimesCorrect(translations.get(language).getTimesCorrect() + 1);
    }
	}

	public int getTimesTested(SupportedLanguage language) {
    if (translations.containsKey(language)) {
      return translations.get(language).getTimesTested();
    } else {
      return 0;
    }
	}
	
	public int getTimesCorrect(SupportedLanguage language) {
    if (translations.containsKey(language)) {
      return translations.get(language).getTimesCorrect();
    } else {
      return 0;
    }
	}
	
	public boolean contains(String searchTerm) {
		Collection<String> translations = getAllTranslations();
		for (String translation : translations) {
			if (translation.toLowerCase().contains(searchTerm.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	private Collection<String> getAllTranslations() {
	  Collection<String> allTranslations = new HashSet<String>();
	  for (Translation translation : this.translations.values()) {
	    for (String trans : translation.getTranslations()) {
	      allTranslations.add(trans);
	    }
	  }
	  return allTranslations;
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
	public Date getLastTested(SupportedLanguage language) {
		if (translations.containsKey(language)) {
		  return translations.get(language).getLastTested();
		} else {
		  return null;
		}
	}

	/**
	 * @param lastTested the lastTested to set
	 */
	public void setLastTested(SupportedLanguage language, Date lastTested) {
    if (translations.containsKey(language)) {
      translations.get(language).setLastTested(lastTested);
    }
	}
	
	public void setLastTested(SupportedLanguage lang) {
	  Calendar cal = Calendar.getInstance();
	  cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		setLastTested(lang, cal.getTime());
	}

  public void addTranslation(SupportedLanguage language, Translation translation) {
    translations.put(language, translation);
  }
	
}
