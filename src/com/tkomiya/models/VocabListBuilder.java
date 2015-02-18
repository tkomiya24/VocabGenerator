package com.tkomiya.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class VocabListBuilder {
	
	private VocabList vList;
	private int primaryLanguage;
	private boolean primaryLanguageSet;
	private int languageCount;
	private HashMap<Integer, List<String>> map;
	
	public VocabListBuilder(){
		this.vList = new VocabList();
		map = new HashMap<Integer, List<String>>();
		primaryLanguageSet = false;
		languageCount = 0;
	}
	
	public void setName(String name){
		vList.setName(name);
	}
	
	public void setPrimaryLanguage(int language, List<String> words){
		this.primaryLanguage = language;
		primaryLanguageSet = true;
		map.put(language, words);
		languageCount++;
	}
	
	public void addLanguage(int language, List<String> words){
		map.put(language, words);
		languageCount++;
	}
	
	//TODO make a concrete version of the Exception class.
	public VocabList build() throws Exception{
		
		if(!primaryLanguageSet || languageCount < 2){
			throw new Exception();
		}
		
		List<String> vocabs = map.get(primaryLanguage);
		Set<Integer> languages = new HashSet<Integer>();
		languages.addAll(map.keySet());
		languages.remove(primaryLanguage);
		
		for(int i = 0; i < vocabs.size(); i++){
			Vocab vocab = new Vocab(this.primaryLanguage);
			vocab.addLanguage(primaryLanguage, vocabs.get(i));
			
			for(Integer lang : languages){
				String translation = map.get(lang).get(i);
				vocab.addLanguage(lang, translation);
			}		
			vList.addVocab(vocab);
		}
		return vList;
	}
	
	public int getLanguagesSet(){
		return this.languageCount;
	}
}
