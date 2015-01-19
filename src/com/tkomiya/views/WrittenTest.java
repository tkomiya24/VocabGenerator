package com.tkomiya.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tkomiya.models.VocabList;

public class WrittenTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VocabList vlist;
	private static final String NEXT_BUTTON = "NEXT";
	private int currentIndex;
	private JLabel label;
	private boolean showingAnswer;
	private int languageTested;
	
	public WrittenTest(VocabList vlist, int languageTested){
		
		this.vlist = vlist;
		this.currentIndex = 0;
		this.label = new JLabel(vlist.get(currentIndex).getTranslation(vlist.get(currentIndex).getPrimaryLanguage()));
		this.showingAnswer = false;
		this.languageTested = languageTested;
		
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		
		//create our buttons
		ActionListener listener = new ButtonListener();
		
		JButton next = new JButton("Next");
		next.setName(NEXT_BUTTON);
		next.addActionListener(listener);
		
		//create a panel to house our buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(next);
		
		//create our main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(label, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton srcButton = (JButton) e.getSource();
			String name = srcButton.getName();
			
			if(name.equals(NEXT_BUTTON)){
				
				if (currentIndex < vlist.size()) {
					if (showingAnswer) {
						showingAnswer = false;
						label.setText(vlist.get(currentIndex).getTranslation(
								vlist.get(currentIndex).getPrimaryLanguage()));
					} else {
						showingAnswer = true;
						label.setText(vlist.get(currentIndex++).getTranslation(
								languageTested));
					}
				}
			}
		}
		
	}
}
