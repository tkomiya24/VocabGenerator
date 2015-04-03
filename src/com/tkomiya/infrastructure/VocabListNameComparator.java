package com.tkomiya.infrastructure;

import java.util.Comparator;

import com.tkomiya.models.VocabList;

public class VocabListNameComparator implements Comparator<VocabList> {

	@Override
	public int compare(VocabList o1, VocabList o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
