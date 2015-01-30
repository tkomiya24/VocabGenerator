package com.tkomiya.tests;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Test;
import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.main.Main;

public class FileUtilitiesTest {

	public static final String TEST_DIRECTORY = "./TEST/";
	File noExtensionFile = new File(TEST_DIRECTORY + "TESTFILE");
	File extensionFile = new File(TEST_DIRECTORY + "TESTFILE." + Main.VOCAB_LIST_FILE_EXTENSION);
	File extensionBlankFile = new File(TEST_DIRECTORY + "TESTFILE.");
	String pathToFiles;
	
	@Test
	public void testGetNameWithNoExtension() {

		//Act
		String noExtensionFileName = FileUtilities.getFileNameWithNoExtension(noExtensionFile);
		String extensionFileName = FileUtilities.getFileNameWithNoExtension(extensionFile);
		String extensionBlankFileName = FileUtilities.getFileNameWithNoExtension(extensionBlankFile);
		//Assert
		assertEquals("TESTFILE", noExtensionFileName);
		assertEquals("TESTFILE", extensionFileName);
		assertEquals("TESTFILE", extensionBlankFileName);
	}

	@Test
	public void  testGetFileExtension(){
		//Act
		String noExtensionFileExtension = FileUtilities.getFileExtension(noExtensionFile);
		String extensionFileExtension = FileUtilities.getFileExtension(extensionFile);
		String extensionBlankFileExtension = FileUtilities.getFileExtension(extensionBlankFile);
		//Assert
		assertEquals("", noExtensionFileExtension);
		assertEquals(Main.VOCAB_LIST_FILE_EXTENSION, extensionFileExtension);
		assertEquals("", extensionBlankFileExtension);
	}
	
	@Test
	public void testGetPathWithouFileName(){
		//Act
		String noExtensionFilePath = FileUtilities.getFilePathWithoutFileName(noExtensionFile);
		String extensionFilePath = FileUtilities.getFilePathWithoutFileName(extensionFile);
		String extensionBlankPath = FileUtilities.getFilePathWithoutFileName(extensionBlankFile);
		//Assert
		assertEquals("./TEST/", noExtensionFilePath);
		assertEquals("./TEST/", extensionFilePath);
		assertEquals("./TEST/", extensionBlankPath);
	}
	
	@Test
	public void testFormatFilepathForSerialization(){
		//Act
		String noExtensionFilePath = FileUtilities.formatFilepathForSerialization(noExtensionFile);
		String extensionFilePath = FileUtilities.formatFilepathForSerialization(extensionFile);
		String extensionBlankPath = FileUtilities.formatFilepathForSerialization(extensionBlankFile);
		//Assert
		assertEquals("./TEST/TESTFILE" + "." + Main.VOCAB_LIST_FILE_EXTENSION, noExtensionFilePath);
		assertEquals("./TEST/TESTFILE" + "." + Main.VOCAB_LIST_FILE_EXTENSION, extensionFilePath);
		assertEquals("./TEST/TESTFILE" + "." + Main.VOCAB_LIST_FILE_EXTENSION, extensionBlankPath);
	}
}
