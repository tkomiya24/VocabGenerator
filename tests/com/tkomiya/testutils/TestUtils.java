package com.tkomiya.testutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TestUtils {

	public static String readFile(String filePath) throws UnsupportedEncodingException, FileNotFoundException {
		Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8")));
		String jsonString = new String();
		while (scan.hasNextLine()) {
			jsonString = jsonString.concat(scan.nextLine());
		}
		scan.close();
		return jsonString;
	}
	
}
