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
		float successRate1 = vocab2.getTimesTested(language) == Integer.MAX_VALUE ? 0 : (float) vocab2.getTimesCorrect(language) / vocab2.getTimesTested(language);
		float successRate0 = vocab1.getTimesTested(language) == Integer.MAX_VALUE ? 0 : (float) vocab1.getTimesCorrect(language) / vocab1.getTimesTested(language);
		if (successRate1 > successRate0) {
			return -1;
		} else if (successRate1 < successRate0) {
			return 1;
		} else {
			return 0;
		}
	}

}
