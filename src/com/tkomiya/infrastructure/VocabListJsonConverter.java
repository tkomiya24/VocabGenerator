package com.tkomiya.infrastructure;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;

public class VocabListJsonConverter {

	public static final String VOCABLIST_NAME = "name";
	public static final String VOCABLIST_CHAPTER = "chapter";
	public static final String VOCAB = "vocab";
	
	public static VocabList convertJsonToVocabList(JSONObject listJson) throws JSONException, ParseException {
		ArrayList<Vocab> vocabs = new ArrayList<Vocab>(); 
		JSONArray vocabListJson = listJson.getJSONArray(VOCAB);
		for (int i = 0; i < vocabListJson.length(); i++) {
			JSONObject vocabJson = vocabListJson.getJSONObject(i);
			Vocab vocab = VocabJsonConverter.convertToVocab(vocabJson);
			vocabs.add(vocab);
		}
		VocabList vlist = new VocabList(vocabs);
		vlist.setName(listJson.getString(VOCABLIST_NAME));
		if (listJson.has(VOCABLIST_CHAPTER)) {
			vlist.setChapter(listJson.getInt(VOCABLIST_CHAPTER));
		}

		return vlist;
	}

	public static JSONObject convertVocabListToJson(VocabList list) throws JSONException {
		JSONObject json = new JSONObject();
		json.put(VOCABLIST_NAME, list.getName());
		if (list.getChapter() != null) {
			json.put(VOCABLIST_CHAPTER, list.getChapter());
		}
		JSONArray array = new JSONArray();
		for (Vocab vocab : list) {
			JSONObject vocabJson = VocabJsonConverter.convertToJson(vocab);
			array.put(vocabJson);
		}
		json.put(VOCAB, array);
		return json;
	}
	
}
