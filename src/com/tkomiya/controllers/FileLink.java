package com.tkomiya.controllers;

import java.io.File;
import java.io.Serializable;

import com.tkomiya.models.VocabList;
import com.tkomiya.vocablistproviders.SerializedFileVocabListProvider;
import com.tkomiya.vocablistproviders.VocabListProvider;

public class FileLink implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6549658279077094117L;
	private File file;
	private String name;
	
	public FileLink(File file, String name){
		this.file = file;
		this.name = name;
	}
	
	public VocabList getVocabList() throws Exception{
		
		VocabListProvider vlg = new SerializedFileVocabListProvider();
		return vlg.getVocabListFromFile(file);
	}
	
	public File getFile(){
		return file;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setFile(File file){
		this.file = file;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {

		FileLink other = (FileLink) obj;
		return other.getFile().equals(this.file);
	}

	public void delete() {
		this.file.delete();
	}
}
