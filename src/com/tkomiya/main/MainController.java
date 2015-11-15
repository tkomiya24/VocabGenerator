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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.JSONException;

import com.tkomiya.exceptions.ListLengthsDoNotMatchException;
import com.tkomiya.infrastructure.ComparatorSortedList;
import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.NaturalOrderComparator;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.utils.VocabListMongoExporter;
import com.tkomiya.models.utils.VocabListUtils;
import com.tkomiya.views.TypedTestController;
import com.tkomiya.vocablistproviders.JsonFileVocabListProvider;
import com.tkomiya.vocablistproviders.SerializedFileVocabListProvider;
import com.tkomiya.vocablistproviders.VocabListProvider;

public class MainController {

	private ComparatorSortedList<VocabList> vocabLists;
	private VocabListProvider separateFileVlistGetter;
	private VocabListProvider vlistGetter;
	private VocabListProvider backupFileVlistProvider;
	private VocabListMongoExporter exporter;
	
	public static final String DEFAULT_DIRECTORY = "./res/";
	private static final String LISTS_FILE_PATH = "./lists.config";
	private static final String DEFAULT_SAVE_DIRECTORY = DEFAULT_DIRECTORY + "backup/";
	private MainView mainView;
	
	private static final SupportedLanguage PRIMARY_LANGUAGE = SupportedLanguage.ENGLISH;
	public static final String VOCAB_LIST_FILE_EXTENSION = "voc";
	public static final String TEXT_FILE_EXTENSION = "txt";
	public static final String BACKUP_FILE_EXTENSION = "json";

	//Button and MeniItem names.
	public static final String ADD_BUTTON_NAME = "add";
	public static final String SEARCH_FIELD_NAME = "Search";
	
	public MainController() {
		initializeFields();
		loadVocabLists();
		mainView = new MainView(this, new MainWindowListener(), new MainButtonListener(), new JListMouseAdapter(), vocabLists);
	}

	private void initializeFields(){
		vocabLists = new ComparatorSortedList<VocabList>(new NaturalOrderComparator());
		vlistGetter = new SerializedFileVocabListProvider();
		exporter = new VocabListMongoExporter();
		backupFileVlistProvider = new JsonFileVocabListProvider();
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
		return backupFileVlistProvider.getVocabListFromFile(file);
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
		Translation trans = new Translation();
		trans.addTranslation("New word");
		vocab.addTranslation(PRIMARY_LANGUAGE, trans);
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
			backupFileVlistProvider.saveVocabList(vocabList, file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void commonMistakeTest(SupportedLanguage testingLanguage, int length) {
		List<Vocab> mostMistakenVocabs = VocabListUtils.findMostMistakenVocabs(vocabLists, testingLanguage);
		mostMistakenVocabs = mostMistakenVocabs.subList(0, length);
		if (mostMistakenVocabs.size() > 0) {
			VocabList mostMistakenVocabsVocabList = new VocabList(mostMistakenVocabs);
			new TypedTestController(mostMistakenVocabsVocabList, testingLanguage);
		} else {
			//TODO message dialog.
		}
	}

	public void openMenuItemAction() {
		JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
		int val = fileChooser.showOpenDialog(mainView);
		if (val == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			boolean vocabListExists = checkIfVocabListExists(file);
			if (vocabListExists) {
				boolean proceed = checkIfShouldOpenAnyways();
				if (!proceed) {
					return;
				}
			}
			VocabList newVocabList;
			try {
				String extension = FileUtilities.getFileExtension(file);
				if (extension.equals(VOCAB_LIST_FILE_EXTENSION)) {
					newVocabList = vlistGetter.getVocabListFromFile(file);
				} else {
					newVocabList = reconstructVocabFile(file);	
				}
				addNewVocabList(newVocabList);
			} catch (ListLengthsDoNotMatchException e) {
				mainView.showErrorDialog("Error", "The length of the lists do not match");
			} catch (Exception e1) {
				try {
					newVocabList = reconstructVocabFile(file);
				} catch (ListLengthsDoNotMatchException e) {
					mainView.showErrorDialog("Error", "The length of the lists do not match");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public boolean checkIfVocabListExists(File file) {
		String vocabListName = FileUtilities.getFileNameWithNoExtension(file);
		List<VocabList> vocabLists = MainController.this.vocabLists.getList();
		for (VocabList vocabList : vocabLists) {
			if (vocabList.getName().equals(vocabListName)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkIfShouldOpenAnyways() {
		int response = mainView.showConfirmationDialog("Name conflict", "A vocablist with this name already exists. Would you like to proceed anyways? (Neither will be overwritten)");
		return response == JOptionPane.YES_OPTION;
	}
	
	public void startTestMenuItemAction(SupportedLanguage language) {
		if (mainView.getCurrentlySelectedVocabList() == null) {
			reportNoVocabListSelectedError();
		} 
		new TypedTestController(mainView.getCurrentlySelectedVocabList(), language);

	}
	
	private void reportNoVocabListSelectedError() {
		mainView.showErrorDialog("No vocablist selected", "Please select a vocablist first.");
	}

	public void loadAllMenuItemAction() {
		int response = mainView.showConfirmationDialog("Load all files from backup", "This will overwrite all currently loaded vocab lists. Are you sure?");
		if (response == JOptionPane.YES_OPTION) {
			vocabLists.clear();
			File backUpDirectory = new File(DEFAULT_SAVE_DIRECTORY);
			File[] allFiles = backUpDirectory.listFiles();
			for (File file : allFiles) {
				if (file.isFile() && !file.isDirectory() && FileUtilities.getFileExtension(file).equals(BACKUP_FILE_EXTENSION)) {
					try {
						VocabList vocabList = loadVocabListFromTextFile(file);
						vocabLists.add(vocabList);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			mainView.refreshShortcutPanel();
		}
	}

	public void backupAllMenuItemAction() {
		int response = mainView.showConfirmationDialog("Back up vocab files", "This will overwrite all previous backup files. Are you sure?");
		if (response == JOptionPane.YES_OPTION) {
			for (VocabList vlist : vocabLists) {
				File file = new File(DEFAULT_SAVE_DIRECTORY + vlist.getName() + "." + BACKUP_FILE_EXTENSION);
				saveVocabListAsTextFile(vlist, file);
			}
		}
	}
	
	public void deleteMenuItemAction() {
		JList<VocabList> links = mainView.getLinks();
		int index = links.getSelectedIndex();
		vocabLists.remove(index);
		mainView.removeLink(index);
	}

	public void deleteVocabMenuItemAction(int row) {
		VocabList selectedVocabList = mainView.getCurrentlySelectedVocabList();
		selectedVocabList.remove(row);
		mainView.updateVocabListTable();
	}

	public void leastTestedTest(SupportedLanguage language) {
		VocabList testingList = VocabListUtils.getLeastTestedVocabList(vocabLists, language);
		new TypedTestController(testingList, language);
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

	public void exportMongo() {
		File file = new File(DEFAULT_SAVE_DIRECTORY +  "master.json");
		try {
			VocabListMongoExporter.saveVocabListsAsMongoDbJson(vocabLists, file);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MainButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JButton) {
				JButton source = (JButton) e.getSource();
				String buttonName = source.getName();
				if (buttonName.equals(ADD_BUTTON_NAME)) {			
					addDialog();
				}
			} else if (e.getSource() instanceof JTextField) {
				JTextField source = (JTextField) e.getSource();
				String sourceName = source.getName();
				if (sourceName.equals(SEARCH_FIELD_NAME)) {
					search(source.getText());
				}
			}
		}
		
		private void search(String searchTerm) {
			List<Vocab> lists = new ArrayList<Vocab>();
			for (VocabList vocablist : vocabLists) {
				lists.addAll(vocablist.search(searchTerm));
			}
			VocabList newVocabList = new VocabList(lists);
			mainView.setCurrentlySelectedVocabList(newVocabList);
			mainView.updateVocabListTable();
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
