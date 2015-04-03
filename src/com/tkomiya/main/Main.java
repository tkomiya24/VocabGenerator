package com.tkomiya.main;

import java.awt.BorderLayout;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tkomiya.controllers.FileLink;
import com.tkomiya.infrastructure.ComparatorSortedList;
import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.MostMistakenDescendingVocabComparator;
import com.tkomiya.infrastructure.NaturalOrderComparator;
import com.tkomiya.infrastructure.VocabListNameComparator;
import com.tkomiya.infrastructure.VocabListTableModel;
import com.tkomiya.listgetter.ListStringGetter;
import com.tkomiya.listgetter.NewlineSeparatedTextfileStringListGetter;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;
import com.tkomiya.views.TypedTest;
import com.tkomiya.views.WrittenTest;
import com.tkomiya.vocablistgetter.SerializedFileVocabListGetter;
import com.tkomiya.vocablistgetter.TextFileVocabListGetter;
import com.tkomiya.vocablistgetter.VocabListGetter;
import com.tkomiya.vocablistsaver.SerializedFileVocabListSaver;
import com.tkomiya.vocablistsaver.TextFileVocabListSaver;
import com.tkomiya.vocablistsaver.VocabListSaver;

public class Main extends JFrame {

	private static final long serialVersionUID = 2825416147024612814L;
	private JTextArea textArea;
	private int currentChunk;
	private int chunkSize = 10;
	private VocabList vocabList;
	private ComparatorSortedList<VocabList> vocabLists;
	private ListStringGetter vocabGetter;
	private VocabListGetter vlistGetter;
	public static final String DEFAULT_DIRECTORY = "./res/";
	private static final String LISTS_FILE_PATH = "./lists.config";
	private static final String DEFAULT_SAVE_DIRECTORY = DEFAULT_DIRECTORY + "backup/";
	private boolean showAnswers = false;
	private DefaultListModel<VocabList> vocabListListModel;
	private VocabListTableModel vltm;

	private static final int PRIMARY_LANGUAGE = Vocab.ENGLISH;
	public static final String VOCAB_LIST_FILE_EXTENSION = "voc";
	public static final String TEXT_FILE_EXTENSION = "txt";

	private JPopupMenu shortcutListPopup;
	private FileLink currentLink;
	private TextFileVocabListSaver textFileVocabListSaver;
	private TextFileVocabListGetter textFileVocabListGetter;
	
	//Button and MeniItem names.
	private static final String LOAD_MENU_ITEM_NAME = "load text";
	private static final String WRITTEN_TEST_MENU_ITEM = "written";
	private static final String MAKE_LINK_MENU_ITEM_NAME = "link";
	private static final String DELETE_MENU_ITEM_NAME = "delete";
	private static final String ADD_BUTTON_NAME = "add";
	private static final String COMMON_MISTAKE_TEST_MENU_ITEM_NAME = "mistake test";
	private static final String SAVE_MENU_ITEM_NAME = "save text";
	private static final String BACKUP_ALL_MENU_ITEM_NAME = "backup all";
	private static final String LOAD_ALL_MENU_ITEM_NAME = "load all";
	
	public Main() {
		initializeFields();
		loadVocabLists();
		makeTextArea();
		makeFrame();
	}

	private void initializeFields(){
		vocabLists = new ComparatorSortedList<VocabList>(new VocabListNameComparator());
		vocabGetter = new NewlineSeparatedTextfileStringListGetter();
		vlistGetter = new SerializedFileVocabListGetter();
		textFileVocabListSaver = new TextFileVocabListSaver();
		textFileVocabListGetter = new TextFileVocabListGetter();
	}
	
	@SuppressWarnings("unchecked")
	private void loadVocabLists() {
		File listsFile = new File(LISTS_FILE_PATH);
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(listsFile)));
			//TODO move the loading and saving logic to another class.
			vocabLists = new ComparatorSortedList<VocabList>((ArrayList<VocabList>) ois.readObject(), new NaturalOrderComparator()) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeTextArea(){
		textArea = new JTextArea();
		textArea.setEditable(false);
	}
	
	private JScrollPane makeVocabTablePane(){
		JScrollPane scrollPane = new JScrollPane(makeVocabTable());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		scrollPane.setSize(100, 400);
		return scrollPane;
	}
	
	private JTable makeVocabTable(){
		vltm = new VocabListTableModel();
		JTable vocabTable = new JTable(vltm);
		vocabTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return vocabTable;
	}
	
	private JPanel makeButtonPanel(){		
		JPanel buttonPanel = new JPanel();
		Collection<JButton> buttons = makeButtons();
		for(JButton button : buttons){
			buttonPanel.add(button);
		}
		return buttonPanel;
	}
	
	private Collection<JButton> makeButtons(){
		ActionListener listener = new Main.ButtonListener();
		Collection<JButton> buttons = new ArrayList<JButton>();
		
		JButton next = new JButton("Next");
		next.setName("Next");
		next.addActionListener(listener);
		buttons.add(next);
		
		JButton previous = new JButton("Back");
		previous.setName("Previous");
		previous.addActionListener(listener);
		buttons.add(next);
		
		JButton showAnswers = new JButton("Show answers");
		showAnswers.setName("show");
		showAnswers.addActionListener(listener);
		buttons.add(next);
		
		JButton add = new JButton("+");
		add.setName(ADD_BUTTON_NAME);
		add.addActionListener(listener);
		buttons.add(next);
		
		return buttons;
	}
	
	private Collection<JMenuItem> makeMenuItems(){		
		ActionListener menuListener = new MenuItemListener();
		Collection<JMenuItem> menuItems = new ArrayList<JMenuItem>();
		
		JMenuItem open = new JMenuItem("Open");
		open.setName("Open");
		open.addActionListener(menuListener);
		menuItems.add(open);

		JMenuItem startTest = new JMenuItem("Start a test");
		startTest.setName("test");
		startTest.addActionListener(menuListener);
		menuItems.add(startTest);
		
		JMenuItem written = new JMenuItem("Start a written test");
		written.setName(WRITTEN_TEST_MENU_ITEM);
		written.addActionListener(menuListener);
		menuItems.add(written);
		
		JMenuItem makeLink = new JMenuItem("Add a shortcut");
		makeLink.setName(MAKE_LINK_MENU_ITEM_NAME );
		makeLink.addActionListener(menuListener);
		menuItems.add(makeLink);
		
		JMenuItem commonMistakeTest = new JMenuItem("Retest common mistakes");
		commonMistakeTest.setName(COMMON_MISTAKE_TEST_MENU_ITEM_NAME);
		commonMistakeTest.addActionListener(menuListener);
		menuItems.add(commonMistakeTest);
		
		JMenuItem save = new JMenuItem("Save as text file");
		save.setName(SAVE_MENU_ITEM_NAME);
		save.addActionListener(menuListener);
		menuItems.add(save);
		
		JMenuItem load = new JMenuItem("Load a text file");
		load.setName(LOAD_MENU_ITEM_NAME);
		load.addActionListener(menuListener);
		menuItems.add(load);
		
		JMenuItem backupAll = new JMenuItem("Back up all");
		backupAll.setName(BACKUP_ALL_MENU_ITEM_NAME);
		backupAll.addActionListener(menuListener);
		menuItems.add(backupAll);
		
		JMenuItem loadAll = new JMenuItem("Load all");
		loadAll.setName(LOAD_ALL_MENU_ITEM_NAME);
		loadAll.addActionListener(menuListener);
		menuItems.add(loadAll);
		
		return menuItems;
	}
	
	private JMenu makeMenu(){
		JMenu fileMenu = new JMenu("File");
		Collection<JMenuItem> menuItems = makeMenuItems();
		for(JMenuItem menuItem : menuItems){
			fileMenu.add(menuItem);
		}
		return fileMenu;
	}
	
	private JMenuBar makeMenuBar(){
		JMenuBar mb = new JMenuBar();
		mb.add(makeMenu());
		return mb;
	}
	
	private JPanel makeShortcutPanel(){
		JPanel sidePanel = new JPanel();
		sidePanel.add(makeShortcutPanelList());
		fillShortcutPanel();
		return sidePanel;
	}
	
	private void fillShortcutPanel() {
		for (int i = 0; i < vocabLists.size(); i++) {
			vocabListListModel.addElement(vocabLists.get(i));
		}
	}
	
	private JList<VocabList> makeShortcutPanelList(){

		vocabListListModel = new DefaultListModel<VocabList>();
		JList<VocabList> links = new JList<VocabList>(vocabListListModel);
		links.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) {					
					@SuppressWarnings("unchecked")
					JList<VocabList> list = (JList<VocabList>) e.getSource();
					vocabList = list.getSelectedValue();
					fillTextArea();
				}
				else if(SwingUtilities.isRightMouseButton(e)){
					int row = links.locationToIndex(e.getPoint());
					links.setSelectedIndex(row);
					shortcutListPopup.show(links, e.getX(), e.getY());
				}
			}
		});
		makeShorcutListPopup(links);
		return links;
	}
	
	private void makeShorcutListPopup(JList<VocabList> links){
		shortcutListPopup = new JPopupMenu();
		JMenuItem deleteMenuItem = new JMenuItem("Delete shorcut");
		deleteMenuItem.setName(DELETE_MENU_ITEM_NAME);
		deleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JMenuItem menuItem = (JMenuItem) e.getSource();
				String sourceName = menuItem.getName();
				if (sourceName.equals(DELETE_MENU_ITEM_NAME)) {
					VocabList list = links.getSelectedValue();
					int index = links.getSelectedIndex();
					vocabLists.remove(list.getName());
					links.remove(index);
				}
			}
		});
		shortcutListPopup.add(deleteMenuItem);
	}
	
	private void addMainPanel(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(makeVocabTablePane(), BorderLayout.CENTER);
		mainPanel.add(makeButtonPanel(), BorderLayout.SOUTH);
		mainPanel.add(makeShortcutPanel(), BorderLayout.WEST);
		add(mainPanel);
	}
	
	private void makeFrame(){
		addWindowListener(new AppWindowListener());
		addMainPanel();
		setJMenuBar(makeMenuBar());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String args[]) {
		new Main();
	}

	private class MenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem sourceItem = (JMenuItem) e.getSource();
			String sourceName = sourceItem.getName();
			if (sourceName == "Open") {
				JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
				int val = fileChooser.showOpenDialog(Main.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						String extension = FileUtilities.getFileExtension(file);
						if (extension.equals(VOCAB_LIST_FILE_EXTENSION)) {
							vocabList = vlistGetter.getVocabListFromFile(file);
							loadInVocabList();
							currentChunk = 0;
							vocabList.shuffle();
							fillTextArea();
							vocabListListModel.addElement(vocabList);
						}
						else {
							vocabList = reconstructVocabFile(file);
							loadInVocabList();
						}
					} catch (Exception e1) {
						try {
							vocabList = reconstructVocabFile(file);
							loadInVocabList();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						e1.printStackTrace();
					}
				}
			}
			else if (sourceName == "test") {		
				new TypedTest(Main.this.vocabList, Vocab.KOREAN);
			}
			else if (sourceName == WRITTEN_TEST_MENU_ITEM) {			
				new WrittenTest(Main.this.vocabList, Vocab.KOREAN);
			}
			else if (sourceName.equals(COMMON_MISTAKE_TEST_MENU_ITEM_NAME)) {
				List<Vocab> mostMistakenVocabs = findMostMistakenVocabs(Vocab.KOREAN);
				if (mostMistakenVocabs.size() > 0) {
					VocabList mostMistakenVocabsVocabList = new VocabList(mostMistakenVocabs);
					new TypedTest(mostMistakenVocabsVocabList, Vocab.KOREAN);
				} else {
					//TODO message dialog.
				}
			}
			else if (sourceName.equals(SAVE_MENU_ITEM_NAME)) {
				if (vocabList == null) {
					showErrorDialog("There is no vocab list selected", "Please select a vocab list to save.");
					return;
				}
				JFileChooser fileChooser = new JFileChooser(DEFAULT_SAVE_DIRECTORY);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", TEXT_FILE_EXTENSION));
				File suggestedSaveFile = new File(DEFAULT_SAVE_DIRECTORY + vocabList.getName() + "." + TEXT_FILE_EXTENSION);
				fileChooser.setSelectedFile(suggestedSaveFile);
				int val = fileChooser.showSaveDialog(Main.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					saveVocabListAsTextFile(vocabList, file);
				}
			} else if (sourceName.equals(LOAD_MENU_ITEM_NAME)) {
				JFileChooser fileChooser = new JFileChooser(DEFAULT_SAVE_DIRECTORY);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", TEXT_FILE_EXTENSION));
				int val = fileChooser.showOpenDialog(Main.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					loadVocabListFromTextFile(file);
				}
			} else if (sourceName.equals(BACKUP_ALL_MENU_ITEM_NAME)) {
				int response = showConfirmationDialog("Back up vocab files", "This will overwrite all previous backup files. Are you sure?");
				if (response == JOptionPane.YES_OPTION) {
					for (VocabList vocabList : vocabLists) {
						File file = new File(DEFAULT_SAVE_DIRECTORY + vocabList.getName() + "." + TEXT_FILE_EXTENSION);
						saveVocabListAsTextFile(vocabList, file);
					}
				}
			} else if (sourceName.equals(LOAD_ALL_MENU_ITEM_NAME)) {
				int response = showConfirmationDialog("Load all files from backup", "This will overwrite all currently loaded vocab lists. Are you sure?");
				if (response == JOptionPane.YES_OPTION) {
					File backUpDirectory = new File(DEFAULT_SAVE_DIRECTORY);
					File[] allFiles = backUpDirectory.listFiles();
					for (File file : allFiles) {
						if (file.isFile() && !file.isDirectory() && FileUtilities.getFileExtension(file).equals(TEXT_FILE_EXTENSION)) {
							loadVocabListFromTextFile(file);
						}
					}
				} 
			}
		}
	}
	
	private void loadVocabListFromTextFile(File file) {
		try {
			vocabList = textFileVocabListGetter.getVocabListFromFile(file);
			loadInVocabList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private int showConfirmationDialog(String title, String message) {
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	private void loadInVocabList() {
		vocabLists.add(vocabList);
		vocabListListModel.insertElementAt(vocabList, vocabLists.indexOf(vocabList));
		fillTextArea();
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
 	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			String buttonName = source.getName();
			if (buttonName.equals("Next")) {
				if (vocabList != null
						&& (currentChunk + 1) * chunkSize < vocabList.size()) {
					currentChunk++;
					fillTextArea();
				}
			} else if (buttonName.equals("Previous")) {
				if (vocabList != null && currentChunk > 0) {
					currentChunk--;
					fillTextArea();
				}
			} else if (buttonName.equals("show")) {
				if (vocabList != null) {
					showAnswers = !showAnswers;
					fillTextArea();
				}
			} else if (buttonName.equals(ADD_BUTTON_NAME)) {			
				addDialog();
			}
		}
	}

	private void fillTextArea() {
		this.vltm.setVocabList(vocabList);
	}
	
	private VocabList reconstructVocabFile(File file) throws Exception{				
		String fileName = FileUtilities.getFileNameWithNoExtension(file);
		String filePath = FileUtilities.getFilePathWithoutFileName(file);
		List<String> englishList = getEnglishList(fileName, filePath);
		List<String> koreanList = getKoreanList(fileName, filePath);
		VocabListBuilder vlb = new VocabListBuilder();
		vlb.setPrimaryLanguage(Vocab.ENGLISH, englishList);
		vlb.addLanguage(Vocab.KOREAN, koreanList);
		vlb.setName(FileUtilities.getFileNameWithNoExtension(file));
		return vlb.build();
	}

	private File saveNewVocabListAsFile(File file) throws FileNotFoundException, IOException {
		VocabListSaver vls = new SerializedFileVocabListSaver();
		String filePath = FileUtilities.formatFilepathForSerialization(file);
		vls.saveVocabList(vocabList, filePath);
		return new File(filePath);
	}

	private List<String> getKoreanList(String fileName, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		return vocabGetter.getVocabFromFile(filePath + "/" + fileName + " Korean");
	}

	private List<String> getEnglishList(String fileName, String filePath) throws FileNotFoundException, UnsupportedEncodingException{
		File englishListFile = new File(filePath + "/" + fileName);
		return vocabGetter.getVocabFromFile(englishListFile);
	}

	private Vocab addDialog(){
		Vocab vocab = new Vocab(PRIMARY_LANGUAGE);
		return vocab;
	}
	

	 
	private class AppWindowListener implements WindowListener {
		
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
				Main.this.dispose();
			}	
		}
	}
	
	private void showErrorDialog(String title, String message) {
		JOptionPane.showMessageDialog(this, title, message, JOptionPane.ERROR_MESSAGE);
	}

	private void saveVocabListAsTextFile(VocabList vocabList, File file) {
		try {
			textFileVocabListSaver.saveVocabList(vocabList, file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
