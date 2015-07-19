package com.tkomiya.tests;

import java.util.ArrayList;
import java.util.List;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
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
		Vocab monday = new Vocab(SupportedLanguage.ENGLISH);
		monday.addLanguage(SupportedLanguage.ENGLISH, "Monday");
		monday.setScore(SupportedLanguage.ENGLISH, 0, 0);
		monday.addLanguage(SupportedLanguage.KOREAN, "월요일");
		monday.setScore(SupportedLanguage.KOREAN, 1, 1);
		monday.addLanguage(SupportedLanguage.JAPANESE, "月曜日");
		monday.setScore(SupportedLanguage.JAPANESE, 2, 2);
		return monday;
	}
	
	public static Vocab tuesday() {
		Vocab tuesday = new Vocab(SupportedLanguage.ENGLISH);
		tuesday.addLanguage(SupportedLanguage.ENGLISH, "Tuesday");
		tuesday.addLanguage(SupportedLanguage.KOREAN, "화요일");
		tuesday.addLanguage(SupportedLanguage.JAPANESE, "火曜日");
		tuesday.setScore(SupportedLanguage.ENGLISH, 4, 5);
		tuesday.setScore(SupportedLanguage.KOREAN, 6, 14);
		tuesday.setScore(SupportedLanguage.JAPANESE, 1, 7);
		return tuesday;
	}
	
	public static Vocab wednesday() {
		Vocab wednesday = new Vocab(SupportedLanguage.ENGLISH);
		wednesday.addLanguage(SupportedLanguage.ENGLISH, "Wednesday");
		wednesday.addLanguage(SupportedLanguage.KOREAN, "수요일");
		wednesday.addLanguage(SupportedLanguage.JAPANESE, "水曜日");
		wednesday.setScore(SupportedLanguage.ENGLISH, 0, 0);
		wednesday.setScore(SupportedLanguage.KOREAN, 1, 7);
		wednesday.setScore(SupportedLanguage.JAPANESE, 9, 15);
		return wednesday;
	}
	
}
