package com.tkomiya.listgetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewlineSeparatedTextfileStringListGetter implements ListStringGetter {

	@Override
	public List<String> getVocabFromFile(String filename) throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File(filename);
		return getVocabFromFile(file);
	}

	@Override
	public List<String> getVocabFromFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
		
		Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
		List<String> vocab = new ArrayList<String>();
		
		while(scan.hasNextLine()){
			vocab.add(scan.nextLine());
		}
		scan.close();
		return vocab;
	}

}
