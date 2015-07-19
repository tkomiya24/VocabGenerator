package com.tkomiya.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tkomiya.models.Vocab;

public class VocabTest {

	@Test
	public void testEquals() {
		//Arrange
		Vocab monday = TestVocabListLoader.monday();
		Vocab monday2 = TestVocabListLoader.monday();
		Vocab tuesday = TestVocabListLoader.tuesday();
		
		//Assert
		assertTrue(monday.equals(monday));
		assertTrue(monday.equals(monday2));
		assertTrue(!monday.equals(null));
		assertTrue(!monday.equals(tuesday));
	}

}
