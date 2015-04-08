package com.tkomiya.vocablistproviders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tkomiya.models.VocabList;

public class SerializedFileVocabListProvider implements VocabListProvider {

	@Override
	public VocabList getVocabListFromFile(File file) throws Exception{
		
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		VocabList vl = (VocabList) ois.readObject();
		ois.close();
		return vl;
	}

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
