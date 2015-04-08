package com.tkomiya.vocablistproviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.tkomiya.models.VocabList;

public interface VocabListProvider {

	VocabList getVocabListFromFile(File file) throws Exception;
	
	void saveVocabList(VocabList vocabList, String filePath) throws FileNotFoundException, IOException;
	
	void saveVocabList(VocabList vocabList, File file) throws FileNotFoundException, IOException;
}
