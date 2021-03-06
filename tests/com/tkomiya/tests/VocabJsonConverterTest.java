package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.testutils.datageneration.vocab.VocabFactory;
import com.tkomiya.testutils.datageneration.vocab.VocabJsonFactory;

public class VocabJsonConverterTest {

	@Test
	public void testJsonToVocab() throws FileNotFoundException, JSONException, UnsupportedEncodingException, ParseException {
		assertEquals("The JSON read should be equal to the Wednesday Vocab object", VocabFactory.wednesdayVocab(), VocabJsonConverter.convertToVocab(VocabJsonFactory.wednesdayJsonObject()));
	}

	@Test
	public void testVocabToJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException {
		JSONAssert.assertEquals(VocabJsonFactory.wednesdayJsonObject(), VocabJsonConverter.convertToJson(VocabFactory.wednesdayVocab()), false);
	}

}
