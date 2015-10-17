package com.tkomiya.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tkomiya.models.Vocab;
import com.tkomiya.testutils.TestVocabListLoader;

public class VocabTest {

	@Test
	public void testEquals() {
		//Arrange
		Vocab monday = TestVocabListLoader.mondayVocab();
		Vocab monday2 = TestVocabListLoader.mondayVocab();
		Vocab tuesday = TestVocabListLoader.tuesdayVocab();
		
		//Assert
		assertEquals(monday, monday);
		assertEquals(monday, monday2);
		assertNotEquals(monday, null);
		assertNotEquals(monday, tuesday);
	}

}
