package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.TestTranslationLoader;

public class TranslationTest {

  @Test
  public void test() {
    Translation trans = TestTranslationLoader.makeNervousTranslation1();
    assertEquals("References to the same object should be equal", trans, trans);
    assertEquals("Objects with the same date, scores, and translation set shoudl be equal", TestTranslationLoader.makeNervousTranslation1(), TestTranslationLoader.makeNervousTranslation1());
    assertNotEquals("Objects with the same date and translation but different scores should not be equal", TestTranslationLoader.makeNervousTranslation1(), TestTranslationLoader.makeNervousTranslation2());
    assertNotEquals("Objects with the same scores and translation but different date should not be equal", TestTranslationLoader.makeNervousTranslation1(), TestTranslationLoader.makeNervousTranslation3());
    assertNotEquals("Objects with the same scores and translation but one with no date should not be equal", TestTranslationLoader.makeNervousTranslation1(), TestTranslationLoader.makeNervousTranslation4());
    assertNotEquals("Objects with the same scores and date but different translation sets should not be equal", TestTranslationLoader.makeNervousTranslation1(), TestTranslationLoader.makeNervousTranslation5());
  }

}
