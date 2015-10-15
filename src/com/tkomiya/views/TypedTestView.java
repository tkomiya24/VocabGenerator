package com.tkomiya.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.views.utils.JComponentFactory;

public class TypedTestView extends JFrame {

	private static final long serialVersionUID = -4764339739449085942L;
	private static final String CHEAT_BUTTON_NAME = "Cheat";
	private static final String SUBMIT_BUTTON_NAME = "Submit";
	private static final String RETEST_INCORRECT_BUTTON_NAME = "Retest Incorrect";
	private static final String RESTART_BUTTON_NAME = "Restart";
	private static final int TEXTFIELD_SIZE = 10;
	private JButton cheat;
	private JButton submit;
	private JButton retestIncorrectButton;
	private JScrollPane testScrollPane;
	private JPanel mainPanel;
	private TypedTestController controller;
	private List<JTextField> fields; 
	private Border defaultBorder;
	
	public TypedTestView(TypedTestController controller, VocabList vocabList) {
		this.controller = controller;
		ActionListener listener = new ButtonListener();
	
		cheat = JComponentFactory.makeButton(CHEAT_BUTTON_NAME, listener);
		submit = JComponentFactory.makeButton(SUBMIT_BUTTON_NAME, listener);
		JButton restartButton = JComponentFactory.makeButton(RESTART_BUTTON_NAME, listener);
		retestIncorrectButton = JComponentFactory.makeButton(RETEST_INCORRECT_BUTTON_NAME, listener);
		retestIncorrectButton.setEnabled(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cheat);
		buttonPanel.add(submit);
		buttonPanel.add(restartButton);
		buttonPanel.add(retestIncorrectButton);
		makeTestScrollPane(vocabList);
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(testScrollPane,BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		defaultBorder = fields.get(0).getBorder();
		//make this frame
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void makeTestScrollPane(VocabList vList) {
		this.fields = new ArrayList<JTextField>();
		vList.shuffle();
		JPanel testPanel = new JPanel(new GridLayout(vList.size(), 2));
		for (int i = 0; i < vList.size(); i++) {			
			Vocab vocab = vList.get(i);
			SupportedLanguage primaryLang = vocab.getPrimaryLanguage();
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
	
	protected void remakeGUI(VocabList retestVocabList) {
		mainPanel.remove(testScrollPane);
		makeTestScrollPane(retestVocabList);
		mainPanel.add(testScrollPane);
		mainPanel.updateUI();
	}
	
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {		
			JButton button = (JButton) e.getSource();
			String buttonName = button.getName();			
			if (buttonName.equals(CHEAT_BUTTON_NAME)) {				
				controller.cheatButtonAction();	
			} else if (buttonName.equals(SUBMIT_BUTTON_NAME)) {
				controller.submitButtonAction();
			} else if (buttonName.equals(RESTART_BUTTON_NAME)) {				
				controller.restartButtonAction();
			} else if (buttonName.equals(RETEST_INCORRECT_BUTTON_NAME)) {
				controller.retestIncorrectButtonAction();
			}
		}
	}

	/**
	 * @return the submit
	 */
	public JButton getSubmit() {
		return submit;
	}

	/**
	 * @return the retestIncorrectButton
	 */
	public JButton getRetestIncorrectButton() {
		return retestIncorrectButton;
	}

	/**
	 * @return the fields
	 */
	public List<JTextField> getFields() {
		return fields;
	}


	/**
	 * @return the cheat
	 */
	public JButton getCheat() {
		return cheat;
	}

	/**
	 * @return the defaultBorder
	 */
	public Border getDefaultBorder() {
		return defaultBorder;
	}
	
}
