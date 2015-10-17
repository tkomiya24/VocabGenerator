package com.tkomiya.infrastructure;

import javax.swing.table.AbstractTableModel;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.models.VocabList;

public class VocabListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1515968393637548946L;
	private VocabList vocabList;
	
	
	public VocabListTableModel() {

	}
	
	@Override
	public String getColumnName(int column) {
		if (column % 3 == 0) {
			return SupportedLanguage.values()[column/2].toString();
		} else if (column % 3 == 1) {
		  return "Last Tested";
		} else {
			return "Score";
		}
	}
	
	@Override
	public int getColumnCount() {
		return SupportedLanguage.values().length * 2;
	}

	@Override
	public int getRowCount() {
		return vocabList == null ? 0 : vocabList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (row >= vocabList.size()) {
			return null;
		}		
		SupportedLanguage lang = getLanguageFromColumn(column);
		Vocab vocab = vocabList.get(row);
		if (vocab.getTranslation(lang) == null) {
			return null;
		}
		if (column % 3 == 0) {
			return vocab.getTranslation(lang);		
		} else if (column % 3 == 1) {
		  return vocab.getLastTested(lang);
		} else {
			String timesCorrect = Integer.toString(vocab.getTimesCorrect(lang));
			String timesTested = Integer.toString(vocab.getTimesTested(lang));
			return timesCorrect + '/' + timesTested;			
		}
	}
	
	private SupportedLanguage getLanguageFromColumn(int column) {
		return SupportedLanguage.values()[column/3];
	}
	
	public void setVocabList(VocabList list){
		vocabList = list;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex / 2 < SupportedLanguage.size() && rowIndex < vocabList.size() && columnIndex % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Vocab editedVocab = vocabList.get(rowIndex);
		SupportedLanguage language = getLanguageFromColumn(columnIndex);
		if (editedVocab.getTranslation(language) == null) {
			editedVocab.addLanguage(language, (String) aValue);
		} else {
			editedVocab.editTranslation(language, (String) aValue);
		}
	}

	public Vocab getVocab(int selectedRow) {
		return vocabList.get(selectedRow);
	}
	
}
