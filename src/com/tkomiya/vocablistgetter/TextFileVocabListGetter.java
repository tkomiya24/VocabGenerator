package com.tkomiya.vocablistgetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;

public class TextFileVocabListGetter implements VocabListGetter {

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
		vocabListBuilder.setPrimaryLanguage(Vocab.ENGLISH, englishWords);
		vocabListBuilder.addLanguage(Vocab.KOREAN, koreanWords);
		VocabList vocabList = vocabListBuilder.build();
		vocabList.setName(FileUtilities.getFileNameWithNoExtension(file));
		for (int i = 0; i < vocabList.size(); i++) {
			Vocab vocab = vocabList.get(i);
			vocab.setScore(Vocab.KOREAN, getTimesTested(scores.get(i)), getTimesCorrect(scores.get(i)));
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

}
