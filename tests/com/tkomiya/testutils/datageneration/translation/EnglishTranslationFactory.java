package com.tkomiya.testutils.datageneration.translation;

import java.util.Calendar;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class EnglishTranslationFactory {

  private static final long RANDOM_LONG_2 = 45645678964L;
  private static final long RANDOM_LONG_1 = 564897489656456L;

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
  
  public static Translation makeNervousTranslation1() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_1);
    return TestTranslationLoaderUtil.makeTranslation(7, 9, cal, "Be nervous");
  }
  
  public static Translation makeNervousTranslation2() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_1);
    return TestTranslationLoaderUtil.makeTranslation(0, 2, cal, "Be nervous");
  }
  
  public static Translation makeNervousTranslation3() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_2);
    return TestTranslationLoaderUtil.makeTranslation(7, 9, cal, "Be nervous");
  }
  
  public static Translation makeNervousTranslation4() {
    return TestTranslationLoaderUtil.makeTranslation(7, 9, "Be nervous");
  }
  
  public static Translation makeNervousTranslation5() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_2);
    return TestTranslationLoaderUtil.makeTranslation(6, 17, cal,  "Be nervous", "beeee nervouuuuussss");
  }
  
}
