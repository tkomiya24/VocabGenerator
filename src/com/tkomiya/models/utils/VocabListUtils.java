package com.tkomiya.models.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tkomiya.infrastructure.MostMistakenDescendingVocabComparator;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.models.VocabList;

public class VocabListUtils {

	public static VocabList getLeastTestedVocabList(Collection<VocabList> vocabLists, SupportedLanguage language) {
		VocabList minVocabList = null;
		int minimum = Integer.MAX_VALUE;
		for (VocabList vocabList : vocabLists) {
			if (vocabList.getMinimumTimesTested(language) < minimum) {
				minVocabList = vocabList;
				minimum = vocabList.getMinimumTimesTested(language);
			}
		}
		return minVocabList;
	}
	
	public static List<Vocab> findMostMistakenVocabs(Collection<? extends VocabList> vocabLists, SupportedLanguage language) {
		List<Vocab> allVocabsTestedAtLeastOnce = getAllVocabsMistakenAtLeastOnce(vocabLists, language);
		Collections.sort(allVocabsTestedAtLeastOnce, new MostMistakenDescendingVocabComparator(language));
		return allVocabsTestedAtLeastOnce;
	}
	
	private static List<Vocab> getAllVocabsMistakenAtLeastOnce(Collection<? extends VocabList> vocabLists, SupportedLanguage language) {
		ArrayList<Vocab> vocabs = new ArrayList<Vocab>();
		for (VocabList vocabList : vocabLists) {
			vocabs.addAll(vocabList.getAllTestedVocabsWithOneMistake(language));
		}
		return vocabs;
	}	
	
}
