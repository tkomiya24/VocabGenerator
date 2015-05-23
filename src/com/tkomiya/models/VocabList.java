package com.tkomiya.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
	
	public VocabList(VocabList vList) {
		listName = vList.getName();
		vocabList = new ArrayList<Vocab>(vList.getList());
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
	
	public void incrementTimesTested(int testLanguage) {
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
	
	@Override
	public String toString() {
		return listName;
	}

	public Collection<? extends Vocab> getAllTestedVocabsWithOneMistake(int language) {
		ArrayList<Vocab> testedVocab = new ArrayList<Vocab>();
		for (Vocab vocab : vocabList) {
			int timesTested = vocab.getTimesTested(language);
			int timesCorrect = vocab.getTimesCorrect(language);
			if (timesTested > 0 && timesCorrect < timesTested) {
				testedVocab.add(vocab);
			}
		}
		return testedVocab;
	}

	public void removeVocab(Vocab vocab) {
		vocabList.remove(vocab);
	}
	
	public List<Vocab> getList() {
		return vocabList;
	}
	
	public List<Vocab> search(String searchTerm) {
		List<Vocab> results = new ArrayList<Vocab>();
		for (Vocab vocab : vocabList) {
			if (vocab.contains(searchTerm)) {
				results.add(vocab);
			}
		}
		return results;
	}
	
	public int getMinimumTimesTested(int language) {
		int minimum = Integer.MAX_VALUE;
		for (Vocab vocab : vocabList) {
			if (vocab.getTimesTested(language) < minimum) {
				minimum = vocab.getTimesTested(language);
			}
		}
		return minimum;
	}
	
}
