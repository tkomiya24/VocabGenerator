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
	String pathToFiles;
	
	@Test
	public void testGetNameWithNoExtension() {

		//Act
		String noExtensionFileName = FileUtilities.getFileNameWithNoExtension(noExtensionFile);
		String extensionFileName = FileUtilities.getFileNameWithNoExtension(extensionFile);
		//Assert
		assertEquals("TESTFILE", noExtensionFileName);
		assertEquals("TESTFILE", extensionFileName);
	}

	@Test
	public void  testGetFileExtension(){
		//Act
		String noExtensionFileExtension = FileUtilities.getFileExtension(noExtensionFile);
		String extensionFileExtension = FileUtilities.getFileExtension(extensionFile);
		//Assert
		assertEquals("", noExtensionFileExtension);
		assertEquals(Main.VOCAB_LIST_FILE_EXTENSION, extensionFileExtension);
	}
	
	@Test
	public void testGetPathWithouFileName(){
		//Act
		String noExtensionFilePath = FileUtilities.getFilePathWithoutFileName(noExtensionFile);
		String extensionFilePath = FileUtilities.getFilePathWithoutFileName(extensionFile);
		//Assert
		assertEquals("./TEST/", noExtensionFilePath);
		assertEquals("./TEST/", extensionFilePath);
	}
	
	@Test
	public void testFormatFilepathForSerialization(){
		//Act
		String noExtensionFilePath = FileUtilities.formatFilepathForSerialization(noExtensionFile);
		String extensionFilePath = FileUtilities.formatFilepathForSerialization(extensionFile);
		//Assert
		assertEquals("./TEST/TESTFILE" + "." + Main.VOCAB_LIST_FILE_EXTENSION, noExtensionFilePath);
		assertEquals("./TEST/TESTFILE" + "." + Main.VOCAB_LIST_FILE_EXTENSION, extensionFilePath);
	}
}
