package com.tkomiya.infrastructure;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.models.Vocab;

public class VocabJsonConverter {

	public static final String PRIMARY_LANGUAGE = "Primary Language";
	public static final String TIMES_TESTED = "Times Tested";
	public static final String TIMES_CORRECT = "Times Correct";
	public static final String TRANSLATION = "Translation";
	
	public static JSONObject convertToJson(Vocab vocab) {
		JSONObject vocabJson = new JSONObject();
		try {
			vocabJson.put(PRIMARY_LANGUAGE, Vocab.SUPPORTED_LANGUAGES[vocab.getPrimaryLanguage()]);
			for (int i : Vocab.SUPPORTED_LANGUAGES_INTS) {
				if (vocab.getTranslation(i) != null) {
					JSONObject languageJson = new JSONObject();
					languageJson.put(TRANSLATION, vocab.getTranslation(i));
					languageJson.put(TIMES_CORRECT, vocab.getTimesCorrect(i));
					languageJson.put(TIMES_TESTED, vocab.getTimesTested(i));
					vocabJson.put(Vocab.SUPPORTED_LANGUAGES[i], languageJson);
				}
			}
			return vocabJson;  
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Vocab convertToVocab(JSONObject json) {
		try {
			String primaryLanguageString = json.getString(PRIMARY_LANGUAGE);
			int primaryLanguage = Arrays.asList(Vocab.SUPPORTED_LANGUAGES).indexOf(primaryLanguageString);
			Vocab vocab = new Vocab(primaryLanguage);
			for (int i : Vocab.SUPPORTED_LANGUAGES_INTS) {
				if (json.has(Vocab.SUPPORTED_LANGUAGES[i])) {
					JSONObject langJson = json.getJSONObject(Vocab.SUPPORTED_LANGUAGES[i]);
					vocab.addLanguage(i, langJson.getString(TRANSLATION));
					vocab.setScore(i, json.getInt(TIMES_TESTED), json.getInt(TIMES_CORRECT));
				}
			}
			return vocab;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}
}
