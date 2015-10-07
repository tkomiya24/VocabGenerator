package com.tkomiya.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.tkomiya.models.VocabList;
import com.tkomiya.testutils.TestVocabListLoader;
import com.tkomiya.vocablistproviders.TextFileVocabListProvider;

public class TextFileVocabListGetterTest {

	private TextFileVocabListProvider vocabListGetter;
	public static final String TEST_DIRECTORY = "./TEST/"; //TODO move to constants class
	
	@Before
	public void setUp() throws Exception {
		vocabListGetter = new TextFileVocabListProvider();
	}

	@Test
	public void testNormalSaveFile() throws Exception {
		//Arrange		
		VocabList vocabList1 = TestVocabListLoader.makeTestList();
		File testFile = new File(TEST_DIRECTORY + "saveFile.txt");
		//Act
		VocabList vocabList2 = vocabListGetter.getVocabListFromFile(testFile);
		//Assert
		assertTrue(vocabList1.equals(vocabList2));
	}
	
	@Test
	public void testJapaneseTranslationMissingSaveFile() {
		
	}
	
	@Test
	public void testSomeKoreanSomeJapaneseTranslationMissingSaveFile() {
		
	}

}
