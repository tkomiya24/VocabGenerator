package com.tkomiya.vocablistproviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.models.VocabList;

public class JsonFileVocabListProvider implements VocabListProvider {

	@Override
	public VocabList getVocabListFromFile(File file) throws Exception {
		String jsonString = FileUtilities.readFile(file);
		JSONObject listJson = new JSONObject(jsonString);
		return VocabListJsonConverter.convertJsonToVocabList(listJson);
	}

	@Override
	public void saveVocabList(VocabList vocabList, File file) throws FileNotFoundException, IOException, JSONException {
		JSONObject json = VocabListJsonConverter.convertVocabListToJson(vocabList);
		FileUtilities.writeFile(json.toString(), file);
	}

}
