package com.tkomiya.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class VocabList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7788088203628363163L;
	private String listName;
	private List<Vocab> vocabList;
	
	public VocabList(List<Vocab> vocab){
		this.vocabList = vocab;
	}
	
	protected VocabList(){
		listName = null;
		vocabList = new ArrayList<Vocab>();
	}
	
	protected VocabList(String listName){
		this.listName = listName;
		vocabList = new ArrayList<Vocab>();
	} 
	
	public void setName(String listName){
		this.listName = listName;
	}
	
	public String getName(){
		return listName;
	}
	
	public void addVocab(Vocab vocab){
		vocabList.add(vocab);
	}
	
	public void removeVocab(String word){
		for(int i = 0; i < vocabList.size();i++){
			
			Vocab vocab = vocabList.get(i);
			if(vocab.means(word)){
				vocabList.remove(i);
			}
			
		}
	}
	
	public int size(){
		return vocabList.size();
	}
	
	public Vocab get(int index){
		return vocabList.get(index);
	}
	
	public Iterator<Vocab> iterator(){
		return vocabList.iterator();
	}

	public void shuffle() {
		Collections.shuffle(vocabList);
	}

	public VocabList subList(int fromIndex, int toIndex) {
		
		return new VocabList(this.vocabList.subList(fromIndex, toIndex));
	}
	
	public void startNewTest(int testLanguage) {
		incrementTimesTestedForAllVocabs(testLanguage);
	}
	
	private void incrementTimesTestedForAllVocabs(int testLanguage) {
		for(Vocab vocab : vocabList) {
			vocab.incrementTimesTested(testLanguage);
		}
	}
	
	public void setCorrect(int index, int testedLanguage) {
		vocabList.get(index).incrementTimesCorrect(testedLanguage);
	}
}
