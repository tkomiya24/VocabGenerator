package com.tkomiya.infrastructure;

import java.util.Comparator;

import com.tkomiya.models.Vocab;

public class MostMistakenDescendingVocabComparator implements Comparator {

	private int language;
	
	public MostMistakenDescendingVocabComparator(int language) {
		this.language = language;
	}
	
	@Override
	public int compare(Object arg0, Object arg1) {
		Vocab vocab1 = (Vocab) arg0;
		Vocab vocab2 = (Vocab) arg1;
		return (vocab2.getTimesTested(language) - vocab2.getTimesCorrect(language)) - (vocab1.getTimesTested(language) - vocab1.getTimesCorrect(language));
	}

}
