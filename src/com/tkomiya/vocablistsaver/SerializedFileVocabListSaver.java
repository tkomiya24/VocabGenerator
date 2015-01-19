package com.tkomiya.vocablistsaver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.tkomiya.models.VocabList;

public class SerializedFileVocabListSaver implements VocabListSaver {

	@Override
	public void saveVocabList(VocabList vocabList, String filePath) throws FileNotFoundException, IOException {
		
		File file = new File(filePath);
		saveVocabList(vocabList, file);
	}

	@Override
	public void saveVocabList(VocabList vocabList, File file) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		oos.writeObject(vocabList);
		oos.close();
	}
	

}
