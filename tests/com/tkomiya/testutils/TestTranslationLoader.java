package com.tkomiya.testutils;

import org.json.JSONException;
import org.json.JSONObject;

public class TestTranslationLoader {
  
  public static JSONObject mondayKoreanJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("월요일", 1, 1, "");
  }
  
  public static JSONObject tuesdayKoreanJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("화요일", 6, 14, "1991-07-17T06:40:44.000Z");
  }

  public static JSONObject wednesdayKoreanJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("수요일", 1, 7, "2015-01-15T12:15:44.000Z");
  }

}
