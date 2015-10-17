package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.models.VocabList;
import com.tkomiya.testutils.Constants;
import com.tkomiya.testutils.datageneration.vocablist.TestVocabListLoader;

public class VocabListJsonConverterTest {

	public static final String JSON_FILEPATH = Constants.TEST_DIRECTORY + "vocablist.json";
	
	@Test
	public void testReadJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException, ParseException {
		assertEquals(TestVocabListLoader.makeTestList(), VocabListJsonConverter.convertJsonToVocabList(TestVocabListLoader.makeTestListJson()));
	}
	
	@Test
	public void testMakeJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException, ParseException {
		//Arrange
		VocabList testList = TestVocabListLoader.makeTestList();
		//Act
		JSONObject actualJson = VocabListJsonConverter.convertVocabListToJson(testList);
		//Assert
		JSONAssert.assertEquals(TestVocabListLoader.makeTestListJson().toString(), actualJson, false);
	}
	
}
