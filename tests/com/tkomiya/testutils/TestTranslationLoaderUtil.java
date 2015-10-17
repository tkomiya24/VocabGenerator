package com.tkomiya.testutils;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.main.Translation;

public class TestTranslationLoaderUtil {

  public static JSONObject makeTranslation(String translation, int timesCorrect, int timesTested, String lastTested) throws JSONException {
    return new JSONObject().
        put(TranslationJsonConverter.TRANSLATION, translation).
        put(TranslationJsonConverter.TIMES_CORRECT, timesCorrect).
        put(TranslationJsonConverter.TIMES_TESTED, timesTested).
        put(TranslationJsonConverter.LAST_TESTED, lastTested);
  }
  
  public static Translation makeTranslation(int timesCorrect, int timesTested, String... translationString) {
    Translation translation = new Translation();
    translation.setTimesCorrect(timesCorrect);
    translation.setTimesTested(timesTested);
    for (String trans : translationString) {
      translation.addTranslation(trans);
    }
    return translation;
  }
  
  public static Translation makeTranslation(int timesCorrect, int timesTested, Calendar cal, String... translations) {
    Translation trans = makeTranslation(timesCorrect, timesTested, translations);
    trans.setLastTested(cal.getTime());
    return trans;
  }
  
}
