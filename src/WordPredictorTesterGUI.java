import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
 
class WordPredictorTesterGUI extends JFrame implements KeyListener { 

	private static final long serialVersionUID = 1L;
	
	JTextArea output= new JTextArea();
	JTextArea input = new JTextArea();
	String partialWord = "";
	boolean inWord = false;
	String current = "";
	String temp = "";
	ArrayList<String> popular;
   Trie tr = new Trie();
   
	public WordPredictorTesterGUI() {
		JFrame frame = new JFrame("Predictive Application");   
      frame.setSize(640,640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(2,1));
		
		JPanel inputPanel = new JPanel();
		JPanel outPanel = new JPanel();
		
		outPanel.setBackground(Color.LIGHT_GRAY);
		inputPanel.setLayout(new GridLayout(1,1));
		outPanel.setLayout(new GridLayout(1,1));
		
		outPanel.add(output);
		inputPanel.add(input);
		output.setEditable(true);
		output.addKeyListener(this);
		input.setEditable(false);
		input.addKeyListener(this);
		input.setLineWrap(true);
		output.setLineWrap(true);
		//
		//change the font and the color in the input textArea
		Font font = new Font("Verdana", Font.BOLD, 16);
		input.setFont(font);
		input.setForeground(Color.BLUE);	

		frame.add(outPanel);
		frame.add(inputPanel);
		frame.setVisible(true);
		partialWord = "";

}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(! inWord) {
			inWord = true;
		}	
		
		int  keyCode = e.getKeyCode();
		char ch      = e.getKeyChar();
		int  index   = parseKeyCode(keyCode);
		
		if ( index < 0 ) {
			output.setEditable(true);

			if ( Character.isAlphabetic(ch) && inWord ) {
				partialWord += ch;
            if(!tr.hasDictionary())
            {
               try {
                  long then = System.currentTimeMillis();
                  tr.insertDictionary("dictionary.txt");
                  long now = System.currentTimeMillis();
                  double elapsed = (Double) ((now - then) / 1000.0);
                  System.out.printf("Elapsed time: %f\n", elapsed);
               }
         		catch(IOException f) {
                  System.out.println("Failed to read dictionary");
                  System.exit(0);
               }
            }
            popular = new ArrayList<String>(tr.findWord(partialWord));
            
            if(popular.size() > 9) {
               popular = new ArrayList<String>(popular.subList(0, 9));
            }
            
            if(popular.size() > 0)
               input.setText(arrtoString(popular) );
            
            else {
               popular = null;
               input.setText("");
            }
               
				System.out.println("Current Prefix:\"" + partialWord + "\"");
			}
		}
		else if( index >= 0 && index <= 9 ){
			output.setEditable(false);
			if(popular != null)
				current += popular.get(index) + " ";
			output.setText(current);
			inWord = false;
			partialWord = "";
		}
		else if( index == 10 || index == 11) {
			output.setEditable(false);
			current = current.substring(0, current.length() - 1);
			if(index == 10) //comma
				current += ", ";
			else
				current += ". ";
			output.setText(current);
		}
	}
	
	private int parseKeyCode(int code) {
		int index = 0;
		switch(code) {
		case KeyEvent.VK_ENTER :
		case KeyEvent.VK_SPACE :
			index = 0;
			break;
		case KeyEvent.VK_1 :
			index = 1;
			break;
		case KeyEvent.VK_2 :
			index = 2;
			break;	
		case KeyEvent.VK_3 :
			index = 3;
			break;
		case KeyEvent.VK_4 :
			index = 4;
			break;
		case KeyEvent.VK_5 :
			index = 5;
			break;
		case KeyEvent.VK_6 :
			index = 6;
			break;
		case KeyEvent.VK_7 :
			index = 7;
			break;
		case KeyEvent.VK_8 :
			index = 8;
			break;
		case KeyEvent.VK_9 :
			index = 9;
			break;
		case KeyEvent.VK_COMMA :
			index = 10;
			break;
		case KeyEvent.VK_PERIOD :
			index = 11;
			break;
		default:
			index = -1;	
		}		
		return index;
			
	}
	
	private String arrtoString(ArrayList<String> a) {
		String ret = "";
		if(a == null)
			return ret;
		ret += "-->" + a.get(0) + "  ";
		for(int i = 1; i < a.size(); i ++) {
			ret += i + ":" + a.get(i) + "  ";
			if (i == 4)
				ret += "\n        ";
		}
		return ret;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static String arrToString(WordItem d[]) {
		String ret = "";
		for(int i=0; i < d.length; i ++){
			ret += i + ":" + d[i] + "\n";
		}
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		
      System.out.println("Initializing .....");
		new WordPredictorTesterGUI();
		System.out.println("Done Intialization and Ready to type in!");
	}
}
