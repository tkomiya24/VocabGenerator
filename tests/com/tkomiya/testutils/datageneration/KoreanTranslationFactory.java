package com.tkomiya.testutils.datageneration;

import java.util.Calendar;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class KoreanTranslationFactory {

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
  
}
