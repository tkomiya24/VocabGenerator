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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tkomiya.controllers.FileLink;
import com.tkomiya.infrastructure.FileUtilities;
import com.tkomiya.infrastructure.VocabListTableModel;
import com.tkomiya.listgetter.ListStringGetter;
import com.tkomiya.listgetter.NewlineSeparatedTextfileStringListGetter;
import com.tkomiya.models.Vocab;
import com.tkomiya.models.VocabList;
import com.tkomiya.models.VocabListBuilder;
import com.tkomiya.views.ListInput;
import com.tkomiya.views.TypedTest;
import com.tkomiya.views.WrittenTest;
import com.tkomiya.vocablistgetter.SerializedFileVocabListGetter;
import com.tkomiya.vocablistgetter.VocabListGetter;
import com.tkomiya.vocablistsaver.SerializedFileVocabListSaver;
import com.tkomiya.vocablistsaver.VocabListSaver;

public class Main extends JFrame implements WindowListener{

	private static final long serialVersionUID = 2825416147024612814L;
	private JTextArea textArea;
	private int currentChunk;
	private int chunkSize = 10;
	private VocabList vocabList;
	private ListStringGetter vocabGetter;
	private VocabListGetter vlistGetter;
	private JFileChooser fileChooser;
	public static final String DEFAULT_DIRECTORY = "./res/";
	private boolean showAnswers = false;
	private final String WRITTEN_TEST_MENU_ITEM = "written";
	private DefaultListModel<FileLink> filelinkListModel;
	private VocabListTableModel vltm;
	private static final String MAKE_LINK_MENU_ITEM_NAME = "link";
	private static final String DELETE_MENU_ITEM_NAME = "delete";
	private static final String ADD_BUTTON_NAME = "add";
	private static final String RECONSTRUCT_MENU_ITEM_NAME = "reconstruct";
	private static final int PRIMARY_LANGUAGE = Vocab.ENGLISH;
	public static final String VOCAB_LIST_FILE_EXTENSION = "voc";
	private JPopupMenu shortcutListPopup;
	private FileLink currentLink;
	
	public Main() {
		initializeFields();
		makeTextArea();
		makeFrame();
	}

	private void initializeFields(){
		fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
		vocabGetter = new NewlineSeparatedTextfileStringListGetter();
		vlistGetter = new SerializedFileVocabListGetter();
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
		return new JTable(vltm);
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

		JMenuItem createNew = new JMenuItem("Create new...");
		createNew.setName("create");
		createNew.addActionListener(menuListener);
		menuItems.add(createNew);
		
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
		
		JMenuItem reconstruct = new JMenuItem("Reconstruct");
		reconstruct.setName(RECONSTRUCT_MENU_ITEM_NAME);
		reconstruct.addActionListener(menuListener);
		menuItems.add(reconstruct);
		
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
		getSavedLinks();
		return sidePanel;
	}
	
	private JList<FileLink> makeShortcutPanelList(){

		filelinkListModel = new DefaultListModel<FileLink>();
		JList<FileLink> links = new JList<FileLink>(filelinkListModel);
		links.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount() == 2){					
					@SuppressWarnings("unchecked")
					JList<FileLink> list = (JList<FileLink>) e.getSource();
					FileLink link = list.getSelectedValue();
					try {
						vocabList = vlistGetter.getVocabListFromFile(link.getFile());
						currentLink = link;
						fillTextArea();
					} catch (Exception e1) {
						reconstructVocabFile(link.getFile());
						e1.printStackTrace();
					}
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
	
	private void makeShorcutListPopup(JList<FileLink> links){
		shortcutListPopup = new JPopupMenu();
		JMenuItem deleteMenuItem = new JMenuItem("Delete shorcut");
		deleteMenuItem.setName(DELETE_MENU_ITEM_NAME);
		deleteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JMenuItem menuItem = (JMenuItem) e.getSource();
				String sourceName = menuItem.getName();
				if(sourceName.equals(DELETE_MENU_ITEM_NAME)){
					
					FileLink fileLink = links.getSelectedValue();
					fileLink.delete();
					filelinkListModel.remove(links.getSelectedIndex());
				}
			}
		});
		shortcutListPopup.add(deleteMenuItem);
	}
	
	private JPanel makeMainPanel(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(makeVocabTablePane(), BorderLayout.CENTER);
		mainPanel.add(makeButtonPanel(), BorderLayout.SOUTH);
		mainPanel.add(makeShortcutPanel(), BorderLayout.WEST);
		return mainPanel;
	}
	
	private void makeFrame(){
		addWindowListener(this);
		add(makeMainPanel());
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

				fileChooser.setFileFilter(new FileNameExtensionFilter(
						"Vocab list files", VOCAB_LIST_FILE_EXTENSION));

				int val = fileChooser.showOpenDialog(Main.this);

				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						String extension = FileUtilities.getFileExtension(file);
						if(extension.equals(VOCAB_LIST_FILE_EXTENSION)){
							vocabList = vlistGetter.getVocabListFromFile(file);
							currentChunk = 0;
							vocabList.shuffle();
							fillTextArea();
							FileLink flink = new FileLink(file, file.getName());
							if(!filelinkListModel.contains(flink))
								filelinkListModel.addElement(flink);
						}
						else{
							reconstructVocabFile(file);
						}

					} catch (Exception e1) {
						reconstructVocabFile(file);
						e1.printStackTrace();
					}
				}

			} else if (sourceName == "create") {

				new ListInput();
			} 
			else if (sourceName == "test") {
				
				new TypedTest(Main.this.vocabList, Vocab.KOREAN);
			}
			else if(sourceName == WRITTEN_TEST_MENU_ITEM){
				
				new WrittenTest(Main.this.vocabList, Vocab.KOREAN);
			}
			else if(sourceName.equals(Main.MAKE_LINK_MENU_ITEM_NAME)){
				
				fileChooser.setFileFilter(new FileNameExtensionFilter("Vocab list files", VOCAB_LIST_FILE_EXTENSION));
				int val = fileChooser.showOpenDialog(Main.this);
				
				if(val == JFileChooser.APPROVE_OPTION){
					
					File file = fileChooser.getSelectedFile();
					FileLink link = new FileLink(file, file.getName());
					filelinkListModel.add(filelinkListModel.getSize(), link);
				}
			}
			else if(sourceName.equals(RECONSTRUCT_MENU_ITEM_NAME)){
				
				reconstructVocabFile(currentLink.getFile());
				fillTextArea();
			}
		}
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
	
	private void reconstructVocabFile(File file){				
		String fileName = FileUtilities.getFileNameWithNoExtension(file);
		String filePath = FileUtilities.getFilePathWithoutFileName(file);
		try {
			List<String> englishList = getEnglishList(fileName, filePath);
			List<String> koreanList = getKoreanList(fileName, filePath);
			VocabListBuilder vlb = new VocabListBuilder();
			vlb.setPrimaryLanguage(Vocab.ENGLISH, englishList);
			vlb.addLanguage(Vocab.KOREAN, koreanList);
			this.vocabList = vlb.build();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		} 			
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		fillTextArea();
		try {
			File newFile = saveNewVocabListAsFile(file);
			filelinkListModel.addElement(new FileLink(newFile, FileUtilities.getFileNameWithNoExtension(newFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	@SuppressWarnings("unchecked")
	private void getSavedLinks(){
		
		List<FileLink> links;
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("./links.config"))));
			links = (ArrayList<FileLink>) ois.readObject();
			ois.close();
			for(int i = 0; i < links.size(); i++){
				filelinkListModel.addElement(links.get(i));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
		ArrayList<FileLink> links = new ArrayList<FileLink>();
		
		for(int i = 0; i < filelinkListModel.getSize(); i++){
			
			FileLink link = filelinkListModel.getElementAt(i);
			links.add(link);
		}
		try {
			File file = new File("./links.config");
			file.delete();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			oos.writeObject(links);
			oos.close();
			this.dispose();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private Vocab addDialog(){
		
		Vocab vocab = new Vocab(PRIMARY_LANGUAGE);
		return vocab;
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
	 
}
