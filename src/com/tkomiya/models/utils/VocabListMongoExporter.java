package com.tkomiya.models.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.models.VocabList;

public class VocabListMongoExporter {

	public static void saveVocabListsAsMongoDbJson(Collection<VocabList> vocablists, File file) throws JSONException, IOException {
		JSONArray vocablistArray = new JSONArray();
		for (VocabList vlist : vocablists) {
			vocablistArray.put(VocabListJsonConverter.convertVocabListToJson(vlist));
		}
		FileUtilities.writeFile(vocablistArray.toString(2), file);
	}
	
}
