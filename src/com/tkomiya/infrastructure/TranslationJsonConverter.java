package com.tkomiya.infrastructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.main.Translation;

public class TranslationJsonConverter {
  
  public static final String TIMES_TESTED = "timesTested";
  public static final String TIMES_CORRECT = "timesCorrect";
  public static final String TRANSLATION = "translation";
  public static final String LAST_TESTED = "lastTested";
  private static final SimpleDateFormat SIMPLED_DATE_FORMAT= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  
  public static Translation convertToTranslation(JSONObject json) throws JSONException, ParseException {
    Translation trans = new Translation();
    if (json.has(LAST_TESTED) && !json.getString(LAST_TESTED).trim().isEmpty()) {
      trans.setLastTested(SIMPLED_DATE_FORMAT.parse(json.getString(LAST_TESTED)));
    }
    trans.addTranslations(jsonArrayToCollection(json.getJSONArray(TRANSLATION)));
    trans.setTimesCorrect(json.getInt(TIMES_CORRECT));
    trans.setTimesTested(json.getInt(TIMES_TESTED));
    return trans;
  }

  private static Collection<String> jsonArrayToCollection(JSONArray jsonArray) throws JSONException {
    HashSet<String> set = new HashSet<String>();
    for (int i = 0; i < jsonArray.length(); i++) {
      set.add(jsonArray.getString(i));
    }
    return set;
  }
  
  public static JSONObject convertToJson(Translation translation) throws JSONException {
    JSONObject translationJson = new JSONObject();
    JSONObject languageJson = new JSONObject();
    languageJson.put(TRANSLATION, translationsToJsonArray(translation));
    languageJson.put(TIMES_CORRECT, translation.getTimesCorrect());
    languageJson.put(TIMES_TESTED, translation.getTimesTested());
    if (translation.getLastTested() != null) {
      translationJson.put(LAST_TESTED, SIMPLED_DATE_FORMAT.format(translation.getLastTested()));
    } else {
      translationJson.put(LAST_TESTED, "");
    }
    return translationJson;  
  }
  
  private static JSONArray translationsToJsonArray(Translation translation) {
    JSONArray array = new JSONArray();
    for (String translationString : translation.getTranslations()) {
      array.put(translationString);
    }
    return array;
  }
  
}
