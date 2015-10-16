package com.tkomiya.tests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.testutils.TestTranslationLoader;

public class TranslationJsonConverterTest {

  @Test
  public void jsonToTranslation() throws JSONException {
    assertEquals(TestTranslationLoader.mondayEnglishTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.mondayEnglishJson()));
    assertEquals(TestTranslationLoader.mondayKoreanTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.mondayKoreanJson()));
    assertEquals(TestTranslationLoader.mondayJapaneseTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.mondayJapaneseJson()));
    assertEquals(TestTranslationLoader.tuesdayEnglishTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.tuesdayEnglishJson()));
    assertEquals(TestTranslationLoader.tuesdayKoreanTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.tuesdayKoreanJson()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.tuesdayJapaneseJson()));
    assertEquals(TestTranslationLoader.wednesdayEnglishTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.wednesdayEnglishJson()));
    assertEquals(TestTranslationLoader.wednesdayKoreanTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.wednesdayKoreanJson()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseTranslation(), TranslationJsonConverter.jsonToTranslation(TestTranslationLoader.wednesdayJapaneseJson()));
  }

  @Test
  public void translationToJson() throws JSONException {
    assertEquals(TestTranslationLoader.mondayEnglishJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.mondayEnglishTranslation()));
    assertEquals(TestTranslationLoader.mondayKoreanJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.mondayKoreanTranslation()));
    assertEquals(TestTranslationLoader.mondayJapaneseJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.mondayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.tuesdayEnglishJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.tuesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.tuesdayKoreanJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.tuesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.tuesdayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.wednesdayEnglishJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.wednesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.wednesdayKoreanJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.wednesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseJson(), TranslationJsonConverter.translationToJson(TestTranslationLoader.wednesdayJapaneseTranslation()));
  }
  
}
