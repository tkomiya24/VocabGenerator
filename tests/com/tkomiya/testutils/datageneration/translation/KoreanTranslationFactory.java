package com.tkomiya.testutils.datageneration.translation;

import java.util.Calendar;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class KoreanTranslationFactory {
  
  private static final long RANDOM_LONG_2 = 8789456135156456L;
  private static final long RANDOM_LONG_1 = 564897894564156L;
  
  public static Translation mondayKoreanTranslation() {
    return TestTranslationLoaderUtil.makeTranslation(1, 1, "월요일");
  }
  
  public static Translation tuesdayKoreanTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1991, Calendar.JULY, 17, 6, 40, 44);
    return TestTranslationLoaderUtil.makeTranslation(6, 14, cal, "화요일");
  }
 
  public static Translation wednesdayKoreanTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2015, Calendar.JANUARY, 15, 12, 15, 44);
    return TestTranslationLoaderUtil.makeTranslation(1, 7, cal, "수요일");
  }
  
  public static Translation makeNervousTranslation1() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_1);
    return TestTranslationLoaderUtil.makeTranslation(6, 17, cal, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation2() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_1);
    return TestTranslationLoaderUtil.makeTranslation(0, 2, cal, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation3() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_2);
    return TestTranslationLoaderUtil.makeTranslation(6, 17, cal, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation4() {
    return TestTranslationLoaderUtil.makeTranslation(6, 17, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation5() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_2);
    return TestTranslationLoaderUtil.makeTranslation(6, 17, cal, "신경(을) 쓰-", "신경(이) 쓰이-", "Pen Island");
  }
  
}
