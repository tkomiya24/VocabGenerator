package com.tkomiya.listgetter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ListStringGetter {

	List<String> getVocabFromFile(String filename) throws FileNotFoundException, UnsupportedEncodingException;
	
	List<String> getVocabFromFile(File file) throws FileNotFoundException, UnsupportedEncodingException;
}
