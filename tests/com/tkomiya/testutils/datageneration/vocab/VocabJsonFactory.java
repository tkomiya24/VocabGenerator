package com.tkomiya.testutils.datageneration.vocab;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.testutils.datageneration.translation.EnglishJsonFactory;
import com.tkomiya.testutils.datageneration.translation.JapaneseJsonFactory;
import com.tkomiya.testutils.datageneration.translation.KoreanJsonFactory;

public class VocabJsonFactory {
  
  public static JSONObject wednesdayJsonObject() throws JSONException {
    return makeJsonObject(SupportedLanguage.ENGLISH, 
              KoreanJsonFactory.wednesdayKoreanJson(), 
              JapaneseJsonFactory.wednesdayJapaneseJson(), 
              EnglishJsonFactory.wednesdayEnglishJson());
  }
  
  public static JSONObject tuesdayJsonObject() throws JSONException {
    return makeJsonObject(SupportedLanguage.ENGLISH, 
        KoreanJsonFactory.tuesdayKoreanJson(), 
        JapaneseJsonFactory.tuesdayJapaneseJson(), 
        EnglishJsonFactory.tuesdayEnglishJson());
  }
  
  public static JSONObject mondayJsonObject() throws JSONException {
    return makeJsonObject(SupportedLanguage.ENGLISH, 
        KoreanJsonFactory.mondayKoreanJson(), 
        JapaneseJsonFactory.mondayJapaneseJson(), 
        EnglishJsonFactory.mondayEnglishJson()); 
  }
 
  private static JSONObject makeJsonObject(SupportedLanguage primaryLanguage, JSONObject korean, JSONObject japanese, JSONObject english) throws JSONException {
    return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, primaryLanguage.toString()).
        put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), korean).
        put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), japanese).
        put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), english);
  }
}
