package com.tkomiya.testutils.datageneration.translation;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class EnglishJsonFactory {

  public static JSONObject mondayEnglishJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("Monday", 0, 0, "");
  }

  public static JSONObject tuesdayEnglishJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("Tuesday", 4, 5, "1991-07-17T06:40:44.000Z");
  }
  
  public static JSONObject wednesdayEnglishJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("Wednesday", 0, 0, "2015-01-15T12:15:44.000Z");
  }
  
}
