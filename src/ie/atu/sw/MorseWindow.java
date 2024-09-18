package ie.atu.sw;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class MorseWindow {
	private Colour[] colours = Colour.values(); //This might come in handy
	private ThreadLocalRandom rand = ThreadLocalRandom.current(); //This will definitely come in handy
	private JFrame win; //The GUI Window
	private JTextArea txtOutput = new JTextArea(); //The text box to output the results to
	private JTextField txtFilePath; //The file name to process
	private BinaryTree<Character> tree;
	
	public MorseWindow(){
		
		var dot = new JPanel();
		
		//Initialising the binary tree
		var createTree = new Character(" ", 100);
		tree = new BinaryTree<Character>(Character::compareTo, createTree);
		init(createTree);
		
		/*
		 * Create a window for the application. Building a GUI is an example of 
		 * "divide and conquer" in action. A GUI is really a tree. That is why
		 * we are able to create and configure GUIs in XML.
		 */
		win = new JFrame();
		win.setTitle("Data Structures & Algorithms 2023 - Morse Encoder/Decoder");
		win.setSize(650, 400);
		win.setResizable(false);
		win.setLayout(new FlowLayout());
		
        /*
         * The top panel will contain the file chooser and encode / decode buttons
         */
        var top = new JPanel(new FlowLayout(FlowLayout.LEADING));
        top.setBorder(new javax.swing.border.TitledBorder("Select File"));
        top.setPreferredSize(new Dimension(600, 80));

        txtFilePath =  new JTextField(20);
		txtFilePath.setPreferredSize(new Dimension(100, 30));

		
		var chooser = new JButton("Browse...");
		chooser.addActionListener((e) -> {
			var fc = new JFileChooser(System.getProperty("user.dir"));
			var val = fc.showOpenDialog(win);
			if (val == JFileChooser.APPROVE_OPTION) {
				var file = fc.getSelectedFile().getAbsoluteFile();
				txtFilePath.setText(file.getAbsolutePath());
			}
		});
		
		var btnEncodeFile = new JButton("Encode");
		btnEncodeFile.addActionListener(e -> {
			/*
			 * Start your encoding here, but put the logic in another class
			 */
			String path = txtFilePath.getText();
			File fileVar = new File(txtFilePath.getText());
			
			//resets the global String variable in BinaryTree.java
			tree.pubEncode = "";
			String displayTxt = "";
			
			//reads the selected file
			//breaks down the file into characters
			//encodes each character using the tree.search method
			try {
				FileReader fr = new FileReader(fileVar);
				BufferedReader br = new BufferedReader(fr);
				int i = 0;
				while((i = br.read()) != -1) {
					char c = (char) i;
					//node key
					int key = 0;
					
					key = assignKey(c, key, dot);
					//encoding
					tree.search(new Character("SEARCH FOR", key));
					
					/*Big-O run time for encoding is O(n log n)*/
					
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//Write out a message 10 times when the Encode button is clicked
			for (int i = 0; i < 10; i++) {
				appendText("ENCODING " + path + "\n");
			}
			
			//prints encode to GUI
			displayTxt = tree.encodeTxt(displayTxt);
			appendText(displayTxt);
		});
		
		var btnDecodeFile = new JButton("Decode");
		btnDecodeFile.addActionListener(e -> {
			/*
			 * Start your decoding here, but put the logic in another class
			 */
			//THIS IS NOT DECODING THE ENCODE ABOVE
			//THIS IS ONLY READING THE FILE AND PRINTING TO THE GUI
			String path = txtFilePath.getText(); //Call getText() to get the file name
			replaceText("Decoding....." + path);
			File fileVar = new File(txtFilePath.getText());
			
			String decodeTxt = "";
			
			try {
				FileReader fr = new FileReader(fileVar);
				BufferedReader br = new BufferedReader(fr);
				int i = 0;
				while((i = br.read()) != -1) {
					char c = (char) i;
					decodeTxt = decodeTxt + c;
					
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			appendText("\n" + decodeTxt);
		});
		
		//Add all the components to the panel and the panel to the window
        top.add(txtFilePath);
        top.add(chooser);
        top.add(btnEncodeFile);
        top.add(btnDecodeFile);
        win.getContentPane().add(top); //Add the panel to the window
        
        
        /*
         * The middle panel contains the coloured square and the text
         * area for displaying the outputted text. 
         */
        var middle = new JPanel(new FlowLayout(FlowLayout.LEADING));
        middle.setPreferredSize(new Dimension(600, 200));

        
        dot.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        dot.setBackground(getRandomColour());
        dot.setPreferredSize(new Dimension(140, 150));
        dot.addMouseListener(new MouseAdapter() { 
        	//Can't use a lambda against MouseAdapter because it is not a SAM
        	public void mousePressed( MouseEvent e ) {  
        		dot.setBackground(getRandomColour());
        	}
        });
        
        //Add the text area
		txtOutput.setLineWrap(true);
		txtOutput.setWrapStyleWord(true);
		txtOutput.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		
		var scroller = new JScrollPane(txtOutput);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(450, 150));
		scroller.setMaximumSize(new Dimension(450, 150));
		
		//Add all the components to the panel and the panel to the window
		middle.add(dot);
		middle.add(scroller);
		win.getContentPane().add(middle);
		
		
		/*
		 * The bottom panel contains the clear and quit buttons.
		 */
		var bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setPreferredSize(new java.awt.Dimension(500, 50));

        //Create and add Clear and Quit buttons
        var clear = new JButton("Clear");
        clear.addActionListener((e) -> txtOutput.setText(""));
        
        var quit = new JButton("Quit");
        quit.addActionListener((e) -> System.exit(0));
        
        //Add all the components to the panel and the panel to the window
        bottom.add(clear);
        bottom.add(quit);
        win.getContentPane().add(bottom);       
        
        
        /*
         * All done. Now show the configured Window.
         */
		win.setVisible(true);
	}
	
	private Color getRandomColour() {
		Colour c = colours[rand.nextInt(0, colours.length)];
		return Color.decode(c.hex() + "");
	}
	
	protected void replaceText(String text) {
		txtOutput.setText(text);
	}
	
	protected void appendText(String text) {
		txtOutput.setText(txtOutput.getText() + " " + text);
	}
	
	//binary tree
	protected void init(Character root){
		tree.insert(root);
		//ORGANIZING FROM LEFT TO RIGHT DESCENDING
		//ROW 1 (TOP)
		tree.insert(new Character("E", 50));
		tree.insert(new Character("T", 150));
		//ROW 2
		tree.insert(new Character("I", 25));
		tree.insert(new Character("A", 75));
		tree.insert(new Character("N", 125));
		tree.insert(new Character("M", 175));
		//ROW 3
		tree.insert(new Character("S", 10));
		tree.insert(new Character("U", 40));
		tree.insert(new Character("R", 60));
		tree.insert(new Character("W", 90));
		tree.insert(new Character("D", 110));
		tree.insert(new Character("K", 140));
		tree.insert(new Character("G", 160));
		tree.insert(new Character("O", 190));
		//ROW 4
		tree.insert(new Character("H", 5));
		tree.insert(new Character("V", 15));
		tree.insert(new Character("F", 35));
		tree.insert(new Character("U", 45));
		tree.insert(new Character("L", 55));
		tree.insert(new Character("A", 65));
		tree.insert(new Character("P", 85));
		tree.insert(new Character("J", 95));
		
		tree.insert(new Character("B", 105));
		tree.insert(new Character("X", 115));
		tree.insert(new Character("C", 135));
		tree.insert(new Character("Y", 145));
		tree.insert(new Character("Z", 155));
		tree.insert(new Character("Q", 165));
		tree.insert(new Character("Ö", 185));
		tree.insert(new Character("CH", 195));
		//ROW 5
		tree.insert(new Character("5", 1));
		tree.insert(new Character("4", 10));
		tree.insert(new Character("3", 20));
		tree.insert(new Character("2", 49));
		tree.insert(new Character("1", 99));
		tree.insert(new Character("6", 101));
		tree.insert(new Character("7", 160));
		tree.insert(new Character("8", 184));
		tree.insert(new Character("9", 191));
		tree.insert(new Character("0", 199));
	}
	
	
	protected int assignKey(char fileCharacter, int key, JPanel dot2){
		
		//assigns the node key and changes the colour of the colour box
		int keyNum = key;
		String characterString = String.valueOf(fileCharacter);
		
		switch(characterString.toUpperCase()) {
		//ROW 1
		case "E":
			keyNum = 50;
			dot2.setBackground(Color.CYAN);
			break;
		case "T":
			keyNum = 150;
			dot2.setBackground(Color.ORANGE);
			break;
		//ROW 2
		case "I":
			keyNum = 25;
			dot2.setBackground(Color.BLACK);
			break;
		case "A":
			keyNum = 75;
			dot2.setBackground(Color.RED);
			break;
		case "N":
			keyNum = 125;
			dot2.setBackground(Color.CYAN);
			break;
		case "M":
			keyNum = 175;
			dot2.setBackground(Color.GREEN);
			break;
		//ROW 3
		case "S":
			keyNum = 10;
			dot2.setBackground(Color.RED);
			break;
		case "U":
			keyNum = 40;
			dot2.setBackground(Color.YELLOW);
			break;
		case "R":
			keyNum = 60;
			dot2.setBackground(Color.BLACK);
			break;
		case "W":
			keyNum = 90;
			dot2.setBackground(Color.CYAN);
			break;
		case "D":
			keyNum = 110;
			dot2.setBackground(Color.GREEN);
			break;
		case "K":
			keyNum = 140;
			dot2.setBackground(Color.ORANGE);
			break;
		case "G":
			keyNum = 160;
			dot2.setBackground(Color.MAGENTA);
			break;
		case "O":
			keyNum = 190;
			dot2.setBackground(Color.BLUE);
			break;
		//ROW 4
		case "H":
			keyNum = 5;
			dot2.setBackground(Color.GRAY);
			break;
		case "V":
			keyNum = 15;
			dot2.setBackground(Color.GREEN);
			break;
		case "F":
			keyNum = 35;
			dot2.setBackground(Color.BLUE);
			break;
		/*case "U":
			keyNum = 45;
			dot2.setBackground(Color.YELLOW);
			break;*/
		case "L":
			keyNum = 55;
			dot2.setBackground(Color.YELLOW);
			break;
		/*case "A":
			keyNum = 65;
			dot2.setBackground(Color.RED);
			break;*/
		case "P":
			keyNum = 85;
			dot2.setBackground(Color.MAGENTA);
			break;
		case "J":
			keyNum = 95;
			dot2.setBackground(Color.RED);
			break;
			
		case "B":
			keyNum = 105;
			dot2.setBackground(Color.ORANGE);
			break;
		case "X":
			keyNum = 115;
			dot2.setBackground(Color.BLUE);
			break;
		case "C":
			keyNum = 135;
			dot2.setBackground(Color.YELLOW);
			break;
		case "Y":
			keyNum = 145;
			dot2.setBackground(Color.MAGENTA);
			break;
		case "Z":
			keyNum = 155;
			dot2.setBackground(Color.GRAY);
			break;
		case "Q":
			keyNum = 165;
			dot2.setBackground(Color.GRAY);
			break;
		case "Ö":
			keyNum = 185;
			dot2.setBackground(Color.PINK);
			break;
		case "CH":
			keyNum = 195;
			dot2.setBackground(Color.PINK);
			break;
		//ROW 5
		case "5":
			keyNum = 1;
			dot2.setBackground(Color.WHITE);
			break;
		case "4":
			keyNum = 10;
			dot2.setBackground(Color.WHITE);
			break;
		case "3":
			keyNum = 20;
			dot2.setBackground(Color.WHITE);
			break;
		case "2":
			keyNum = 49;
			dot2.setBackground(Color.WHITE);
			break;
		case "1":
			keyNum = 99;
			dot2.setBackground(Color.WHITE);
			break;
		case "6":
			keyNum = 101;
			dot2.setBackground(Color.WHITE);
			break;
		case "7":
			keyNum = 160;
			dot2.setBackground(Color.WHITE);
			break;
		case "8":
			keyNum = 184;
			dot2.setBackground(Color.WHITE);
			break;
		case "9":
			keyNum = 191;
			dot2.setBackground(Color.WHITE);
			break;
		case "0":
			keyNum = 199;
			dot2.setBackground(Color.WHITE);
			break;
		//unknown characters become spaces
		default:
			keyNum = 100;
			dot2.setBackground(Color.LIGHT_GRAY);
			break;
		}
		return keyNum;
	}
}