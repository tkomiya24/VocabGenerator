package com.tkomiya.vocablistsaver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;

public class TextFileVocabListSaver implements VocabListSaver{
	
	public TextFileVocabListSaver() {

	}

	@Override
	public void saveVocabList(VocabList vocabList, String filePath) throws FileNotFoundException, IOException {
		
	}

	@Override
	public void saveVocabList(VocabList vocabList, File file) throws FileNotFoundException, IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8").newEncoder()));
		int lang = Vocab.ENGLISH;
		for (int i = 0; i < vocabList.size(); i++) {
			Vocab vocab = vocabList.get(i);
			writer.write(vocab.getTranslation(lang));
			writer.newLine();
		}
		writer.newLine();
		lang = Vocab.KOREAN;
		for (int i = 0; i < vocabList.size(); i++) {
			Vocab vocab = vocabList.get(i);
			writer.write(vocab.getTranslation(lang));
			writer.newLine();
		}
		writer.newLine();
		for (int i = 0; i < vocabList.size(); i++) {
			Vocab vocab = vocabList.get(i);
			writer.write(Integer.toString(vocab.getTimesCorrect(lang)));
			writer.write('/');
			writer.write(Integer.toString(vocab.getTimesCorrect(lang)));
			writer.newLine();
		}
		writer.close();
	}
	
	
}
