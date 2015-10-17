package com.tkomiya.testutils.datageneration.translation;

import java.util.Calendar;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class EnglishTranslationFactory {

  public static Translation mondayTranslation() {
    return TestTranslationLoaderUtil.makeTranslation(0, 0, "Monday");
  }
  
  public static Translation tuesdayTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1991, Calendar.JULY, 17, 6, 40, 44);
    return TestTranslationLoaderUtil.makeTranslation(4, 5, cal, "Tuesday");
  }
 
  public static Translation wednesdayTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2015, Calendar.JANUARY, 15, 12, 15, 44);
    return TestTranslationLoaderUtil.makeTranslation(0, 0, cal, "Wednesday");
  }
  
}
