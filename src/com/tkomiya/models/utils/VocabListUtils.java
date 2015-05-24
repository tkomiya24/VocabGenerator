package com.tkomiya.models.utils;

import java.util.Collection;

import com.tkomiya.models.VocabList;

public class VocabListUtils {

	public static VocabList getLeastTestedVocabList(Collection<VocabList> vocabLists, int language) {
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
	
}
