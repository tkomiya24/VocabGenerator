package com.tkomiya.main;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

public class Translation implements Serializable {

  private static final long serialVersionUID = 2746919310902901290L;
  private Set<String> translations;
   private int timesTested;
   private int timesCorrect;
   private Date lastTested;
   
   public Translation() {
     this.translations = new HashSet<String>();
   }

  /**
   * @return the timesTested
   */
  public int getTimesTested() {
    return timesTested;
  }

  /**
   * @param timesTested the timesTested to set
   */
  public void setTimesTested(int timesTested) {
    this.timesTested = timesTested;
  }

  /**
   * @return the timesCorrect
   */
  public int getTimesCorrect() {
    return timesCorrect;
  }

  /**
   * @param timesCorrect the timesCorrect to set
   */
  public void setTimesCorrect(int timesCorrect) {
    this.timesCorrect = timesCorrect;
  }

  /**
   * @return the lastTested
   */
  public Date getLastTested() {
    return lastTested;
  }

  /**
   * @param lastTested the lastTested to set
   * @return 
   */
  public void setLastTested(Date lastTested) {
    this.lastTested = lastTested;
  }

  /**
   * @return the translations
   */
  public Set<String> getTranslations() {
    return translations;
  }
   
  public void addTranslation(String translation) {
    translations.add(translation);
  }
  
  public void addTranslations(Collection<String> translations) {
    for (String trans : translations) {
      this.translations.add(trans);
    }
  }
  
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Translation)) {
      return false;
    } else {
      Translation trans2 = (Translation) other;
      return this.timesCorrect == trans2.timesCorrect &&
             this.timesTested == trans2.timesTested &&
             sameDateHelper(trans2) &&
             CollectionUtils.isEqualCollection(this.translations, trans2.translations);
    }
  }
  
  private boolean sameDateHelper(Translation other) {
    if (this.lastTested == null && other.lastTested == null) {
      return true;
    }
    
    if (this.lastTested != null ^ other.lastTested != null) {
      return false;
    }
    
    return this.lastTested.equals(other.lastTested);
  }
  
  public String printTranslations() {
    StringBuilder sb = new StringBuilder();
    for (String trans : translations) {
      sb.append(trans);
      sb.append(", ");
    }
    sb.delete(sb.length() - 2, sb.length());
    return sb.toString();
  }
}
