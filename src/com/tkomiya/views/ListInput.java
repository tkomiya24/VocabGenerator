package com.tkomiya.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;
import com.tkomiya.vocablistsaver.SerializedFileVocabListSaver;
import com.tkomiya.vocablistsaver.VocabListSaver;

public class ListInput extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 743291150033167537L;
	private JTextArea textArea;
	private static final String SUBMIT_BUTTON_NAME = "submit";
	private static final String DONE_BUTTON_NAME = "done";
	private static final String OPEN_BUTTON_NAME = "one";
	private JComboBox<String> comboBox;
	private VocabListBuilder vlb;
	
	public ListInput(){
		
		//assignments
		vlb = new VocabListBuilder();
		comboBox = new JComboBox<String>(Vocab.SUPPORTED_LANGUAGES);
		
		//make a text area.
		textArea = new JTextArea();
		textArea.setRows(20);
		
		//make the buttons
		ActionListener buttonListener = new ButtonListener();
		
		JButton submit =  new JButton("Submit");
		submit.setName(SUBMIT_BUTTON_NAME);
		submit.addActionListener(buttonListener);

		JButton done = new JButton("Done");
		done.setName(DONE_BUTTON_NAME);
		done.addActionListener(buttonListener);
		
		JButton open = new JButton("Open...");
		open.setName(OPEN_BUTTON_NAME);
		open.addActionListener(buttonListener);
		
		
		//put GUI together
		JScrollPane sp = new JScrollPane(textArea);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(done);
		buttonPanel.add(open);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(sp,BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(comboBox, BorderLayout.NORTH);
		
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private List<String> makeList(){
		
		String list = textArea.getText();
		Scanner scan = new Scanner(list);
		List<String> vocab = new ArrayList<String>();
		while(scan.hasNext()){
			vocab.add(scan.nextLine());
		}
		scan.close();
		return vocab;
	}
	
	private class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String buttonName = ((JButton) e.getSource()).getName();
			
			if (buttonName.equals(ListInput.SUBMIT_BUTTON_NAME)) {
				List<String> list = makeList();
				
				if (vlb.getLanguagesSet() <= 0) {
					vlb.setPrimaryLanguage(comboBox.getSelectedIndex(), list);
				} 
				else {
					vlb.addLanguage(comboBox.getSelectedIndex(), list);
					if (vlb.getLanguagesSet() >= Vocab.VOCAB_SUPPORT_SIZE) {
						
						this.done();		
					}
				}
				textArea.setText(null);
			}
			else if(buttonName.equals(ListInput.DONE_BUTTON_NAME)){
				
				this.done();
			}
			else if(buttonName.equals(ListInput.OPEN_BUTTON_NAME)){
				
				JFileChooser jfc = new JFileChooser("./res/");
				jfc.setFileFilter(new FileNameExtensionFilter("Text files", ".txt"));
				int val = jfc.showOpenDialog(ListInput.this);
				
				if(val == JFileChooser.APPROVE_OPTION){
					
					File file = jfc.getSelectedFile();
					try {
						Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
						while(scanner.hasNext()){
							textArea.append(scanner.nextLine());
							textArea.append("\n");
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
		private void submit(){
			
		}
		
		private void done(){
			JFileChooser jfc = new JFileChooser("./res/");
			int val = jfc.showSaveDialog(ListInput.this);

			if (val == JFileChooser.APPROVE_OPTION) {

				try {
					VocabList vl = vlb.build();
					VocabListSaver vls = new SerializedFileVocabListSaver();
					vls.saveVocabList(vl, jfc.getSelectedFile());
					ListInput.this.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				ListInput.this.dispose();
			}
		}
	}
	
}
