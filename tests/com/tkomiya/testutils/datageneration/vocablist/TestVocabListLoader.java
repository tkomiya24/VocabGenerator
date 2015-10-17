package com.tkomiya.testutils.datageneration.vocablist;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.testutils.datageneration.vocab.VocabFactory;
import com.tkomiya.testutils.datageneration.vocab.VocabJsonFactory;

public class TestVocabListLoader {

	public static VocabList makeTestList() {
		List<Vocab> vocabs = new ArrayList<Vocab>();
		vocabs.add(VocabFactory.mondayVocab());
		vocabs.add(VocabFactory.tuesdayVocab());
		vocabs.add(VocabFactory.wednesdayVocab());
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
						put(VocabJsonFactory.mondayJsonObject()).
						put(VocabJsonFactory.tuesdayJsonObject()).
						put(VocabJsonFactory.wednesdayJsonObject()));
	}
	
	public static JSONObject vocablistJson() throws JSONException {
		return new JSONObject().
				put(VocabListJsonConverter.VOCABLIST_CHAPTER, 22).
				put(VocabListJsonConverter.VOCABLIST_NAME, "Days of the Week").
				put(VocabListJsonConverter.VOCAB, new JSONArray().
					put(VocabJsonFactory.mondayJsonObject()).
					put(VocabJsonFactory.tuesdayJsonObject()).
					put(VocabJsonFactory.wednesdayJsonObject())
				);
	}
	
}
