package com.tkomiya.infrastructure;

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

		return Vocab.SUPPORTED_LANGUAGES[column];
	}
	
	@Override
	public int getColumnCount() {
		
		return Vocab.VOCAB_SUPPORT_SIZE;
	}

	@Override
	public int getRowCount() {
		
		return vocabList == null ? 0 : vocabList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		
		return vocabList.size() > arg0 ? vocabList.get(arg0).getTranslation(arg1) : null;
		
	}
	
	public void setVocabList(VocabList list){
		vocabList = list;
		fireTableDataChanged();
	}

}
