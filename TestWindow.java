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
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import javax.swing.JLabel;

public class TestWindow extends JFrame {
	private JTextField textField;
	  public static Image img;
	  
	public TestWindow() {
		getContentPane().setBackground(new Color(192, 192, 192));
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Barter");
		btnNewButton.setBounds(1232, 576, 182, 43);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Property Management");
		btnNewButton_1.setBounds(1232, 629, 182, 48);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Roll");
		btnNewButton_2.setBounds(1232, 687, 182, 43);
		getContentPane().add(btnNewButton_2);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(766, 8, 427, 629);
		getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(766, 656, 427, 70);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(1232, 8, 182, 543);
		getContentPane().add(textArea_1);
		
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\barba\\workspace\\WIndowTest\\src\\Window\\Monopoly700.jpg"));
		lblNewLabel.setBounds(10, 10, 715, 715);
		getContentPane().add(lblNewLabel);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
		TestWindow x = new TestWindow();
	}
}
