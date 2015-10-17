package com.tkomiya.testutils.datageneration.translation;

import java.util.Calendar;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class JapaneseTranslationFactory {

  public static Translation mondayJapaneseTranslation() {
    return TestTranslationLoaderUtil.makeTranslation(2, 2, "月曜日");
  }
  
  public static Translation tuesdayJapaneseTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1991, Calendar.JULY, 17, 6, 40, 44);
    return TestTranslationLoaderUtil.makeTranslation(1, 7, cal, "火曜日");
  }
  
  public static Translation wednesdayJapaneseTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2015, Calendar.JANUARY, 15, 12, 15, 44);
    return TestTranslationLoaderUtil.makeTranslation(9, 15, cal, "水曜日");
  }
  
}
