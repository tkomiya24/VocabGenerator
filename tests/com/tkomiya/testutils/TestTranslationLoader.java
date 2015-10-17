package com.tkomiya.testutils;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.main.Translation;

public class TestTranslationLoader {

  public static JSONObject mondayEnglishJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("Monday", 0, 0, "");
  }
  
  public static JSONObject mondayKoreanJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("월요일", 1, 1, "");
  }

  public static JSONObject mondayJapaneseJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("月曜日", 2, 2, "");
  }
  
  public static JSONObject tuesdayEnglishJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("Tuesday", 4, 5, "1991-07-17T06:40:44.000Z");
  }
  
  public static JSONObject tuesdayKoreanJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("화요일", 6, 14, "1991-07-17T06:40:44.000Z");
  }

  public static JSONObject tuesdayJapaneseJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("火曜日", 1, 7, "1991-07-17T06:40:44.000Z");  
  }
  
  public static JSONObject wednesdayEnglishJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("Wednesday", 0, 0, "2015-01-15T12:15:44.000Z");
  }
  
  public static JSONObject wednesdayKoreanJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("수요일", 1, 7, "2015-01-15T12:15:44.000Z");
  }

  public static JSONObject wednesdayJapaneseJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslation("水曜日", 9, 15, "2015-01-15T12:15:44.000Z");
  }

}
