package com.tkomiya.infrastructure;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.tkomiya.models.Vocab;
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
		if (column % 2 == 0) {
			return Vocab.SUPPORTED_LANGUAGES[column/2];
		} else {
			return "Score";
		}
	}
	
	@Override
	public int getColumnCount() {
		return Vocab.VOCAB_SUPPORT_SIZE * 2;
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
		int lang = column/2;
		Vocab vocab = vocabList.get(row);
		if (vocab.getTranslation(lang) == null) {
			return null;
		}
		if (column % 2 == 0) {
			return vocab.getTranslation(lang);		
		} else {
			String timesCorrect = Integer.toString(vocab.getTimesCorrect(lang));
			String timesTested = Integer.toString(vocab.getTimesTested(lang));
			return timesCorrect + '/' + timesTested;			
		}
	}
	
	public void setVocabList(VocabList list){
		vocabList = list;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex / 2 < Vocab.VOCAB_SUPPORT_SIZE && rowIndex < vocabList.size() && columnIndex % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Vocab editedVocab = vocabList.get(rowIndex);
		int language = columnIndex / 2;
		editedVocab.setTranslation(language, (String) aValue);
	}
	
}
