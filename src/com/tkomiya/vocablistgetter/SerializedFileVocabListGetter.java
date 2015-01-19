package com.tkomiya.vocablistgetter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.tkomiya.models.VocabList;

public class SerializedFileVocabListGetter implements VocabListGetter {

	@Override
	public VocabList getVocabListFromFile(File file) throws Exception{
		
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		VocabList vl = (VocabList) ois.readObject();
		ois.close();
		return vl;
	}

}
