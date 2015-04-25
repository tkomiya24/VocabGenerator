package com.tkomiya.vocablistproviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.tkomiya.exceptions.ListLengthsDoNotMatchException;
import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.listgetter.ListStringGetter;
import com.tkomiya.listgetter.NewlineSeparatedTextfileStringListGetter;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;

public class MultipleTextFileVocabListProvider implements VocabListProvider {
	
	private ListStringGetter listStringGetter;
	
	public MultipleTextFileVocabListProvider() {
		listStringGetter = new NewlineSeparatedTextfileStringListGetter();
	}
	
	@Override
	public VocabList getVocabListFromFile(File file) throws Exception {
		String fileName = FileUtilities.getFileNameWithNoExtension(file);
		String filePath = FileUtilities.getFilePathWithoutFileName(file);
		List<String> englishList;
		englishList = getEnglishList(fileName, filePath);
		VocabListBuilder vlb = new VocabListBuilder();
		vlb.setPrimaryLanguage(Vocab.ENGLISH, englishList);
		vlb.setName(FileUtilities.getFileNameWithNoExtension(file));
		List<String> koreanList;
		try {
			koreanList = getKoreanList(fileName, filePath);
			if (koreanList.size() != englishList.size()) {
				throw new ListLengthsDoNotMatchException();
			}
			vlb.addLanguage(Vocab.KOREAN, koreanList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> japaneseList;
		try {
			japaneseList = getJapaneseList(fileName, filePath);
			if (japaneseList.size() != englishList.size()) {
				throw new ListLengthsDoNotMatchException();
			}
			vlb.addLanguage(Vocab.JAPANESE, japaneseList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vlb.build();
	}

	@Override
	public void saveVocabList(VocabList vocabList, String filePath)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveVocabList(VocabList vocabList, File file)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	private List<String> getJapaneseList(String fileName, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		return listStringGetter.getVocabFromFile(filePath + "/" + fileName + " Japanese");
	}

	private List<String> getKoreanList(String fileName, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		return listStringGetter.getVocabFromFile(filePath + "/" + fileName + " Korean");
	}

	private List<String> getEnglishList(String fileName, String filePath) throws FileNotFoundException, UnsupportedEncodingException{
		File englishListFile = new File(filePath + "/" + fileName);
		return listStringGetter.getVocabFromFile(englishListFile);
	}

}
