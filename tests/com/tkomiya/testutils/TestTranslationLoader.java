package com.tkomiya.testutils;

import com.tkomiya.main.Translation;

public class TestTranslationLoader {
  
  public Translation mondayEnglishTranslation() {
    return new Translation().
                setLastTested(null).
                setTimesCorrect(0).
                setTimesTested(0).
                addTranslation("Monday");
  }
  
  public Translation mondayKoreanTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(1).
        setTimesTested(1).
        addTranslation("월요일");
  }
  
  public Translation mondayJapaneseTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(2).
        setTimesTested(2).
        addTranslation("月曜日");
  }
  
  public Translation tuesdayEnglishTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(4).
        setTimesTested(5).
        addTranslation("Tuesday");
  }
  
  public Translation tuesdayKoreanTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(6).
        setTimesTested(14).
        addTranslation("화요일");
  }
  
  public Translation tuesdayJapaneseTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(1).
        setTimesTested(7).
        addTranslation("火曜日");
  }
  
  public Translation wednesdayEnglishTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(0).
        setTimesTested(0).
        addTranslation("Wednesday");
  }
  
  public Translation wednesdayKoreanTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(1).
        setTimesTested(7).
        addTranslation("수요일");
  }
  
  public Translation wednesdayJapaneseTranslation() {
    return new Translation().
        setLastTested(null).
        setTimesCorrect(9).
        setTimesTested(15).
        addTranslation("水曜日");
  }
  
}
