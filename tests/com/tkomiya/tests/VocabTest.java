package com.tkomiya.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

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
		Vocab nervous = VocabFactory.nervous();
		String[] correct = {"신경(을) 쓰-", "신경(이) 쓰이-"};
		String[] correct2 = { "신경(이) 쓰이-", "신경(을) 쓰-"};
		String[] incorrect = { "신경(이) 쓰이-" };
		String[] incorrect2 = {"신(이) 쓰이-", "신경(을) 쓰-"};
		
		//Assert
		assertEquals(monday, monday);
		assertEquals(monday, monday2);
		assertNotEquals(monday, null);
		assertNotEquals(monday, tuesday);
		assertNotEquals(VocabFactory.nervous(), VocabFactory.nervousTranslationDifferent());
		assertNotEquals(VocabFactory.nervous(), VocabFactory.nervousTranslationMissing());
		assertTrue(nervous.isCorrect(Vocab.SupportedLanguage.KOREAN, Arrays.asList(correct)));
		assertTrue(nervous.isCorrect(Vocab.SupportedLanguage.KOREAN, Arrays.asList(correct2)));
		assertFalse(nervous.isCorrect(Vocab.SupportedLanguage.ENGLISH, Arrays.asList(correct)));
		assertFalse(nervous.isCorrect(Vocab.SupportedLanguage.KOREAN, Arrays.asList(incorrect)));
		assertFalse(nervous.isCorrect(Vocab.SupportedLanguage.KOREAN,Arrays.asList( incorrect2)));
	}

}
