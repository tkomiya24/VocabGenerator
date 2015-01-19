package com.tkomiya.vocablistsaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.tkomiya.models.VocabList;

public interface VocabListSaver {

		void saveVocabList(VocabList vocabList, String filePath) throws FileNotFoundException, IOException;
		
		void saveVocabList(VocabList vocabList, File file) throws FileNotFoundException, IOException;
}
