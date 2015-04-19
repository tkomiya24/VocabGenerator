package com.tkomiya.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tkomiya.controllers.FileLink;
import com.tkomiya.infrastructure.ComparatorSortedList;
import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.MostMistakenDescendingVocabComparator;
import com.tkomiya.infrastructure.NaturalOrderComparator;
import com.tkomiya.listgetter.ListStringGetter;
import com.tkomiya.listgetter.NewlineSeparatedTextfileStringListGetter;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;
import com.tkomiya.views.TypedTest;
import com.tkomiya.views.WrittenTest;
import com.tkomiya.vocablistproviders.MultipleTextFileVocabListProvider;
import com.tkomiya.vocablistproviders.SerializedFileVocabListProvider;
import com.tkomiya.vocablistproviders.TextFileVocabListProvider;
import com.tkomiya.vocablistproviders.VocabListProvider;

public class MainController {

	private ComparatorSortedList<VocabList> vocabLists;
	private VocabListProvider separateFileVlistGetter;
	private VocabListProvider vlistGetter;
	public static final String DEFAULT_DIRECTORY = "./res/";
	private static final String LISTS_FILE_PATH = "./lists.config";
	private static final String DEFAULT_SAVE_DIRECTORY = DEFAULT_DIRECTORY + "backup/";
	private MainView mainView;
	
	private static final int PRIMARY_LANGUAGE = Vocab.ENGLISH;
	public static final String VOCAB_LIST_FILE_EXTENSION = "voc";
	public static final String TEXT_FILE_EXTENSION = "txt";
	private TextFileVocabListProvider textFileVocabListProvider;
	
	//Button and MeniItem names.
	public static final String LOAD_MENU_ITEM_NAME = "Load a text file";
	public static final String WRITTEN_TEST_MENU_ITEM = "Start a written test";
	public static final String DELETE_MENU_ITEM_NAME = "Delete vocab list";
	public static final String ADD_BUTTON_NAME = "add";
	public static final String COMMON_MISTAKE_TEST_MENU_ITEM_NAME = "Test common mistakes";
	public static final String SAVE_MENU_ITEM_NAME = "Save as text file";
	public static final String BACKUP_ALL_MENU_ITEM_NAME = "Back up all vocablists as text files";
	public static final String LOAD_ALL_MENU_ITEM_NAME = "Load all backup text files";
	public static final String OPEN_MENU_ITEM_NAME = "Open";
	public static final String START_TEST_MENU_ITEM_NAME = "Start a test";
	public static final String DELETE_VOCAB_MENU_ITEM_NAME = "Delete vocab";
	
	public MainController() {
		initializeFields();
		loadVocabLists();
		mainView = new MainView(new MainWindowListener(), new MainMenuItemListener(), new MainButtonListener(), new JListMouseAdapter(), vocabLists);
	}

	private void initializeFields(){
		vocabLists = new ComparatorSortedList<VocabList>(new NaturalOrderComparator());
		separateFileVlistGetter = new MultipleTextFileVocabListProvider();
		vlistGetter = new SerializedFileVocabListProvider();
		textFileVocabListProvider = new TextFileVocabListProvider();
	}
	
	@SuppressWarnings("unchecked")
	private void loadVocabLists() {
		File listsFile = new File(LISTS_FILE_PATH);
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(listsFile)));
			//TODO move the loading and saving logic to another class.
			vocabLists = new ComparatorSortedList<VocabList>((ArrayList<VocabList>) ois.readObject(), new NaturalOrderComparator()) ;
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new MainController();
	}	
	
	private VocabList loadVocabListFromTextFile(File file) throws Exception {
		return textFileVocabListProvider.getVocabListFromFile(file);
	}
	
	@SuppressWarnings("unchecked")
	private List<Vocab> findMostMistakenVocabs(int language) {
		List<Vocab> allVocabsTestedAtLeastOnce = getAllVocabsMistakenAtLeastOnce(language);
		Collections.sort(allVocabsTestedAtLeastOnce, new MostMistakenDescendingVocabComparator(language));
		return allVocabsTestedAtLeastOnce;
	}
	
	private List<Vocab> getAllVocabsMistakenAtLeastOnce(int language) {
		ArrayList<Vocab> vocabs = new ArrayList<Vocab>();
		for (VocabList vocabList : vocabLists) {
			vocabs.addAll(vocabList.getAllTestedVocabsWithOneMistake(language));
		}
		return vocabs;
	}	
	
	private VocabList reconstructVocabFile(File file) throws Exception {				
		return separateFileVlistGetter.getVocabListFromFile(file);
	}

	private void addDialog(){
		//make error dialog if null, then eixt
		if (mainView.getCurrentlySelectedVocabList() == null) {
			showNoVocabListSelectedDialog();
			return;
		}
		//make a new vocab object with a default 'translation'.
		Vocab vocab = new Vocab(PRIMARY_LANGUAGE);
		vocab.addLanguage(PRIMARY_LANGUAGE, "New word");
		//add it to the vocablist.
		mainView.getCurrentlySelectedVocabList().addVocab(vocab);
		//update the table.
		mainView.refreshVocabTable();
	}	
	 
	private void showNoVocabListSelectedDialog() {
		mainView.showErrorDialog("No vocab list selected", "Please choose a vocab list.");
	}
	
	private void saveVocabListAsTextFile(VocabList vocabList, File file) {
		try {
			textFileVocabListProvider.saveVocabList(vocabList, file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public class MainWindowListener implements WindowListener {
		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowClosing(WindowEvent e) {	
			saveVocabLists();
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub		
		}
		
		private void saveVocabLists() {
			try {
				File file = new File(LISTS_FILE_PATH);
				if (file.exists() && !file.isDirectory()) {
					file.delete();
				}
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				oos.writeObject(vocabLists.getList()); //TODO make a class that handles the logic of saving a ComparatorSortedList.
				oos.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				mainView.dispose();
			}	
		}
	}
	
	public class MainMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source instanceof JMenuItem) {
				JMenuItem sourceItem = (JMenuItem) e.getSource();
				handleMenuAction(sourceItem);
			} 
		}
		
		private void handleMenuAction(JMenuItem sourceItem) {
			String sourceName = sourceItem.getName();
			VocabList vocabList = mainView.getCurrentlySelectedVocabList();
			if (sourceName == "Open") {
				JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
				int val = fileChooser.showOpenDialog(mainView);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					VocabList newVocabList;
					try {
						String extension = FileUtilities.getFileExtension(file);
						if (extension.equals(VOCAB_LIST_FILE_EXTENSION)) {
							newVocabList = vlistGetter.getVocabListFromFile(file);
						} else {
							newVocabList = reconstructVocabFile(file);	
						}
						addNewVocabList(newVocabList);
					} catch (Exception e1) {
						try {
							newVocabList = reconstructVocabFile(file);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			} else if (sourceName.equals(START_TEST_MENU_ITEM_NAME)) {		
				new TypedTest(mainView.getCurrentlySelectedVocabList(), Vocab.KOREAN);
			} else if (sourceName == WRITTEN_TEST_MENU_ITEM) {			
				new WrittenTest(mainView.getCurrentlySelectedVocabList(), Vocab.KOREAN);
			} else if (sourceName.equals(COMMON_MISTAKE_TEST_MENU_ITEM_NAME)) {
				commonMistakeTest();
			} else if (sourceName.equals(SAVE_MENU_ITEM_NAME)) {
				if (vocabList == null) {
					mainView.showErrorDialog("There is no vocab list selected", "Please select a vocab list to save.");
					return;
				}
				JFileChooser fileChooser = new JFileChooser(DEFAULT_SAVE_DIRECTORY);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", TEXT_FILE_EXTENSION));
				File suggestedSaveFile = new File(DEFAULT_SAVE_DIRECTORY + vocabList.getName() + "." + TEXT_FILE_EXTENSION);
				fileChooser.setSelectedFile(suggestedSaveFile);
				int val = fileChooser.showSaveDialog(mainView);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					saveVocabListAsTextFile(vocabList, file);
				}
			} else if (sourceName.equals(LOAD_MENU_ITEM_NAME)) {
				JFileChooser fileChooser = new JFileChooser(DEFAULT_SAVE_DIRECTORY);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", TEXT_FILE_EXTENSION));
				int val = fileChooser.showOpenDialog(mainView);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						VocabList newVocabList = loadVocabListFromTextFile(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (sourceName.equals(BACKUP_ALL_MENU_ITEM_NAME)) {
				int response = mainView.showConfirmationDialog("Back up vocab files", "This will overwrite all previous backup files. Are you sure?");
				if (response == JOptionPane.YES_OPTION) {
					for (VocabList vlist : vocabLists) {
						File file = new File(DEFAULT_SAVE_DIRECTORY + vlist.getName() + "." + TEXT_FILE_EXTENSION);
						saveVocabListAsTextFile(vlist, file);
					}
				}
			} else if (sourceName.equals(LOAD_ALL_MENU_ITEM_NAME)) {
				int response = mainView.showConfirmationDialog("Load all files from backup", "This will overwrite all currently loaded vocab lists. Are you sure?");
				if (response == JOptionPane.YES_OPTION) {
					vocabLists.clear();
					File backUpDirectory = new File(DEFAULT_SAVE_DIRECTORY);
					File[] allFiles = backUpDirectory.listFiles();
					for (File file : allFiles) {
						if (file.isFile() && !file.isDirectory() && FileUtilities.getFileExtension(file).equals(TEXT_FILE_EXTENSION)) {
							try {
								vocabList = loadVocabListFromTextFile(file);
								vocabLists.add(vocabList);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					mainView.refreshShortcutPanel();
				} 
			} else if (sourceName.equals(DELETE_MENU_ITEM_NAME)) {
				JList<VocabList> links = mainView.getLinks();
				int index = links.getSelectedIndex();
				vocabLists.remove(index);
				mainView.removeLink(index);
			} else if (sourceName.equals(DELETE_VOCAB_MENU_ITEM_NAME)) {
				Vocab vocab = mainView.getCurrentlySelectedVocab();
				VocabList selectedVocabList = mainView.getCurrentlySelectedVocabList();
				selectedVocabList.removeVocab(vocab);
				mainView.updateVocabListTable();
			}
		}

		private void commonMistakeTest() {
			boolean badInput = true;
			do {
				String response = mainView.showInputDialog("Length", "Please enter the maximum desired test length");
				if (response == null) {
					return;
				} else {
					try {
						int testLength = Integer.parseInt(response);
						badInput = false;
						List<Vocab> mostMistakenVocabs = findMostMistakenVocabs(Vocab.KOREAN);
						mostMistakenVocabs = mostMistakenVocabs.subList(0, testLength);
						if (mostMistakenVocabs.size() > 0) {
							VocabList mostMistakenVocabsVocabList = new VocabList(mostMistakenVocabs);
							new TypedTest(mostMistakenVocabsVocabList, Vocab.KOREAN);
						} else {
							//TODO message dialog.
						}
					} catch(NumberFormatException e) {
						mainView.showErrorDialog("Error with input", "Please input a number");
					}
				}
			} while(badInput);
		}
		
	}
	
	private void addNewVocabList(VocabList vocabList) {
		vocabLists.add(vocabList);
		//add to shortcut pane
		mainView.addToShortcutPane(vocabList, vocabLists.indexOf(vocabList));
		//make the currently selected
		mainView.setCurrentlySelectedVocabList(vocabList);
		//make it fill the table
		mainView.updateVocabListTable();
	}
	
	public class MainButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			String buttonName = source.getName();
			if (buttonName.equals(ADD_BUTTON_NAME)) {			
				addDialog();
			}
		}
	}
	
	public class JListMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JList) {
				handleMouseClickForJList(e);
			} else if (e.getSource() instanceof JTable) {
				handleMouseClickForJTable(e);
			}
		}		

		private void handleMouseClickForJList(MouseEvent e) {
			if (e.getClickCount() == 2) {			
				@SuppressWarnings("unchecked")
				JList<VocabList> list = (JList<VocabList>) e.getSource();
				VocabList vocabList = list.getSelectedValue();
				mainView.setCurrentlySelectedVocabList(vocabList);
				mainView.updateVocabListTable();
			} else if(SwingUtilities.isRightMouseButton(e)) {
				JList<VocabList> links = mainView.getLinks();
				int row = links.locationToIndex(e.getPoint());
				links.setSelectedIndex(row);
				mainView.showShortcutPopup(e.getX(), e.getY());
			}
		}
		
		private void handleMouseClickForJTable(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				mainView.showVocabTableShortcut(e);
			}
		}
	}
}
