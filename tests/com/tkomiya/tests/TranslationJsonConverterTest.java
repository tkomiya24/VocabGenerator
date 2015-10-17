package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.tkomiya.infrastructure.TranslationJsonConverter;
import com.tkomiya.testutils.datageneration.translation.EnglishJsonFactory;
import com.tkomiya.testutils.datageneration.translation.EnglishTranslationFactory;
import com.tkomiya.testutils.datageneration.translation.JapaneseJsonFactory;
import com.tkomiya.testutils.datageneration.translation.JapaneseTranslationFactory;
import com.tkomiya.testutils.datageneration.translation.KoreanJsonFactory;
import com.tkomiya.testutils.datageneration.translation.KoreanTranslationFactory;

public class TranslationJsonConverterTest {

  @Test
  public void jsonToTranslation() throws JSONException, ParseException {
    assertEquals(EnglishTranslationFactory.mondayTranslation(), TranslationJsonConverter.convertToTranslation(EnglishJsonFactory.mondayEnglishJson()));
    assertEquals(KoreanTranslationFactory.mondayTranslation(), TranslationJsonConverter.convertToTranslation(KoreanJsonFactory.mondayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.mondayTranslation(), TranslationJsonConverter.convertToTranslation(JapaneseJsonFactory.mondayJapaneseJson()));
    assertEquals(EnglishTranslationFactory.tuesdayTranslation(), TranslationJsonConverter.convertToTranslation(EnglishJsonFactory.tuesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.tuesdayTranslation(), TranslationJsonConverter.convertToTranslation(KoreanJsonFactory.tuesdayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.tuesdayTranslation(), TranslationJsonConverter.convertToTranslation(JapaneseJsonFactory.tuesdayJapaneseJson()));
    assertEquals(EnglishTranslationFactory.wednesdayTranslation(), TranslationJsonConverter.convertToTranslation(EnglishJsonFactory.wednesdayEnglishJson()));
    assertEquals(KoreanTranslationFactory.wednesdayTranslation(), TranslationJsonConverter.convertToTranslation(KoreanJsonFactory.wednesdayKoreanJson()));
    assertEquals(JapaneseTranslationFactory.wednesdayTranslation(), TranslationJsonConverter.convertToTranslation(JapaneseJsonFactory.wednesdayJapaneseJson()));
  }

  @Test
  public void translationToJson() throws JSONException {
    JSONAssert.assertEquals(EnglishJsonFactory.mondayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.mondayTranslation()), false);
    JSONAssert.assertEquals(KoreanJsonFactory.mondayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.mondayTranslation()), false);
    JSONAssert.assertEquals(JapaneseJsonFactory.mondayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.mondayTranslation()), false);
    JSONAssert.assertEquals(EnglishJsonFactory.tuesdayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.tuesdayTranslation()), false);
    JSONAssert.assertEquals(KoreanJsonFactory.tuesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.tuesdayTranslation()), false);
    JSONAssert.assertEquals(JapaneseJsonFactory.tuesdayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.tuesdayTranslation()), false);
    JSONAssert.assertEquals(EnglishJsonFactory.wednesdayEnglishJson(), TranslationJsonConverter.convertToJson(EnglishTranslationFactory.wednesdayTranslation()), false);
    JSONAssert.assertEquals(KoreanJsonFactory.wednesdayKoreanJson(), TranslationJsonConverter.convertToJson(KoreanTranslationFactory.wednesdayTranslation()), false);
    JSONAssert.assertEquals(JapaneseJsonFactory.wednesdayJapaneseJson(), TranslationJsonConverter.convertToJson(JapaneseTranslationFactory.wednesdayTranslation()), false);
  }
  
}
