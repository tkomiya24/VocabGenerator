package com.tkomiya.infrastructure;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;

public class VocabJsonConverter {

	public static final String PRIMARY_LANGUAGE = "Primary Language";
	public static final String TIMES_TESTED = "Times Tested";
	public static final String TIMES_CORRECT = "Times Correct";
	public static final String TRANSLATION = "Translation";
	
	public static JSONObject convertToJson(Vocab vocab) throws JSONException {
		JSONObject vocabJson = new JSONObject();
		vocabJson.put(PRIMARY_LANGUAGE, vocab.getPrimaryLanguage().toString());
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (vocab.getTranslation(language) != null) {
				JSONObject languageJson = new JSONObject();
				languageJson.put(TRANSLATION, vocab.getTranslation(language));
				languageJson.put(TIMES_CORRECT, vocab.getTimesCorrect(language));
				languageJson.put(TIMES_TESTED, vocab.getTimesTested(language));
				vocabJson.put(language.toString(), languageJson);
			}
		}
		return vocabJson;  
	}
	
	public static Vocab convertToVocab(JSONObject json) throws JSONException {
		String primaryLanguageString = json.getString(PRIMARY_LANGUAGE);
		SupportedLanguage primaryLanguage = SupportedLanguage.valueOf(primaryLanguageString.toUpperCase().trim());
		Vocab vocab = new Vocab(primaryLanguage);
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (json.has(language.toString())) {
				JSONObject langJson = json.getJSONObject(language.toString());
				vocab.addLanguage(language, langJson.getString(TRANSLATION));
				vocab.setScore(language, langJson.getInt(TIMES_TESTED), langJson.getInt(TIMES_CORRECT));
			}
		}
		return vocab;
	}
}
