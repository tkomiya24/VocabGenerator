package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.json.JSONException;
import org.junit.Test;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.testutils.TestTranslationLoader;
import com.tkomiya.testutils.datageneration.JapaneseTranslationFactory;
import com.tkomiya.testutils.datageneration.KoreanTranslationFactory;
import com.tkomiya.testutils.datageneration.EnglishTranslationFactory;

public class TranslationJsonConverterTest {

  @Test
  public void jsonToTranslation() throws JSONException, ParseException {
    assertEquals(EnglishTranslationFactory.mondayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayEnglishJson()));
    assertEquals(KoreanTranslationFactory.mondayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.mondayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayJapaneseJson()));
    assertEquals(EnglishTranslationFactory.tuesdayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.tuesdayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.tuesdayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayJapaneseJson()));
    assertEquals(EnglishTranslationFactory.wednesdayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.wednesdayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.wednesdayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayJapaneseJson()));
  }

  @Test
  public void translationToJson() throws JSONException {
    assertEquals(TestTranslationLoader.mondayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.mondayEnglishTranslation()));
    assertEquals(TestTranslationLoader.mondayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.mondayKoreanTranslation()));
    assertEquals(TestTranslationLoader.mondayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.mondayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.tuesdayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.tuesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.tuesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.tuesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.tuesdayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.wednesdayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.wednesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.wednesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.wednesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.wednesdayJapaneseTranslation()));
  }
  
}
