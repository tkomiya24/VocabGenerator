package com.tkomiya.testutils;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.main.Translation;

public class TestTranslationLoader {
  
  private static final long RANDOM_LONG_2 = 8789456135156456L;
  private static final long RANDOM_LONG_1 = 564897894564156L;
  
  public static Translation mondayEnglishTranslation() {
    return makeTranslation(0, 0, "Monday");
  }
  
  public static Translation mondayKoreanTranslation() {
    return makeTranslation(1, 1, "월요일");
  }
  
  public static Translation mondayJapaneseTranslation() {
    return makeTranslation(2, 2, "月曜日");
  }
  
  public static Translation tuesdayEnglishTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1991, Calendar.JULY, 17, 6, 40, 44);
    return makeTranslation(4, 5, cal, "Tuesday");
  }
  
  public static Translation tuesdayKoreanTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1991, Calendar.JULY, 17, 6, 40, 44);
    return makeTranslation(6, 14, cal, "화요일");
  }
  
  public static Translation tuesdayJapaneseTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1991, Calendar.JULY, 17, 6, 40, 44);
    return makeTranslation(1, 7, cal, "火曜日");
  }
  
  public static Translation wednesdayEnglishTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2015, Calendar.JANUARY, 15, 12, 15, 44);
    return makeTranslation(0, 0, cal, "Wednesday");
  }
  
  public static Translation wednesdayKoreanTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2015, Calendar.JANUARY, 15, 12, 15, 44);
    return makeTranslation(1, 7, cal, "수요일");
  }
  
  public static Translation wednesdayJapaneseTranslation() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(2015, Calendar.JANUARY, 15, 12, 15, 44);
    return makeTranslation(9, 15, cal, "水曜日");
  }
  
  public static JSONObject mondayEnglishJson() throws JSONException {
    return makeTranslation("Monday", 0, 0, "");
  }
  
  public static JSONObject mondayKoreanJson() throws JSONException {
    return makeTranslation("월요일", 1, 1, "");
  }

  public static JSONObject mondayJapaneseJson() throws JSONException {
    return makeTranslation("月曜日", 2, 2, "");
  }
  
  public static JSONObject tuesdayEnglishJson() throws JSONException {
    return makeTranslation("Tuesday", 4, 5, "1991-07-17T06:40:44.000Z");
  }
  
  public static JSONObject tuesdayKoreanJson() throws JSONException {
    return makeTranslation("화요일", 6, 14, "1991-07-17T06:40:44.000Z");
  }

  public static JSONObject tuesdayJapaneseJson() throws JSONException {
    return makeTranslation("火曜日", 1, 7, "1991-07-17T06:40:44.000Z");  
  }
  
  public static JSONObject wednesdayEnglishJson() throws JSONException {
    return makeTranslation("Wednesday", 0, 0, "2015-01-15T12:15:44.000Z");
  }
  
  public static JSONObject wednesdayKoreanJson() throws JSONException {
    return makeTranslation("수요일", 1, 7, "2015-01-15T12:15:44.000Z");
  }

  public static JSONObject wednesdayJapaneseJson() throws JSONException {
    return makeTranslation("水曜日", 9, 15, "2015-01-15T12:15:44.000Z");
  }
 
  public static Translation makeNervousTranslation1() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_1);
    return makeTranslation(6, 17, cal, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation2() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_1);
    return makeTranslation(0, 2, cal, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation3() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_2);
    return makeTranslation(6, 17, cal, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation4() {
    return makeTranslation(6, 17, "신경(을) 쓰-", "신경(이) 쓰이-");
  }
  
  public static Translation makeNervousTranslation5() {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.setTimeInMillis(RANDOM_LONG_2);
    return makeTranslation(6, 17, cal, "신경(을) 쓰-", "신경(이) 쓰이-", "Pen Island");
  }
 
  private static JSONObject makeTranslation(String translation, int timesCorrect, int timesTested, String lastTested) throws JSONException {
    return new JSONObject().
        put(TranslationJsonConverter.TRANSLATION, translation).
        put(TranslationJsonConverter.TIMES_CORRECT, timesCorrect).
        put(TranslationJsonConverter.TIMES_TESTED, timesTested).
        put(TranslationJsonConverter.LAST_TESTED, lastTested);
  }
  
  private static Translation makeTranslation(int timesCorrect, int timesTested, String... translationString) {
    Translation translation = new Translation();
    translation.setTimesCorrect(timesCorrect);
    translation.setTimesTested(timesTested);
    for (String trans : translationString) {
      translation.addTranslation(trans);
    }
    return translation;
  }
  
  private static Translation makeTranslation(int timesCorrect, int timesTested, Calendar cal, String... translations) {
    Translation trans = makeTranslation(timesCorrect, timesTested, translations);
    trans.setLastTested(cal.getTime());
    return trans;
  }
}
