package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.tkomiya.main.Translation;
import com.tkomiya.testutils.datageneration.translation.KoreanTranslationFactory;

public class TranslationTest {

  @Test
  public void test() {
    Translation trans = KoreanTranslationFactory.makeNervousTranslation1();
    assertEquals("References to the same object should be equal", trans, trans);
    assertEquals("Objects with the same date, scores, and translation set should be equal", KoreanTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation1());
    assertNotEquals("Objects with the same date and translation but different scores should not be equal", KoreanTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation2());
    assertNotEquals("Objects with the same scores and translation but different date should not be equal", KoreanTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation3());
    assertNotEquals("Objects with the same scores and translation but one with no date should not be equal", KoreanTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation4());
    assertNotEquals("Objects with the same scores and date but different translation sets should not be equal", KoreanTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation5());
    assertEquals("Clone should make an Translation that is the same by equals()", KoreanTranslationFactory.makeNervousTranslation1(), (Translation) KoreanTranslationFactory.makeNervousTranslation1().clone());
  }

}
