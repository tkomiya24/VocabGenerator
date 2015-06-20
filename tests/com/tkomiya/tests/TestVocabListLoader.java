package com.tkomiya.tests;

import java.util.ArrayList;
import java.util.List;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;

public class TestVocabListLoader {

	public static VocabList makeTestList() {
		List<Vocab> vocabs = new ArrayList<Vocab>();
		vocabs.add(monday());
		vocabs.add(tuesday());
		vocabs.add(wednesday());
		VocabList vlist = new VocabList(vocabs);
		vlist.setName("Days of the Week");
		return vlist; 
	}
	
	public static Vocab monday() {
		Vocab monday = new Vocab(Vocab.ENGLISH);
		monday.addLanguage(Vocab.ENGLISH, "Monday");
		monday.setScore(Vocab.ENGLISH, 0, 0);
		monday.addLanguage(Vocab.KOREAN, "월요일");
		monday.setScore(Vocab.KOREAN, 1, 1);
		monday.addLanguage(Vocab.JAPANESE, "月曜日");
		monday.setScore(Vocab.JAPANESE, 2, 2);
		return monday;
	}
	
	public static Vocab tuesday() {
		Vocab tuesday = new Vocab(Vocab.ENGLISH);
		tuesday.addLanguage(Vocab.ENGLISH, "Tuesday");
		tuesday.addLanguage(Vocab.KOREAN, "화요일");
		tuesday.addLanguage(Vocab.JAPANESE, "火曜日");
		tuesday.setScore(Vocab.ENGLISH, 4, 5);
		tuesday.setScore(Vocab.KOREAN, 6, 14);
		tuesday.setScore(Vocab.JAPANESE, 1, 7);
		return tuesday;
	}
	
	public static Vocab wednesday() {
		Vocab wednesday = new Vocab(Vocab.ENGLISH);
		wednesday.addLanguage(Vocab.ENGLISH, "Wednesday");
		wednesday.addLanguage(Vocab.KOREAN, "수요일");
		wednesday.addLanguage(Vocab.JAPANESE, "水曜日");
		wednesday.setScore(Vocab.ENGLISH, 0, 0);
		wednesday.setScore(Vocab.KOREAN, 1, 7);
		wednesday.setScore(Vocab.JAPANESE, 9, 15);
		return wednesday;
	}
	
}
