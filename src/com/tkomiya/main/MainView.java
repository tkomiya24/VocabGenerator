package com.tkomiya.main;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.border.EtchedBorder;

import com.tkomiya.infrastructure.VocabListTableModel;
import com.tkomiya.models.VocabList;

/**
 * @author Takeru
 *
 */
public class MainView extends JFrame {
	private JTextArea textArea;
	private JPopupMenu shortcutListPopup;
	private ActionListener menuListener;
	private ActionListener buttonListener;
	private List<JMenuItem> menuItems;
	private DefaultListModel<VocabList> vocabListListModel;
	private VocabList currentlySelectedVocabList;
	private JList<VocabList> links;
	private MouseAdapter shorcutListMouseAdapter;
	private JPanel buttonPanel;
	private VocabListTableModel vltm;
	private List<VocabList> vocabLists;
	
	public MainView(WindowListener mainWindowsListener, ActionListener mainMenuItemListener, ActionListener mainButtonListener, MouseAdapter mouseAdapterForShortcutList, List<VocabList> vocabLists) {
		super();
		this.menuListener = mainMenuItemListener;
		this.buttonListener = mainButtonListener;
		this.shorcutListMouseAdapter = mouseAdapterForShortcutList;
		this.vocabLists = vocabLists;
		init();
		addWindowListener(mainWindowsListener);
		makeTextArea();
		makeFrame();
	}
	
	public void showErrorDialog(String title, String message) {
		JOptionPane.showMessageDialog(this, title, message, JOptionPane.ERROR_MESSAGE);
	}
	
	public int showConfirmationDialog(String title, String message) {
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
	}
	
	public void showShortcutPopup(int x, int y) {
		shortcutListPopup.show(links, x, y);
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
	
	/**
	 * @return the links
	 */
	public JList<VocabList> getLinks() {
		return links;
	}
	
	private void init() {
		 menuItems = new ArrayList<JMenuItem>();	
		 buttonPanel = new JPanel();
	}
	
	private void makeButtonPanel(){		
		makeButtonAndAddItToButtonPanel("Next");
		makeButtonAndAddItToButtonPanel("Previous");
		makeButtonAndAddItToButtonPanel("show");
		makeButtonAndAddItToButtonPanel(MainController.ADD_BUTTON_NAME);
	}
	
	private Collection<JMenuItem> makeMenuItems(){		
		makeMenuItem(MainController.OPEN_MENU_ITEM_NAME);
		makeMenuItem(MainController.START_TEST_MENU_ITEM_NAME);
		makeMenuItem(MainController.WRITTEN_TEST_MENU_ITEM);
		makeMenuItem(MainController.COMMON_MISTAKE_TEST_MENU_ITEM_NAME);
		makeMenuItem(MainController.SAVE_MENU_ITEM_NAME);
		makeMenuItem(MainController.LOAD_MENU_ITEM_NAME);
		makeMenuItem(MainController.BACKUP_ALL_MENU_ITEM_NAME);
		makeMenuItem(MainController.LOAD_ALL_MENU_ITEM_NAME);	
		return menuItems;
	}
	
	private void makeShorcutListPopup(JList<VocabList> links){
		shortcutListPopup = new JPopupMenu();
		JMenuItem deleteMenuItem = new JMenuItem(MainController.DELETE_MENU_ITEM_NAME);
		deleteMenuItem.setName(MainController.DELETE_MENU_ITEM_NAME);
		deleteMenuItem.addActionListener(menuListener);
		shortcutListPopup.add(deleteMenuItem);
	}
	
	private JList<VocabList> makeShortcutPanelList(){
		vocabListListModel = new DefaultListModel<VocabList>();
		links = new JList<VocabList>(vocabListListModel);
		links.addMouseListener(shorcutListMouseAdapter);
		makeShorcutListPopup(links);
		return links;
	}
	
	private void makeButtonAndAddItToButtonPanel(String buttonName) {
		JButton button = new JButton(buttonName);
		button.setName(buttonName);
		button.addActionListener(buttonListener);
		buttonPanel.add(button);
	}
	
	private JMenuBar makeMenuBar(){
		JMenuBar mb = new JMenuBar();
		mb.add(makeMenu());
		return mb;
	}
	
	private JMenu makeMenu(){
		JMenu fileMenu = new JMenu("File");
		Collection<JMenuItem> menuItems = makeMenuItems();
		for(JMenuItem menuItem : menuItems){
			fileMenu.add(menuItem);
		}
		return fileMenu;
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
	
	private void makeMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setName(name);
		menuItem.addActionListener(menuListener);
		menuItems.add(menuItem);
	}
	
	private JTable makeVocabTable(){
		vltm = new VocabListTableModel();
		JTable vocabTable = new JTable(vltm);
		vocabTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return vocabTable;
	}
	
	private JScrollPane makeVocabTablePane(){
		JScrollPane scrollPane = new JScrollPane(makeVocabTable());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		scrollPane.setSize(100, 400);
		return scrollPane;
	}
	
	private void makeTextArea(){
		textArea = new JTextArea();
		textArea.setEditable(false);
	}
	
	private void makeFrame(){
		addMainPanel();
		setJMenuBar(makeMenuBar());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addMainPanel(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(makeVocabTablePane(), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(makeShortcutPanel(), BorderLayout.WEST);
		add(mainPanel);
	}

}
