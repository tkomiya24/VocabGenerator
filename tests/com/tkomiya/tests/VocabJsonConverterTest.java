package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.models.Vocab;
import com.tkomiya.testutils.Constants;
import com.tkomiya.testutils.TestVocabListLoader;

public class VocabJsonConverterTest {

	@Test
	public void testJsonToVocab() throws FileNotFoundException, JSONException, UnsupportedEncodingException {
		//Arrange
		JSONObject jsonVocab = testReadArrange();
		//Act
		Vocab vocab = VocabJsonConverter.convertToVocab(jsonVocab);
		//Assert
		assert(vocab.equals(TestVocabListLoader.wednesday()));
	}

	@Test
	public void testVocabToJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException {
		//Arrange
		Vocab wednesday = TestVocabListLoader.wednesday();
		//Act
		JSONObject actualJson = VocabJsonConverter.convertToJson(wednesday);
		//Assert
		assert(wednesday.equals(testReadArrange()));
	}
	
	private JSONObject testReadArrange() throws UnsupportedEncodingException,
			FileNotFoundException, JSONException {
		return new JSONObject(FileUtilities.readFile(Constants.JSON_TEST_FILE_DIRECTORY));
	}

}
