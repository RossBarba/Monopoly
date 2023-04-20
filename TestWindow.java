package Window;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Canvas;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Button;
import java.awt.Color;
import java.awt.Image;

public class TestWindow extends JFrame {
	private JTextField textField;
	  public static Image img;
	  
	public TestWindow() {
		getContentPane().setBackground(new Color(192, 192, 192));
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Barter");
		btnNewButton.setBounds(524, 380, 111, 21);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Property Management");
		btnNewButton_1.setBounds(524, 413, 111, 21);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Roll");
		btnNewButton_2.setBounds(524, 444, 111, 21);
		getContentPane().add(btnNewButton_2);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 378, 490, 56);
		getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(10, 445, 490, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(524, 10, 111, 360);
		getContentPane().add(textArea_1);
		
		Gpanel Board = new Gpanel();
		Board.setBounds(10, 10, 490, 360);
		getContentPane().add(Board);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	class Gpanel extends JPanel {
	
		public void paintComponent(Graphics g) {
			super.paintComponents(g);

			try {
		        img = ImageIO.read(new File("C:/Users/barba/Downloads/OrigB.gif"));
		    } catch (IOException e) {
		        System.out.println("no image");
		    }
		    
		    
	}
	
	}
	public static void main(String[] args) {
		
		TestWindow x = new TestWindow();
	}
}
