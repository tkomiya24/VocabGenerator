package com.tkomiya.main;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

public class Translation {

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
    if (!(other instanceof Translation)) {
      return false;
    } else {
      Translation trans2 = (Translation) other;
      return this.timesCorrect == trans2.timesCorrect &&
             this.timesTested == trans2.timesTested &&
             this.lastTested.equals(trans2.lastTested) &&
             CollectionUtils.isEqualCollection(this.translations, trans2.translations);
    }
  }
}
