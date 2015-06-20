package com.tkomiya.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;

public class VocabListTest {

	List<String> englishWords;
	List<String> japaneseWords;	
	List<String> koreanWords;
	VocabList list;
	
	@Before
	public void setUp(){
		englishWords = new ArrayList<String>();
		englishWords.add("Monday");
		englishWords.add("Tuesday");
		englishWords.add("Wednesday");
		
		japaneseWords = new ArrayList<String>();
		japaneseWords.add("月曜日");
		japaneseWords.add("火曜日");
		japaneseWords.add("水曜日");
		
		koreanWords = new ArrayList<String>();
		koreanWords.add("월요일");
		koreanWords.add("화요일");
		koreanWords.add("수요일");
		
		VocabListBuilder vlb = new VocabListBuilder();
		vlb.setPrimaryLanguage(Vocab.ENGLISH, englishWords);
		vlb.addLanguage(Vocab.JAPANESE, japaneseWords);
		vlb.addLanguage(Vocab.KOREAN, koreanWords);
		try {
			list = vlb.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBuilder() {
		
		//Arrange
		Vocab vocab = new Vocab(Vocab.ENGLISH);
		vocab.addLanguage(Vocab.ENGLISH, "Thursday");
		vocab.addLanguage(Vocab.JAPANESE, "木曜日");
		vocab.addLanguage(Vocab.KOREAN, "목요일");
		
		//act
		list.addVocab(vocab);
		
		//Assert
	}

}
