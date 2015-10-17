package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.testutils.TestTranslationLoader;

public class TranslationJsonConverterTest {

  @Test
  public void jsonToTranslation() throws JSONException {
    assertEquals(TestTranslationLoader.mondayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayEnglishJson()));
    assertEquals(TestTranslationLoader.mondayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayKoreanJson()));
    assertEquals(TestTranslationLoader.mondayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayJapaneseJson()));
    assertEquals(TestTranslationLoader.tuesdayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayEnglishJson()));
    assertEquals(TestTranslationLoader.tuesdayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayKoreanJson()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayJapaneseJson()));
    assertEquals(TestTranslationLoader.wednesdayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayEnglishJson()));
    assertEquals(TestTranslationLoader.wednesdayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayKoreanJson()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayJapaneseJson()));
  }

  @Test
  public void translationToJson() throws JSONException {
    assertEquals(TestTranslationLoader.mondayEnglishJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.mondayEnglishTranslation()));
    assertEquals(TestTranslationLoader.mondayKoreanJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.mondayKoreanTranslation()));
    assertEquals(TestTranslationLoader.mondayJapaneseJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.mondayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.tuesdayEnglishJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.tuesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.tuesdayKoreanJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.tuesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.tuesdayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.wednesdayEnglishJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.wednesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.wednesdayKoreanJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.wednesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.wednesdayJapaneseTranslation()));
  }
  
}
