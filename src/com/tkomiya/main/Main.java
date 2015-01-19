package com.tkomiya.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.tkomiya.controllers.FileLink;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825416147024612814L;
	private JTextArea textArea;
	private int currentChunk;
	private int chunkSize = 10;
	private VocabList vocabList;
	private ListStringGetter vocabGetter;
	private VocabListGetter vlistGetter;
	private JFileChooser fileChooser;
	private String defaultDirectory = "./res/";
	private boolean showAnswers = false;
	private final String WRITTEN_TEST_MENU_ITEM = "written";
	private DefaultListModel<FileLink> filelinkListModel;
	private VocabListTableModel vltm;
	private static final String MAKE_LINK_MENU_ITEM_NAME = "link";
	private static final String DELETE_MENU_ITEM_NAME = "delete";
	private static final String ADD_BUTTON_NAME = "add";
	private static final String RECONSTRUCT_MENU_ITEM_NAME = "reconstruct";
	private static final int PRIMARY_LANGUAGE = Vocab.ENGLISH;
	private JPopupMenu shortcutListPopup;
	private FileLink currentLink;
	
	public Main() {

		// make a filechooser
		fileChooser = new JFileChooser(defaultDirectory);

		// Make our vocab getter
		vocabGetter = new NewlineSeparatedTextfileStringListGetter();

		// make our vocabListgetter
		vlistGetter = new SerializedFileVocabListGetter();

		// Make a text are to display the vocab
		textArea = new JTextArea();
		textArea.setEditable(false);
//		JScrollPane scrollPane = new JScrollPane(textArea);


		//make a table to display the vocab
		vltm = new VocabListTableModel();
		JTable table = new JTable(vltm);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		scrollPane.setSize(100, 400);
		
		// make the file menu
		ActionListener menuListener = new MenuItemListener();

		JMenuItem open = new JMenuItem("Open");
		open.setName("Open");
		open.addActionListener(menuListener);

		JMenuItem createNew = new JMenuItem("Create new...");
		createNew.setName("create");
		createNew.addActionListener(menuListener);

		JMenuItem startTest = new JMenuItem("Start a test");
		startTest.setName("test");
		startTest.addActionListener(menuListener);
		
		JMenuItem written = new JMenuItem("Start a written test");
		written.setName(WRITTEN_TEST_MENU_ITEM);
		written.addActionListener(menuListener);
		
		JMenuItem makeLink = new JMenuItem("Add a shortcut");
		makeLink.setName(MAKE_LINK_MENU_ITEM_NAME );
		makeLink.addActionListener(menuListener);
		
		JMenuItem reconstruct = new JMenuItem("Reconstruct");
		reconstruct.setName(RECONSTRUCT_MENU_ITEM_NAME);
		reconstruct.addActionListener(menuListener);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(open);
		fileMenu.add(createNew);
		fileMenu.add(startTest);
		fileMenu.add(written);
		fileMenu.add(makeLink);
		fileMenu.add(reconstruct);
		
		// make a menu bar
		JMenuBar mb = new JMenuBar();
		mb.add(fileMenu);

		// Make the buttons
		ActionListener listener = new Main.ButtonListener();
		JButton next = new JButton("Next");
		next.setName("Next");
		next.addActionListener(listener);
		JButton previous = new JButton("Back");
		previous.setName("Previous");
		previous.addActionListener(listener);
		JButton showAnswers = new JButton("Show answers");
		showAnswers.setName("show");
		showAnswers.addActionListener(listener);
		JButton add = new JButton("+");
		add.setName(ADD_BUTTON_NAME);
		add.addActionListener(listener);
		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(previous);
		buttonPanel.add(next);
		buttonPanel.add(showAnswers);

		//make the side shortcut panel
		JPanel sidePanel = new JPanel();
		filelinkListModel = new DefaultListModel<FileLink>();
		getSavedLinks();
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
		sidePanel.add(links);
		
		//make the shortcut list's popup menu
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
		
		// Main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(sidePanel, BorderLayout.WEST);

		// Make a frame
		addWindowListener(this);
		add(mainPanel);
		setJMenuBar(mb);
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
						"Vocab list files", "vocab"));

				int val = fileChooser.showOpenDialog(Main.this);

				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						vocabList = vlistGetter.getVocabListFromFile(file);
						currentChunk = 0;
						vocabList.shuffle();
						fillTextArea();
						FileLink flink = new FileLink(file, file.getName());
						if(!filelinkListModel.contains(flink))
							filelinkListModel.addElement(flink);

					} catch (Exception e1) {
						reconstructVocabFile(file);
//						e1.printStackTrace();
					}
				}

			} else if (sourceName == "create") {

				new ListInput();
//				fileChooser.setFileFilter(null);
//				int val = fileChooser.showOpenDialog(Main.this);
//				File koreanFile;
//
//				if (val == JFileChooser.APPROVE_OPTION) {
//					koreanFile = fileChooser.getSelectedFile();
//				} else {
//					return;
//				}
//				List<String> koreanWords;
//				try {
//					koreanWords = vocabGetter.getVocabFromFile(koreanFile);
//
//				} catch (FileNotFoundException e1) {
//
//					e1.printStackTrace();
//					return;
//				} catch (UnsupportedEncodingException e1) {
//
//					e1.printStackTrace();
//					return;
//				}
//
//				val = fileChooser.showOpenDialog(Main.this);
//				File englishFile;
//
//				if (val == JFileChooser.APPROVE_OPTION) {
//					englishFile = fileChooser.getSelectedFile();
//				} else {
//					return;
//				}
//
//				List<String> englishWords;
//				try {
//					englishWords = vocabGetter.getVocabFromFile(englishFile);
//				} catch (FileNotFoundException e1) {
//
//					e1.printStackTrace();
//					return;
//				} catch (UnsupportedEncodingException e1) {
//
//					e1.printStackTrace();
//					return;
//				}
//
//				val = fileChooser.showOpenDialog(Main.this);
//				File newVocablistFile;
//				if (val == JFileChooser.APPROVE_OPTION) {
//					newVocablistFile = fileChooser.getSelectedFile();
//				} else {
//					return;
//				}
//
//				VocabListBuilder vlBuilder = new VocabListBuilder();
//				vlBuilder.setPrimaryLanguage(Vocab.ENGLISH, englishWords);
//				vlBuilder.addLanguage(Vocab.KOREAN, koreanWords);
//				VocabList vl;
//				try {
//					vl = vlBuilder.build();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					return;
//				}
//
//				VocabListSaver vls = new SerializedFileVocabListSaver();
//				try {
//					vls.saveVocabList(vl, newVocablistFile);
//					return;
//				} catch (IOException e1) {
//
//					e1.printStackTrace();
//					return;
//				}

			} 
			else if (sourceName == "test") {
				
				new TypedTest(Main.this.vocabList, Vocab.KOREAN);
//				Main.this.setVisible(false);

			}
			else if(sourceName == WRITTEN_TEST_MENU_ITEM){
				
				new WrittenTest(Main.this.vocabList, Vocab.KOREAN);
			}
			else if(sourceName.equals(Main.MAKE_LINK_MENU_ITEM_NAME)){
				
				fileChooser.setFileFilter(new FileNameExtensionFilter("Vocab list files", "vocab"));
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
		
//		VocabList vocab = vocabList.subList(currentChunk * chunkSize, Math.min(
//				vocabList.size(), currentChunk * chunkSize + chunkSize));
//
//		if (!showAnswers) {
//			textArea.setText(null);
//			for (int i = 0; i < vocab.size(); i++) {
//				textArea.append(vocab.get(i).getTranslation(Vocab.ENGLISH));
//				textArea.append("\n");
//			}
//		} else {
//			textArea.setText(null);
//			for (int i = 0; i < vocab.size(); i++) {
//				textArea.append(vocab.get(i).getTranslation(Vocab.ENGLISH)
//						+ "    -    "
//						+ vocab.get(i).getTranslation(Vocab.KOREAN));
//				textArea.append("\n");
//			}
//		}

	}
	
	private void reconstructVocabFile(File file){
		
		
		String fileName = file.getName();
		String filePath = file.getPath().substring(0, file.getPath().length() - fileName.length());
		
		fileName = fileName.substring(0, fileName.length() - 6);
		
		//get first list
		File englishListFile = new File(filePath + "/" + fileName);
		List<String> englishList;
		try {
			englishList = vocabGetter.getVocabFromFile(englishListFile);
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		} 
		
		//get the korean list
		List<String> koreanList;
		try {
			koreanList = vocabGetter.getVocabFromFile(filePath + "/" + fileName + " Korean");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		VocabListBuilder vlb = new VocabListBuilder();
		vlb.setPrimaryLanguage(Vocab.ENGLISH, englishList);
		vlb.addLanguage(Vocab.KOREAN, koreanList);
		try {
			this.vocabList = vlb.build();
			fillTextArea();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		VocabListSaver vls = new SerializedFileVocabListSaver();
		try {
			vls.saveVocabList(vocabList, file.getPath());
			filelinkListModel.addElement(new FileLink(file, file.getName()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
//		JDialog dialog = new JDialog(this, "Enter details",true);
		Vocab vocab = new Vocab(PRIMARY_LANGUAGE);
//		
//		JTextField field = new JTextField();
//		field.setText("Enter the word here");
//		JComboBox<String> comboBox = new JComboBox<String>(Vocab.SUPPORTED_LANGUAGES);
//		JLabel label = new JLabel("Primary Language");
//		JRadioButton button = new JRadioButton();
//		button.setSelected(true);
//		
//		JPanel panel = new JPanel()
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
