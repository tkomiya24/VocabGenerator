package com.tkomiya.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tkomiya.models.Vocab;
import com.tkomiya.testutils.TestVocabListLoader;

public class VocabTest {

	@Test
	public void testEquals() {
		//Arrange
		Vocab monday = TestVocabListLoader.monday();
		Vocab monday2 = TestVocabListLoader.monday();
		Vocab tuesday = TestVocabListLoader.tuesday();
		
		//Assert
		assertTrue(monday.equals(monday));
		assertEquals(monday, monday);
		assertTrue(monday.equals(monday2));
		assertTrue(!monday.equals(null));
		assertTrue(!monday.equals(tuesday));
	}

}
