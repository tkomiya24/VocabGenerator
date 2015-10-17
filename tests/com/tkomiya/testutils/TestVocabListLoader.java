package com.tkomiya.testutils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.main.Translation;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.models.VocabList;
import com.tkomiya.testutils.datageneration.KoreanTranslationFactory;
import com.tkomiya.testutils.datageneration.TestEnglishTranslationLoader;

public class TestVocabListLoader {

	public static VocabList makeTestList() {
		List<Vocab> vocabs = new ArrayList<Vocab>();
		vocabs.add(mondayVocab());
		vocabs.add(tuesdayVocab());
		vocabs.add(wednesdayVocab());
		VocabList vlist = new VocabList(vocabs);
		vlist.setName("Days of the Week");
		vlist.setChapter(22);
		return vlist; 
	}
	
	public static JSONObject makeTestListJson() throws JSONException {
		return new JSONObject().
				put(VocabListJsonConverter.VOCABLIST_NAME, "Days of the Week").
				put(VocabListJsonConverter.VOCABLIST_CHAPTER, 22).
				put(VocabListJsonConverter.VOCAB, new JSONArray().
						put(mondayJsonObject()).
						put(tuesdayJsonObject()).
						put(wednesdayJsonObject()));
	}
	
	public static Vocab mondayVocab() {
	  return makeVocab(SupportedLanguage.ENGLISH, TestEnglishTranslationLoader.mondayEnglishTranslation(), KoreanTranslationFactory.mondayKoreanTranslation(), TestTranslationLoader.mondayJapaneseTranslation());
	}
	
	public static Vocab tuesdayVocab() {
	  return makeVocab(SupportedLanguage.ENGLISH, TestEnglishTranslationLoader.tuesdayEnglishTranslation(), KoreanTranslationFactory.tuesdayKoreanTranslation(), TestTranslationLoader.tuesdayJapaneseTranslation());
	}
	
	public static Vocab wednesdayVocab() {
		return makeVocab(SupportedLanguage.ENGLISH, TestEnglishTranslationLoader.wednesdayEnglishTranslation(), KoreanTranslationFactory.wednesdayKoreanTranslation(), TestTranslationLoader.wednesdayJapaneseTranslation());
	}
	
	private static Vocab makeVocab(SupportedLanguage primaryLanguage, Translation englishTranslation, Translation koreanTranslation, Translation japaneseTranslation) {
	  Vocab vocab = new Vocab(primaryLanguage);
//    vocab.addTranslation(SupportedLanguage.ENGLISH, englishTranslation);
//    vocab.addTranslation(SupportedLanguage.KOREAN, koreanTranslation);
//    vocab.addTranslation(SupportedLanguage.JAPANESE, japaneseTranslation);
    return vocab;
	}
	
	public static JSONObject vocablistJson() throws JSONException {
		return new JSONObject().
				put(VocabListJsonConverter.VOCABLIST_CHAPTER, 22).
				put(VocabListJsonConverter.VOCABLIST_NAME, "Days of the Week").
				put(VocabListJsonConverter.VOCAB, new JSONArray().
					put(mondayJsonObject()).
					put(tuesdayJsonObject()).
					put(wednesdayJsonObject())
				);
	}

	public static JSONObject wednesdayJsonObject() throws JSONException {
		return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, Vocab.SupportedLanguage.ENGLISH.toString()).
				put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), makeTranslation("수요일", 1, 7)).
				put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), makeTranslation("水曜日", 9, 15)).
				put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), makeTranslation("Wednesday", 0, 0)).
				put(VocabJsonConverter.LAST_TESTED, "2015-01-15T12:15:44.000Z"); 
	}
	
	public static JSONObject tuesdayJsonObject() throws JSONException {
		return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, Vocab.SupportedLanguage.ENGLISH.toString()).
				put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), makeTranslation("화요일", 6, 14)).
				put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), makeTranslation("火曜日", 1, 7)).
				put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), makeTranslation("Tuesday", 4, 5)).
				put(VocabJsonConverter.LAST_TESTED, "1991-07-17T06:40:44.000Z"); 
	}
	
	public static JSONObject mondayJsonObject() throws JSONException {
		return new JSONObject().put(VocabJsonConverter.PRIMARY_LANGUAGE, Vocab.SupportedLanguage.ENGLISH.toString()).
				put(Vocab.SupportedLanguage.KOREAN.toString().toLowerCase(), makeTranslation("월요일", 1, 1)).
				put(Vocab.SupportedLanguage.JAPANESE.toString().toLowerCase(), makeTranslation("月曜日", 2, 2)).
				put(Vocab.SupportedLanguage.ENGLISH.toString().toLowerCase(), makeTranslation("Monday", 0, 0)).
				put(VocabJsonConverter.LAST_TESTED, ""); 
	}
	
	public static String wednesdayJsonString() throws JSONException {
		return wednesdayJsonObject().toString();
	}

	private static JSONObject makeTranslation(String translation, int timesCorrect, int timesTested) throws JSONException {
		return new JSONObject().
				put(VocabJsonConverter.TRANSLATION, translation).
				put(VocabJsonConverter.TIMES_CORRECT, timesCorrect).
				put(VocabJsonConverter.TIMES_TESTED, timesTested);
	}
}
