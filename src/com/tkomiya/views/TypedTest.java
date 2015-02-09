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
	private int languageTested;
	private static final int TEXTFIELD_SIZE = 10;
	private List<JTextField> fields; 
	private Border defaultBorder;
	
	public TypedTest(VocabList vList, int languageTested){
		
		this.vList = vList;
		this.languageTested = languageTested;
		this.fields = new ArrayList<JTextField>();
		
		//make a panel that all the labels and field will go in
		JPanel testPanel = new JPanel(new GridLayout(vList.size(), 2));

		//make a label and a text field for each item in vocablist
		this.vList.shuffle();
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
		
		//get the default border
		defaultBorder = fields.get(0).getBorder();
		
		//Make the buttons
		ActionListener listener = new ButtonListener();
		
		JButton submit = new JButton("Submit");
		submit.setName("submit");
		submit.addActionListener(listener);
		
		JButton giveUp = new JButton("Give up");
		giveUp.setName("giveup");
		giveUp.addActionListener(listener);
		
		JButton restartButton = new JButton("Restart");
		restartButton.setName("restart");
		restartButton.addActionListener(listener);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(giveUp);
		buttonPanel.add(restartButton);
		
		//make a scrollpane
		JScrollPane scrollPane = new JScrollPane(testPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(scrollPane,BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		//make this frame
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton button = (JButton) e.getSource();
			String bName = button.getName();
			
			if (bName.equals("submit")) {
				
				for(int i = 0; i < vList.size(); i++){
					
					JTextField field = fields.get(i);
					
					if(checkAnswer(i)){
						field.setEditable(false);
						field.setBackground(Color.WHITE);
						field.setBorder(BorderFactory.createLineBorder(Color.GREEN));
					}
					else{
						field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					}
				}
				
			} else if (bName.equals("giveup")) {
				
				for(int i = 0; i < vList.size(); i++){
					
					JTextField field = fields.get(i);
					field.setEditable(false);
					if(checkAnswer(i)){
						field.setBorder(BorderFactory.createLineBorder(Color.GREEN));
					}
					else{
						field.setText(vList.get(i).getTranslation(languageTested));
						field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					}
				}
			} else if (bName.equals("restart")) {				
				restartTest();
			}
		}
		
	}
	
	private void restartTest() {
		blankAllTextFields();
		resetTextFieldColours();
		makeAllTextFieldsEditableAgain();
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
	
	private boolean checkAnswer(int index){
		
		Vocab vocab = vList.get(index);
		String answer = fields.get(index).getText();
		return answer.equals(vocab.getTranslation(languageTested));
	}
	
}
