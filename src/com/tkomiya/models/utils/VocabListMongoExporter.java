package com.tkomiya.models.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.json.JSONException;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.models.VocabList;

public class VocabListMongoExporter {

	public static void saveVocabListsAsMongoDbJson(Collection<VocabList> vocablists, File file) throws JSONException, IOException {
		StringBuilder builder = new StringBuilder();
		for (VocabList vlist : vocablists) {
			builder.append(VocabListJsonConverter.convertVocabListToJson(vlist).toString());
			builder.append('\n');
		}
		FileUtilities.writeFile(builder.toString(), file);
	}
	
}
