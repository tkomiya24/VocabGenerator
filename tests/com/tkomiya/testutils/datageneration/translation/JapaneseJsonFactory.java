package com.tkomiya.testutils.datageneration.translation;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.testutils.TestTranslationLoaderUtil;

public class JapaneseJsonFactory {
  
  public static JSONObject mondayJapaneseJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("月曜日", 2, 2, "");
  } 
  
  public static JSONObject tuesdayJapaneseJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("火曜日", 1, 7, "1991-07-17T06:40:44.000Z");  
  }
  
  public static JSONObject wednesdayJapaneseJson() throws JSONException {
    return TestTranslationLoaderUtil.makeTranslationJson("水曜日", 9, 15, "2015-01-15T12:15:44.000Z");
  }

}
