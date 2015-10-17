package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.json.JSONException;
import org.junit.Test;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.testutils.TestEnglishTranslationLoader;
import com.tkomiya.testutils.TestTranslationLoader;
import com.tkomiya.testutils.datageneration.KoreanTranslationFactory;

public class TranslationJsonConverterTest {

  @Test
  public void jsonToTranslation() throws JSONException, ParseException {
    assertEquals(TestEnglishTranslationLoader.mondayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayEnglishJson()));
    assertEquals(KoreanTranslationFactory.mondayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayKoreanJson()));
    assertEquals(TestTranslationLoader.mondayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayJapaneseJson()));
    assertEquals(TestEnglishTranslationLoader.tuesdayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.tuesdayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayKoreanJson()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayJapaneseJson()));
    assertEquals(TestEnglishTranslationLoader.wednesdayEnglishTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.wednesdayKoreanTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayKoreanJson()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayJapaneseJson()));
  }

  @Test
  public void translationToJson() throws JSONException {
    assertEquals(TestTranslationLoader.mondayEnglishJson(), TranslationJsonConverter.convertToJson(TestEnglishTranslationLoader.mondayEnglishTranslation()));
    assertEquals(TestTranslationLoader.mondayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.mondayKoreanTranslation()));
    assertEquals(TestTranslationLoader.mondayJapaneseJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.mondayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.tuesdayEnglishJson(), TranslationJsonConverter.convertToJson(TestEnglishTranslationLoader.tuesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.tuesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.tuesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.tuesdayJapaneseJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.tuesdayJapaneseTranslation()));
    assertEquals(TestTranslationLoader.wednesdayEnglishJson(), TranslationJsonConverter.convertToJson(TestEnglishTranslationLoader.wednesdayEnglishTranslation()));
    assertEquals(TestTranslationLoader.wednesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.wednesdayKoreanTranslation()));
    assertEquals(TestTranslationLoader.wednesdayJapaneseJson(), TranslationJsonConverter.convertToJson(TestTranslationLoader.wednesdayJapaneseTranslation()));
  }
  
}
