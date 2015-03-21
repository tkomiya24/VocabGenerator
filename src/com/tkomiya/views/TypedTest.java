package com.tkomiya.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;

public class TypedTest extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256239802890038271L;
	private VocabList vList;
	private List<Vocab> incorrectlyAnsweredVocabList;
	private VocabList retestVocabList;
	private int languageTested;
	private static final int TEXTFIELD_SIZE = 10;
	private List<JTextField> fields; 
	private Border defaultBorder;
	private JButton cheat;
	private JButton submit;
	private JButton retestIncorrectButton;
	private JPanel mainPanel;
	private JScrollPane testScrollPane;
	private static final String CHEAT_BUTTON_NAME = "Cheat";
	private static final String SUBMIT_BUTTON_NAME = "Submit";
	private static final String RETEST_INCORRECT_BUTTON_NAME = "Retest Incorrect";
	private boolean isRetest;
	
	public TypedTest(VocabList vList, int languageTested){
		this.vList = vList;
		this.incorrectlyAnsweredVocabList = new ArrayList<Vocab>();
		this.languageTested = languageTested;
		this.isRetest = false;
		makeTestScrollPane(vList);
		defaultBorder = fields.get(0).getBorder();
		
		//Make the buttons
		ActionListener listener = new ButtonListener();
		
		cheat = new JButton(CHEAT_BUTTON_NAME);
		cheat.setName(CHEAT_BUTTON_NAME);
		cheat.addActionListener(listener);
		
		submit = new JButton(SUBMIT_BUTTON_NAME);
		submit.setName(SUBMIT_BUTTON_NAME);
		submit.addActionListener(listener);
		
		JButton restartButton = new JButton("Restart");
		restartButton.setName("restart");
		restartButton.addActionListener(listener);
		
		retestIncorrectButton = new JButton("Restest incorrect");
		retestIncorrectButton.setName(RETEST_INCORRECT_BUTTON_NAME);
		retestIncorrectButton.addActionListener(listener);
		retestIncorrectButton.setEnabled(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cheat);
		buttonPanel.add(submit);
		buttonPanel.add(restartButton);
		buttonPanel.add(retestIncorrectButton);
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(testScrollPane,BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		//make this frame
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	private void makeTestScrollPane(VocabList vList) {
		this.fields = new ArrayList<JTextField>();
		JPanel testPanel = new JPanel(new GridLayout(vList.size(), 2));
		for(int i = 0; i < vList.size(); i++){			
			Vocab vocab = vList.get(i);
			int primaryLang = vocab.getPrimaryLanguage();
			String labelTitle = vocab.getTranslation(primaryLang);
			JLabel label = new JLabel(labelTitle);
			JTextField field = new JTextField(TEXTFIELD_SIZE);
			
			fields.add(field);
			testPanel.add(label);
			testPanel.add(field);
		}
		testScrollPane = new JScrollPane(testPanel);
		testScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {		
			JButton button = (JButton) e.getSource();
			String buttonName = button.getName();			
			if (buttonName.equals(CHEAT_BUTTON_NAME)) {				
				cheatButtonAction();	
			} else if (buttonName.equals(SUBMIT_BUTTON_NAME)) {
				submitButtonAction();
			} else if (buttonName.equals("restart")) {				
				restartButtonAction();
			} else if (buttonName.equals(RETEST_INCORRECT_BUTTON_NAME)) {
				retestIncorrectButtonAction();
			}
		}

		private void cheatButtonAction() {
			for(int i = 0; i < vList.size(); i++){					
				JTextField field = fields.get(i);					
				if(checkAnswer(vList, i)){
					field.setEditable(false);
					field.setBackground(Color.WHITE);
					field.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				}
				else{
					field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				}
			}
		}

		private void submitButtonAction() {
			VocabList vList;
			if (!isRetest) {
				vList = TypedTest.this.vList;
			} else {
				vList = retestVocabList;
			}			
			for(int i = 0; i < vList.size(); i++){	
				JTextField field = fields.get(i);
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
			if(!incorrectlyAnsweredVocabList.isEmpty())
				enableRetestIncorrectButton();
			vList.incrementTimesTested(TypedTest.this.languageTested);
		}

		private void enableRetestIncorrectButton() {
			retestIncorrectButton.setEnabled(true);
		}
		
		private void restartButtonAction() {
			incorrectlyAnsweredVocabList = new ArrayList<Vocab>();
			blankAllTextFields();
			resetTextFieldColours();
			makeAllTextFieldsEditableAgain();
			enableSubmitAndGiveUpButtons();
			disableRetestIncorrectButton();
		}
		
		private void retestIncorrectButtonAction() {
			isRetest = true;
			disableRetestIncorrectButton();
			makeNewVocabListFromIncorrectAnswers();
			remakeGUI();
			enableSubmitAndGiveUpButtons();
			incorrectlyAnsweredVocabList = new ArrayList<Vocab>();
		}
	}
	
	private void disableRetestIncorrectButton() {
		retestIncorrectButton.setEnabled(false);
	}

	private void remakeGUI() {
		mainPanel.remove(testScrollPane);
		remakeTestPane();
		mainPanel.add(testScrollPane);
		mainPanel.updateUI();
	}

	private void remakeTestPane() {
		makeTestScrollPane(retestVocabList);
	}

	private void makeNewVocabListFromIncorrectAnswers() {
		retestVocabList = new VocabList(incorrectlyAnsweredVocabList);
	}

	private void blankAllTextFields() {
		for(JTextField currentField : fields) {
			currentField.setText("");
		}
	}
	
	private void resetTextFieldColours() {
		for(JTextField currentField : fields) {
			currentField.setBorder(defaultBorder);
		}
	}
	
	private void makeAllTextFieldsEditableAgain() {
		for(JTextField currentField : fields) {
			currentField.setEditable(true);
		}
	}
	
	private boolean checkAnswer(VocabList vList, int index){
		Vocab vocab = vList.get(index);
		String answer = fields.get(index).getText();
		return answer.equals(vocab.getTranslation(languageTested));
	}

	private void disableSubmitAndGiveUpButtons() {
		submit.setEnabled(false);
		cheat.setEnabled(false);
	}
	
	private void enableSubmitAndGiveUpButtons() {
		submit.setEnabled(true);
		cheat.setEnabled(true);
	}
	
	private void setCorrect(VocabList vList, int index) {
		vList.setCorrect(index, languageTested);
	}
}
