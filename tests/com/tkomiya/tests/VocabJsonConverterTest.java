package com.tkomiya.tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.tkomiya.infrastructure.VocabJsonConverter;
import com.tkomiya.models.Vocab;
import com.tkomiya.testutils.Constants;

public class VocabJsonConverterTest {

	@Test
	public void testJsonToVocab() throws FileNotFoundException, JSONException, UnsupportedEncodingException {
		//Arrange
		JSONObject jsonVocab = testReadArrange();
		//Act
		Vocab vocab = VocabJsonConverter.convertToVocab(jsonVocab);
		//Assert
		assertEquals(vocab, TestVocabListLoader.wednesday());
	}

	@Test
	public void testVocabToJson() throws UnsupportedEncodingException, FileNotFoundException, JSONException {
		//Arrange
		Vocab wednesday = TestVocabListLoader.wednesday();
		//Act
		JSONObject actualJson = VocabJsonConverter.convertToJson(wednesday);
		Vocab wednesdayReread = VocabJsonConverter.convertToVocab(actualJson);
		//Assert
		assertEquals(wednesday, wednesdayReread);
	}
	
	private JSONObject testReadArrange() throws UnsupportedEncodingException,
			FileNotFoundException, JSONException {
		File file = new File(Constants.JSON_TEST_FILE_DIRECTORY);
		Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
		String jsonString = new String();
		while (scan.hasNextLine()) {
			jsonString = jsonString.concat(scan.nextLine());
		}
		scan.close();
		return new JSONObject(jsonString);
	}

}
