package com.tkomiya.infrastructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;

public class VocabJsonConverter {

	public static final String PRIMARY_LANGUAGE = "primaryLanguage";
	public static final String TIMES_TESTED = "timesTested";
	public static final String TIMES_CORRECT = "timesCorrect";
	public static final String TRANSLATION = "translation";
	private static final SimpleDateFormat SIMPLED_DATE_FORMAT= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	public static final String LAST_TESTED = "lastTested";
	
	public static JSONObject convertToJson(Vocab vocab) throws JSONException {
		JSONObject vocabJson = new JSONObject();
		vocabJson.put(PRIMARY_LANGUAGE, vocab.getPrimaryLanguage().toString());
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (vocab.getTranslation(language) != null) {
				JSONObject languageJson = new JSONObject();
				languageJson.put(TRANSLATION, vocab.getTranslation(language));
				languageJson.put(TIMES_CORRECT, vocab.getTimesCorrect(language));
				languageJson.put(TIMES_TESTED, vocab.getTimesTested(language));
				vocabJson.put(language.toString().toLowerCase(), languageJson);
			}
		}
		if (vocab.getLastTested() != null) {
			vocabJson.put(LAST_TESTED, SIMPLED_DATE_FORMAT.format(vocab.getLastTested()));
		} else {
			vocabJson.put(LAST_TESTED, "");
		}
		return vocabJson;  
	}
	
	public static Vocab convertToVocab(JSONObject json) throws JSONException, ParseException {
		String primaryLanguageString = json.getString(PRIMARY_LANGUAGE);
		SupportedLanguage primaryLanguage = SupportedLanguage.valueOf(primaryLanguageString.toUpperCase().trim());
		Vocab vocab = new Vocab(primaryLanguage);
		if (json.has(LAST_TESTED)) {
			vocab.setLastTested(SIMPLED_DATE_FORMAT.parse(json.getString(LAST_TESTED)));
		}
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (json.has(language.toString().toLowerCase())) {
				JSONObject langJson = json.getJSONObject(language.toString().toLowerCase());
				vocab.addLanguage(language, langJson.getString(TRANSLATION));
				vocab.setScore(language, langJson.getInt(TIMES_TESTED), langJson.getInt(TIMES_CORRECT));
			}
		}
		return vocab;
	}
}
