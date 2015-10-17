package com.tkomiya.infrastructure;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;

public class VocabJsonConverter {

	public static final String PRIMARY_LANGUAGE = "primaryLanguage";
	public static final String TIMES_TESTED = "timesTested";
	public static final String TIMES_CORRECT = "timesCorrect";
	public static final String TRANSLATION = "translation";
	public static final String LAST_TESTED = "lastTested";
	
	public static JSONObject convertToJson(Vocab vocab) throws JSONException {
		JSONObject vocabJson = new JSONObject();
		vocabJson.put(PRIMARY_LANGUAGE, vocab.getPrimaryLanguage().toString());
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (vocab.getTranslation(language) != null) {
				vocabJson.put(language.toString().toLowerCase(), TranslationJsonConverter.convertToJson(vocab.getTranslation(language)));
			}
		}
		return vocabJson;  
	}
	
	public static Vocab convertToVocab(JSONObject json) throws JSONException, ParseException {
		String primaryLanguageString = json.getString(PRIMARY_LANGUAGE);
		SupportedLanguage primaryLanguage = SupportedLanguage.valueOf(primaryLanguageString.toUpperCase().trim());
		Vocab vocab = new Vocab(primaryLanguage);
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (json.has(language.toString().toLowerCase())) {
				vocab.addTranslation(language, TranslationJsonConverter.convertToTranslation(json.getJSONObject(language.toString().toLowerCase())));
			}
		}
		return vocab;
	}
}
