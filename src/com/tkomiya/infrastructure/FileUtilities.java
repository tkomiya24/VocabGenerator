package com.tkomiya.infrastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Scanner;

import com.tkomiya.main.MainController;
import com.tkomiya.models.Vocab;

public class FileUtilities {

	public static String getFileNameWithNoExtension(File file){
		String fullName = file.getName();
		int lastIndex = fullName.lastIndexOf('.');
		lastIndex = lastIndex == -1 ? fullName.length() : lastIndex;
		String nameWithoutExtension = fullName.substring(0,lastIndex);
		return nameWithoutExtension;
	}
	
	public static String getFileExtension(File file){
		String fileName = file.getName();
		int lastIndexOfPeriod = fileName.lastIndexOf('.');
		if(lastIndexOfPeriod == -1){
			return "";
		}
		String fileExtension = fileName.substring(lastIndexOfPeriod + 1, fileName.length());
		return fileExtension;
	}
	
	public static String getFilePathWithoutFileName(File file){
		String fullFilePathAndName = file.getPath();
		String fileName = file.getName();
		int lastOccurrenceIndex = fullFilePathAndName.lastIndexOf(fileName);
		return fullFilePathAndName.substring(0, lastOccurrenceIndex);
	}
	
	public static String formatFilepathForSerialization(File file) {
		String fileExtension = getFileExtension(file);
		if(fileExtension.equals(MainController.VOCAB_LIST_FILE_EXTENSION)){
			return file.getPath();
		}
		else{
			return getFilePathWithoutFileName(file) + getFileNameWithNoExtension(file) + "." + MainController.VOCAB_LIST_FILE_EXTENSION;
		}
	}
	
	public static String readFile(String filePath) throws UnsupportedEncodingException, FileNotFoundException {
		return readFile(new File(filePath));
	}
	
	public static String readFile(File file) throws UnsupportedEncodingException, FileNotFoundException {
		Scanner scan = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
		String jsonString = new String();
		while (scan.hasNextLine()) {
			jsonString = jsonString.concat(scan.nextLine());
		}
		scan.close();
		return jsonString;
	}
	
	public static void writeFile(String string, File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8").newEncoder()));
		writer.write(string);
		writer.close();
	}
	
}
