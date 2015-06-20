package com.tkomiya.vocablistproviders;

import java.io.File;

import com.tkomiya.models.VocabList;

public interface VocabListProvider {

	VocabList getVocabListFromFile(File file) throws Exception;
	
	void saveVocabList(VocabList vocabList, File file) throws Exception;
}
