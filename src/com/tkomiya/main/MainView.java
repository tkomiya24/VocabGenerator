package com.tkomiya.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;

import com.tkomiya.infrastructure.VocabListTableModel;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.Vocab.SupportedLanguage;
import com.tkomiya.models.VocabList;
import com.tkomiya.views.utils.JComponentFactory;

public class MainView extends JFrame {

  private static final long serialVersionUID = -9134764614592744974L;
  private JTextArea textArea;
	private JPopupMenu shortcutListPopup;
	private JPopupMenu vocabTablePopup;
	private ActionListener menuListener;
	private ActionListener buttonListener;
	private List<JMenuItem> menuItems;
	private DefaultListModel<VocabList> vocabListListModel;
	private VocabList currentlySelectedVocabList;
	private JList<VocabList> links;
	private MouseAdapter mouseAdapter;
	private JPanel buttonPanel;
	private VocabListTableModel vltm;
	private List<VocabList> vocabLists;
	private JTable vocabTable;
	private JLabel vocabListNameLabel;
	private MainController mainController;
	
	//Menu Item names
	public static final String DELETE_MENU_ITEM_NAME = "Delete vocab list";
	public static final String COMMON_MISTAKE_TEST_MENU_ITEM_NAME = "Test common mistakes";
	public static final String BACKUP_ALL_MENU_ITEM_NAME = "Back up all vocablists as text files";
	public static final String LOAD_ALL_MENU_ITEM_NAME = "Load all backup text files";
	public static final String OPEN_MENU_ITEM_NAME = "Open";
	public static final String START_TEST_MENU_ITEM_NAME = "Start a test";
	public static final String DELETE_VOCAB_MENU_ITEM_NAME = "Delete vocab";
	public static final String LEAST_TESTED_VOCABLIST_MENU_ITEM_NAME = "Start a test with the least tested vocablist";
	public static final String EXPORT_MONGO_MENU_ITEM_NAME = "Export file for MongoDB";
	
	public MainView(MainController mainController, WindowListener mainWindowsListener, ActionListener mainButtonListener, MouseAdapter mouseAdapter, List<VocabList> vocabLists) {
		super();
		this.menuListener = new MainMenuItemListener();
		this.buttonListener = mainButtonListener;
		this.mouseAdapter = mouseAdapter;
		this.mainController = mainController;
		this.vocabLists = vocabLists;
		init();
		addWindowListener(mainWindowsListener);
		makeTextArea();
		makeFrame();
	}
	
	public void showErrorDialog(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public int showConfirmationDialog(String title, String message) {
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public String showInputDialog(String title, String message) {
		return JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE);
	}
	
	public int showOptionDialog(String message, String title, Object[] options, Object initialValue) {
		return JOptionPane.showOptionDialog(this, message, title, JOptionPane.DEFAULT_OPTION
				, JOptionPane.QUESTION_MESSAGE, null, options, initialValue);
	}
	
	/**
	 * @return the currentlySelectedVocabList
	 */
	public VocabList getCurrentlySelectedVocabList() {
		return currentlySelectedVocabList;
	}

	/**
	 * @param currentlySelectedVocabList the currentlySelectedVocabList to set
	 */
	public void setCurrentlySelectedVocabList(VocabList currentlySelectedVocabList) {
		this.currentlySelectedVocabList = currentlySelectedVocabList;
	}

	public void updateVocabListTable() {
		this.vltm.setVocabList(currentlySelectedVocabList);
		this.vocabListNameLabel.setText(currentlySelectedVocabList.getName());
	}
	
	public void showShortcutPopup(int x, int y) {
		shortcutListPopup.show(links, x, y);
	}

	public void showVocabTableShortcut(MouseEvent e) {
		vocabTablePopup.show(vocabTable, e.getX(), e.getY());
		Point mouseLocation = e.getPoint();
		int selectedRow = vocabTable.rowAtPoint(mouseLocation);
		vocabTable.setRowSelectionInterval(selectedRow, selectedRow);
	}
	
	public void addToShortcutPane(VocabList vocabList, int index) {
		vocabListListModel.insertElementAt(vocabList, index);
	}
	
	public void refreshShortcutPanel() {
		vocabListListModel.clear();
		fillShortcutPanel();
	}

	public void removeLink(int index) {
		vocabListListModel.remove(index);
	}
	
	public void refreshVocabTable() {
		vltm.fireTableDataChanged();
	}
	
	/**
	 * @return the links
	 */
	public JList<VocabList> getLinks() {
		return links;
	}
	
	public Vocab getCurrentlySelectedVocab() {
		return vltm.getVocab(vocabTable.getSelectedRow());
	}
	
	private void init() {
		 menuItems = new ArrayList<JMenuItem>();	
		 buttonPanel = new JPanel();
	}
	
	private void makeButtonPanel() {		
		makeButtonAndAddItToButtonPanel(MainController.ADD_BUTTON_NAME);
	}
	
	private Collection<JMenuItem> makeMenuItems() {		
		makeMenuItem(OPEN_MENU_ITEM_NAME);
		makeMenuItem(START_TEST_MENU_ITEM_NAME);
		makeMenuItem(COMMON_MISTAKE_TEST_MENU_ITEM_NAME);
		makeMenuItem(LEAST_TESTED_VOCABLIST_MENU_ITEM_NAME);
		makeMenuItem(BACKUP_ALL_MENU_ITEM_NAME);
		makeMenuItem(LOAD_ALL_MENU_ITEM_NAME);	
		makeMenuItem(EXPORT_MONGO_MENU_ITEM_NAME);
		return menuItems;
	}

	private Component makeShortcutPanelList() {
		vocabListListModel = new DefaultListModel<VocabList>();
		links = new JList<VocabList>(vocabListListModel);
		fillShortcutPanel();
		JScrollPane scrollPane = new JScrollPane(links);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		links.addMouseListener(mouseAdapter);
		makeShorcutListPopup();
		return scrollPane;
	}
	
	private void makeShorcutListPopup() {
		shortcutListPopup = new JPopupMenu();
		JMenuItem deleteMenuItem = makePopupMenuItem(DELETE_MENU_ITEM_NAME);
		shortcutListPopup.add(deleteMenuItem);
	}
	
	private JMenuItem makePopupMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setName(name);
		menuItem.addActionListener(menuListener);
		return menuItem;
	}
	
	private void makeButtonAndAddItToButtonPanel(String buttonName) {
		buttonPanel.add(JComponentFactory.makeButton(buttonName, buttonListener));
	}
	
	private JMenuBar makeMenuBar() {
		JMenuBar mb = new JMenuBar();
		mb.add(makeMenu());
		mb.add(makeSearchField());
		return mb;
	}
	
	private JMenu makeMenu() {
		JMenu fileMenu = new JMenu("File");
		Collection<JMenuItem> menuItems = makeMenuItems();
		for(JMenuItem menuItem : menuItems) {
			fileMenu.add(menuItem);
		}
		return fileMenu;
	}
	
	private JTextField makeSearchField() {
		JTextField searchField = new JTextField();
		searchField.setName(MainController.SEARCH_FIELD_NAME);
		addListenerToSearchField(searchField);
		return searchField;
	}
	
	private void addListenerToSearchField(JTextField searchField) {
		searchField.addActionListener(buttonListener);
	}
	
	private void fillShortcutPanel() {
		for (int i = 0; i < vocabLists.size(); i++) {
			vocabListListModel.addElement(vocabLists.get(i));
		}
	}
	
	private void makeMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setName(name);
		menuItem.addActionListener(menuListener);
		menuItems.add(menuItem);
	}
	
	private void makeFrame() {
		addMainPanel();
		setJMenuBar(makeMenuBar());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(makeMainPanel(), BorderLayout.CENTER);
		makeButtonPanel();
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(makeShortcutPanelList(), BorderLayout.WEST);
		add(mainPanel);
	}
	
	private JPanel makeMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(makeVocabTablePane(), BorderLayout.CENTER);
		mainPanel.add((vocabListNameLabel = new JLabel()), BorderLayout.NORTH);
		return mainPanel;
	}
	
	private JScrollPane makeVocabTablePane() {
		JScrollPane scrollPane = new JScrollPane(makeVocabTable());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		scrollPane.setSize(100, 400);
		return scrollPane;
	}
	
	private JTable makeVocabTable() {
		vltm = new VocabListTableModel();
		vocabTable = new JTable(vltm);
		vocabTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vocabTable.addMouseListener(mouseAdapter);
		makeVocabTablePopup();
		return vocabTable;
	}
	
	private void makeVocabTablePopup() {
		vocabTablePopup = new JPopupMenu();
		vocabTablePopup.add(makePopupMenuItem(DELETE_VOCAB_MENU_ITEM_NAME));
	}
	
	private void makeTextArea() {
		textArea = new JTextArea();
		textArea.setEditable(false);
	}
	
	private SupportedLanguage getTestingLanguageFromUser() {
		ArrayList<SupportedLanguage> optionList = new ArrayList<Vocab.SupportedLanguage>();
		for (SupportedLanguage language : SupportedLanguage.values()) {
			if (language != SupportedLanguage.ENGLISH) {
				optionList.add(language);
			}
		}
		Object[] options = optionList.toArray();
		int selectedIndex = showOptionDialog("Which language would you like to test?", "Please enter option", options, 0);
		return (SupportedLanguage) options[selectedIndex];
	}
	
	private int getTestLengthFromUser() {
		boolean badInput = true;
		int testLength = 0;
		do {
			String response = showInputDialog("Length", "Please enter the maximum desired test length");
			try {
				testLength = Integer.parseInt(response);
				badInput = false;
				return testLength;
			} catch(NumberFormatException e) {
				showErrorDialog("Error with input", "Please input a number");
			}
		} while(badInput);
		return testLength;
	}
	
	private class MainMenuItemListener implements ActionListener {

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
			if (sourceName.equals(OPEN_MENU_ITEM_NAME)) {
				mainController.openMenuItemAction();
			} else if (sourceName.equals(START_TEST_MENU_ITEM_NAME)) {
				mainController.startTestMenuItemAction(getTestingLanguageFromUser());
			} else if (sourceName.equals(COMMON_MISTAKE_TEST_MENU_ITEM_NAME)) {
				mainController.commonMistakeTest(getTestingLanguageFromUser(), getTestLengthFromUser());
			} else if (sourceName.equals(BACKUP_ALL_MENU_ITEM_NAME)) {
				mainController.backupAllMenuItemAction();
			} else if (sourceName.equals(LOAD_ALL_MENU_ITEM_NAME)) {
				mainController.loadAllMenuItemAction();
			} else if (sourceName.equals(DELETE_MENU_ITEM_NAME)) {
				mainController.deleteMenuItemAction();
			} else if (sourceName.equals(DELETE_VOCAB_MENU_ITEM_NAME)) {
				mainController.deleteVocabMenuItemAction(vocabTable.getSelectedRow());
			} else if (sourceName.equals(LEAST_TESTED_VOCABLIST_MENU_ITEM_NAME)) {
				mainController.leastTestedTest(getTestingLanguageFromUser());
			} else if (sourceName.equals(EXPORT_MONGO_MENU_ITEM_NAME)) {
				mainController.exportMongo();
			}
		}
	}

}
