package com.tkomiya.views;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.models.VocabList;

public class TypedTestController {

	private VocabList vList;
	private List<Vocab> incorrectlyAnsweredVocabList;
	private VocabList retestVocabList;
	private SupportedLanguage languageTested;
	private boolean isRetest;
	private TypedTestView view;
	
	public TypedTestController(VocabList vList, SupportedLanguage languageTested) {
		this.vList = new VocabList(vList);
		this.view = new TypedTestView(this, this.vList);
		this.incorrectlyAnsweredVocabList = new ArrayList<Vocab>();
		this.languageTested = languageTested;
		this.isRetest = false;
	}

	public void cheatButtonAction() {
		for(int i = 0; i < vList.size(); i++){					
			JTextField field = view.getFields().get(i);					
			if(checkAnswer(vList, i)) {
				field.setEditable(false);
				field.setBackground(Color.WHITE);
				field.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			}
			else {
				field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			}
		}
	}

	public void submitButtonAction() {
		VocabList vList;
		if (!isRetest) {
			vList = TypedTestController.this.vList;
		} else {
			vList = retestVocabList;
		}			
		for(int i = 0; i < vList.size(); i++){	
			JTextField field = view.getFields().get(i);
			field.setEditable(false);
			if (checkAnswer(vList, i)) {
				field.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				setCorrect(vList, i);
			}
			else {
				field.setText(vList.get(i).getTranslation(languageTested));
				field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				incorrectlyAnsweredVocabList.add(vList.get(i));
			}
		}
		disableSubmitAndGiveUpButtons();
		if (!incorrectlyAnsweredVocabList.isEmpty()) {
			enableRetestIncorrectButton();
		}	
		vList.incrementTimesTested(TypedTestController.this.languageTested);
		vList.updateLastTested();
	}

	public void enableRetestIncorrectButton() {
		view.getRetestIncorrectButton().setEnabled(true);
	}
	
	public void restartButtonAction() {
		incorrectlyAnsweredVocabList = new ArrayList<Vocab>();
		blankAllTextFields();
		resetTextFieldColours();
		makeAllTextFieldsEditableAgain();
		enableSubmitAndGiveUpButtons();
		disableRetestIncorrectButton();
	}
	
	public void retestIncorrectButtonAction() {
		isRetest = true;
		disableRetestIncorrectButton();
		makeNewVocabListFromIncorrectAnswers();
		view.remakeGUI(retestVocabList);
		enableSubmitAndGiveUpButtons();
		incorrectlyAnsweredVocabList = new ArrayList<Vocab>();
	}
	
	public void disableRetestIncorrectButton() {
		view.getRetestIncorrectButton().setEnabled(false);
	}

	private void makeNewVocabListFromIncorrectAnswers() {
		retestVocabList = new VocabList(incorrectlyAnsweredVocabList);
	}

	private void blankAllTextFields() {
		for(JTextField currentField : view.getFields()) {
			currentField.setText("");
		}
	}
	
	private void resetTextFieldColours() {
		for(JTextField currentField : view.getFields()) {
			currentField.setBorder(view.getDefaultBorder());
		}
	}
	
	private void makeAllTextFieldsEditableAgain() {
		for(JTextField currentField : view.getFields()) {
			currentField.setEditable(true);
		}
	}
	
	private boolean checkAnswer(VocabList vList, int index){
		Vocab vocab = vList.get(index);
		String answer = view.getFields().get(index).getText().trim();
		return answer.equals(vocab.getTranslation(languageTested).trim());
	}

	private void disableSubmitAndGiveUpButtons() {
		view.getSubmit().setEnabled(false);
		view.getCheat().setEnabled(false);
	}
	
	private void enableSubmitAndGiveUpButtons() {
		view.getSubmit().setEnabled(true);
		view.getCheat().setEnabled(true);
	}
	
	private void setCorrect(VocabList vList, int index) {
		vList.setCorrect(index, languageTested);
	}
}
