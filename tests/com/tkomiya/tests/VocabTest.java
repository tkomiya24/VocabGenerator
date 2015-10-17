package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.tkomiya.models.Vocab;
import com.tkomiya.testutils.datageneration.vocab.VocabFactory;

public class VocabTest {

	@Test
	public void testEquals() {
		//Arrange
		Vocab monday = VocabFactory.mondayVocab();
		Vocab monday2 = VocabFactory.mondayVocab();
		Vocab tuesday = VocabFactory.tuesdayVocab();
		
		//Assert
		assertEquals(monday, monday);
		assertEquals(monday, monday2);
		assertNotEquals(monday, null);
		assertNotEquals(monday, tuesday);
		assertNotEquals(VocabFactory.nervous(), VocabFactory.nervousTranslationDifferent());
		assertNotEquals(VocabFactory.nervous(), VocabFactory.nervousTranslationMissing());
	}

}
