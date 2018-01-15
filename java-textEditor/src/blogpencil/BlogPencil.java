/**
 * This is my own personal text editor with a future concept:
 * it is related to my interest on creating an editor that work with
 * google blog since the actual blog editors online has problems to
 * perform this action. 
 */
package blogpencil;

//importing packages needed for the project to work
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * @author murodriguez
 *
 */
public class BlogPencil extends JFrame{

	/**
	 * Base variables
	 */
	private JTextArea area = new JTextArea(20, 120);
//	private JTabbedPane tab = new JTabbedPane();//to open each file in a tab panel
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "Untitled";
	private boolean changed = false;
	
	
//	constructor
	public BlogPencil() {
		area.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		//Menu Bar
		JMenuBar JBM = new JMenuBar();
		setJMenuBar(JBM);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JBM.add(file);JBM.add(edit);
		
		// - file menu
		file.add(New);file.add(Open);file.add(Save);file.add(SaveAs);file.add(Quit);file.addSeparator();
		
		for(int i = 0; i < 4; i++) {
			file.getItem(i).setIcon(null);
		}
		
		// - edit menu
		edit.add(Cut);edit.add(Copy);edit.add(Paste);
		
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		
		//toolbar
		JToolBar tool = new JToolBar();
		//placing the toolbar and adding the buttons ADD, OPEN, SAVE
		add(tool, BorderLayout.NORTH);
		tool.add(New);tool.add(Open);tool.add(Save);tool.addSeparator();
		
		//setting the button icons
		JButton cut = tool.add(Cut), cop = tool.add(Copy), pas = tool.add(Paste);
		cut.setText(null);cut.setIcon(new ImageIcon(getClass().getResource("save.png")));
		cop.setText(null);cop.setIcon(new ImageIcon(getClass().getResource("save.png")));
		pas.setText(null);pas.setIcon(new ImageIcon(getClass().getResource("save.png")));
		
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);
		setTitle(currentFile);
		setVisible(true);
	}
	
	//k1 listener
	private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			changed = true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};
	
	/**
	 * Action New | Open | Save | SavesAs | Quit | New
	 */
	//New
	Action New = new AbstractAction("New", new ImageIcon(getClass().getResource("save.png"))) {
		public void actionPerformed(ActionEvent e) {
			//TODO created a new file
			//newFile();
		}
	};
	//Open
	Action Open = new AbstractAction("Open", new ImageIcon(getClass().getResource("save.png"))) {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			if(dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
			SaveAs.setEnabled(true);
		}
	};
	//Save
	Action Save = new AbstractAction("Save", new ImageIcon(getClass().getResource("save.png"))) {
		public void actionPerformed(ActionEvent e) {
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();
		}
	};
	//SavesAs...
	Action SaveAs = new AbstractAction("Save as...") {
		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
	//Quit
	Action Quit = new AbstractAction("Quit") {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);
		}
	};
	
	//Action Mapping
	ActionMap m = area.getActionMap();
	//adding default action to cut, copy and paste
	Action Cut = m.get(DefaultEditorKit.cutAction);
	Action Copy = m.get(DefaultEditorKit.copyAction);
	Action Paste = m.get(DefaultEditorKit.pasteAction);
	
	/**
	 * Methods
	 * type: newFile, saveFileAs, saveOld, readInFile, saveFile
	 */
	//newFile()
//	private void newFile(String fileName) {
//		try {
//			//trying to create a new file with untitled name
//			JComponent panel = makeTextPanel(currentFile);
//			tab.addTab(currentFile, area.getDocument());
//		}catch(IOException e) {
//			//nothing goes here.
//		}
//	}
	//saveFileAs()
	private void saveFileAs() {
		if(dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	//saveOld()
	private void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save " + currentFile + " ?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				saveFile(currentFile);
			}
		}
	}
	//readInFile()
	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			area.read(r, null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}catch(IOException e){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Editor can't find the file called " + fileName);
		}
	}
	//saveFile()
	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			area.write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}catch(IOException e){
			//empty here on purpose 
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BlogPencil();
	}

}
