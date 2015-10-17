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
  
  public static Vocab nervous() {
    return makeVocab(SupportedLanguage.ENGLISH, EnglishTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation1(), null);
  }
  
  public static Vocab nervousTranslationMissing() {
    return makeVocab(SupportedLanguage.ENGLISH, EnglishTranslationFactory.makeNervousTranslation1(), null, null);
  }
  
  public static Vocab nervousTranslationDifferent() {
    return makeVocab(SupportedLanguage.ENGLISH, EnglishTranslationFactory.makeNervousTranslation1(), KoreanTranslationFactory.makeNervousTranslation2(), null);
  }
  
  private static Vocab makeVocab(SupportedLanguage primaryLanguage, Translation englishTranslation, Translation koreanTranslation, Translation japaneseTranslation) {
    Vocab vocab = new Vocab(primaryLanguage);
    addVocabIfNotNull(vocab, SupportedLanguage.ENGLISH, englishTranslation);
    addVocabIfNotNull(vocab, SupportedLanguage.KOREAN, koreanTranslation);
    addVocabIfNotNull(vocab, SupportedLanguage.JAPANESE, japaneseTranslation);
    return vocab;
  }
  
  private static void addVocabIfNotNull(Vocab vocab, SupportedLanguage language, Translation translation) {
    if (translation != null) {
      vocab.addTranslation(language, translation);
    }
  }
  
}
