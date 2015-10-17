package com.tkomiya.testutils.datageneration.vocab;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.models.Vocab;

public class VocabJsonFactory {
  
  public static JSONObject wednesdayJsonObject() throws JSONException {
    return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, Vocab.SupportedLanguage.ENGLISH.toString()).
        put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), makeTranslation("수요일", 1, 7)).
        put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), makeTranslation("水曜日", 9, 15)).
        put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), makeTranslation("Wednesday", 0, 0)).
        put(VocabJsonConverter.LAST_TESTED, "2015-01-15T12:15:44.000Z"); 
  }
  
  public static JSONObject tuesdayJsonObject() throws JSONException {
    return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, Vocab.SupportedLanguage.ENGLISH.toString()).
        put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), makeTranslation("화요일", 6, 14)).
        put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), makeTranslation("火曜日", 1, 7)).
        put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), makeTranslation("Tuesday", 4, 5)).
        put(VocabJsonConverter.LAST_TESTED, "1991-07-17T06:40:44.000Z"); 
  }
  
  public static JSONObject mondayJsonObject() throws JSONException {
    return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, Vocab.SupportedLanguage.ENGLISH.toString()).
        put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), makeTranslation("월요일", 1, 1)).
        put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), makeTranslation("月曜日", 2, 2)).
        put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), makeTranslation("Monday", 0, 0)).
        put(VocabJsonConverter.LAST_TESTED, ""); 
  }
  
  private static JSONObject makeTranslation(String translation, int timesCorrect, int timesTested) throws JSONException {
    return new JSONObject().
        put(VocabJsonConverter.TRANSLATION, translation).
        put(VocabJsonConverter.TIMES_CORRECT, timesCorrect).
        put(VocabJsonConverter.TIMES_TESTED, timesTested);
  }
}