package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabListJsonConverter;
import com.tkomiya.models.VocabList;
import com.tkomiya.testutils.Constants;

public class VocabListJsonConverterTest {

	public static final String JSON_FILEPATH = Constants.TEST_DIRECTORY + "vocablist.json";
	
	@Test
	public void testReadJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException {
		//Arrange
		JSONObject vocabListJson = new JSONObject(FileUtilities.readFile(JSON_FILEPATH));
		VocabList expectedList = TestVocabListLoader.makeTestList();
		//Act
		VocabList vocabList = VocabListJsonConverter.convertJsonToVocabList(vocabListJson);
		//Assert
		assertEquals(expectedList, vocabList);
	}
	
	@Test
	public void testMakeJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException {
		//Arrange
		VocabList testList = TestVocabListLoader.makeTestList();
//		JSONObject expectedJson = new JSONObject(TestUtils.readFile(JSON_FILEPATH));
		//Act
		JSONObject actualJson = VocabListJsonConverter.convertVocabListToJson(testList);
		System.out.println(actualJson.toString());
		//Assert
		assertEquals(testList, VocabListJsonConverter.convertJsonToVocabList(actualJson));
	}
	
}
