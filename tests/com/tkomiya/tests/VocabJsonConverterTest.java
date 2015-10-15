package com.tkomiya.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.models.Vocab;
import com.tkomiya.testutils.Constants;
import com.tkomiya.testutils.TestVocabListLoader;

public class VocabJsonConverterTest {

	@Test
	public void testJsonToVocab() throws FileNotFoundException, JSONException, UnsupportedEncodingException, ParseException {
		//Arrange
		JSONObject jsonVocab = TestVocabListLoader.wednesdayJsonObject();
		//Act
		Vocab vocab = VocabJsonConverter.convertToVocab(jsonVocab);
		//Assert
		assertEquals("The JSON read should be equal to the Wednesday Vocab object", TestVocabListLoader.wednesdayVocab(), vocab);
	}

	@Test
	public void testVocabToJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException {
		//Arrange
		Vocab wednesday = TestVocabListLoader.wednesdayVocab();
		//Act
		JSONObject actualJson = VocabJsonConverter.convertToJson(wednesday);
		//Assert
		JSONAssert.assertEquals(TestVocabListLoader.wednesdayJsonString(), actualJson, false);
	}

}
