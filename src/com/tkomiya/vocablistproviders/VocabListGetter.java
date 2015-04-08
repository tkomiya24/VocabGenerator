package com.tkomiya.vocablistproviders;

import java.io.File;

import com.tkomiya.models.VocabList;

public interface VocabListGetter {

	VocabList getVocabListFromFile(File file) throws Exception;
}
