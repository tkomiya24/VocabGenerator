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
		Vocab vocab = vocabList.get(row);
		if (!vocab.hasTranslation(getLanguageFromColumn(column))) {
			return null;
		} else {
		  return getColumnValue(vocab, column);
		}
	}
	
	private Object getColumnValue(Vocab vocab, int column) {
	  if (isTranslationColumn(column)) {
	    return vocab.getTranslation(getLanguageFromColumn(column)).printTranslations();
	  } else if (isDateColumn(column)) {
	    return vocab.getTranslation(getLanguageFromColumn(column)).getLastTested();
	  } else if (isScoreColumn(column)) {
	    return formatScoreString(vocab, getLanguageFromColumn(column));
	  } else {
	    return null;
	  }
	}
	
	private Object formatScoreString(Vocab vocab, SupportedLanguage language) {
	  String timesCorrect = Integer.toString(vocab.getTimesCorrect(language));
    String timesTested = Integer.toString(vocab.getTimesTested(language));
    return timesCorrect + '/' + timesTested;
  }

  private boolean isScoreColumn(int column) {
    return column % 3 == 2;
  }

  private boolean isDateColumn(int column) {
    return column % 3 == 1;
  }

  private boolean isTranslationColumn(int column) {
    return column % 3 == 0;
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
	
//	@Override
//	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//		Vocab editedVocab = vocabList.get(rowIndex);
//		SupportedLanguage language = getLanguageFromColumn(columnIndex);
//		if (editedVocab.getTranslation(language) == null) {
//			editedVocab.addLanguage(language, (String) aValue);
//		} else {
//			editedVocab.editTranslation(language, (String) aValue);
//		}
//	}

	public Vocab getVocab(int selectedRow) {
		return vocabList.get(selectedRow);
	}
	
}
