package com.tkomiya.testutils.datageneration.vocab;

import com.tkomiya.main.Translation;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.testutils.datageneration.translation.EnglishTranslationFactory;
import com.tkomiya.testutils.datageneration.translation.JapaneseTranslationFactory;
import com.tkomiya.testutils.datageneration.translation.KoreanTranslationFactory;

public class VocabFactory {

  public static Vocab mondayVocab() {
    return makeVocab(SupportedLanguage.ENGLISH, EnglishTranslationFactory.mondayTranslation(), KoreanTranslationFactory.mondayTranslation(), JapaneseTranslationFactory.mondayTranslation());
  }
  
  public static Vocab tuesdayVocab() {
    return makeVocab(SupportedLanguage.ENGLISH, EnglishTranslationFactory.tuesdayTranslation(), KoreanTranslationFactory.tuesdayTranslation(), JapaneseTranslationFactory.tuesdayTranslation());
  }
  
  public static Vocab wednesdayVocab() {
    return makeVocab(SupportedLanguage.ENGLISH, EnglishTranslationFactory.wednesdayTranslation(), KoreanTranslationFactory.wednesdayTranslation(), JapaneseTranslationFactory.wednesdayTranslation());
  }
  
  private static Vocab makeVocab(SupportedLanguage primaryLanguage, Translation englishTranslation, Translation koreanTranslation, Translation japaneseTranslation) {
    Vocab vocab = new Vocab(primaryLanguage);
//    vocab.addTranslation(SupportedLanguage.ENGLISH, englishTranslation);
//    vocab.addTranslation(SupportedLanguage.KOREAN, koreanTranslation);
//    vocab.addTranslation(SupportedLanguage.JAPANESE, japaneseTranslation);
    return vocab;
  }
  
}
