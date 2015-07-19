package com.tkomiya.vocablistproviders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;
import com.tkomiya.models.Vocab.SupportedLanguage;

public class TextFileVocabListProvider implements VocabListProvider {

	@Override
	public VocabList getVocabListFromFile(File file) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8").newDecoder()));
		ArrayList<String> englishWords = new ArrayList<String>();
		String next;
		while (!(next = reader.readLine()).isEmpty()) {
			englishWords.add(next);
		}
		ArrayList<String> koreanWords = new ArrayList<String>();
		while (!(next = reader.readLine()).isEmpty()) {
			koreanWords.add(next);
		}
		ArrayList<String> scores = new ArrayList<String>();
		while ((next = reader.readLine()) != null) {
			scores.add(next);
		}
		VocabListBuilder vocabListBuilder = new VocabListBuilder();
		vocabListBuilder.setPrimaryLanguage(SupportedLanguage.ENGLISH, englishWords);
		vocabListBuilder.addLanguage(SupportedLanguage.KOREAN, koreanWords);
		VocabList vocabList = vocabListBuilder.build();
		vocabList.setName(FileUtilities.getFileNameWithNoExtension(file));
		for (int i = 0; i < vocabList.size(); i++) {
			Vocab vocab = vocabList.get(i);
			vocab.setScore(SupportedLanguage.KOREAN, getTimesTested(scores.get(i)), getTimesCorrect(scores.get(i)));
		}
		return vocabList;
	}

	private int getTimesCorrect(String string) {
		String[] parts = string.split("/");
		return Integer.parseInt(parts[0]);
	}

	private int getTimesTested(String string) {
		String[] parts = string.split("/");
		return Integer.parseInt(parts[1]);
	}

	@Override
	public void saveVocabList(VocabList vocabList, File file) throws FileNotFoundException, IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8").newEncoder()));
		SupportedLanguage lang = SupportedLanguage.ENGLISH;
		for (int i = 0; i < vocabList.size(); i++) {
			Vocab vocab = vocabList.get(i);
			writer.write(vocab.getTranslation(lang));
			writer.newLine();
		}
		writer.newLine();
		lang = SupportedLanguage.KOREAN;
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
			writer.write(Integer.toString(vocab.getTimesTested(lang)));
			writer.newLine();
		}
		writer.close();
	}

}
