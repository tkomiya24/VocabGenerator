package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.testutils.TestTranslationLoader;
import com.tkomiya.testutils.datageneration.translation.EnglishJsonFactory;
import com.tkomiya.testutils.datageneration.translation.EnglishTranslationFactory;
import com.tkomiya.testutils.datageneration.translation.JapaneseTranslationFactory;
import com.tkomiya.testutils.datageneration.translation.KoreanTranslationFactory;

public class TranslationJsonConverterTest {

  @Test
  public void jsonToTranslation() throws JSONException, ParseException {
    assertEquals(EnglishTranslationFactory.mondayTranslation(), TranslationJsonConverter.convertToTranslation(EnglishJsonFactory.mondayEnglishJson()));
    assertEquals(KoreanTranslationFactory.mondayTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.mondayTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.mondayJapaneseJson()));
    assertEquals(EnglishTranslationFactory.tuesdayTranslation(), TranslationJsonConverter.convertToTranslation(EnglishJsonFactory.tuesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.tuesdayTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.tuesdayTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.tuesdayJapaneseJson()));
    assertEquals(EnglishTranslationFactory.wednesdayTranslation(), TranslationJsonConverter.convertToTranslation(EnglishJsonFactory.wednesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.wednesdayTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.wednesdayTranslation(), TranslationJsonConverter.convertToTranslation(TestTranslationLoader.wednesdayJapaneseJson()));
  }

  @Test
  public void translationToJson() throws JSONException {
    JSONAssert.assertEquals(EnglishJsonFactory.mondayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.mondayTranslation()), false);
    JSONAssert.assertEquals(TestTranslationLoader.mondayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.mondayTranslation()), false);
    JSONAssert.assertEquals(TestTranslationLoader.mondayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.mondayTranslation()), false);
    JSONAssert.assertEquals(EnglishJsonFactory.tuesdayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.tuesdayTranslation()), false);
    JSONAssert.assertEquals(TestTranslationLoader.tuesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.tuesdayTranslation()), false);
    JSONAssert.assertEquals(TestTranslationLoader.tuesdayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.tuesdayTranslation()), false);
    JSONAssert.assertEquals(EnglishJsonFactory.wednesdayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.wednesdayTranslation()), false);
    JSONAssert.assertEquals(TestTranslationLoader.wednesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.wednesdayTranslation()), false);
    JSONAssert.assertEquals(TestTranslationLoader.wednesdayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.wednesdayTranslation()), false);
  }
  
}
