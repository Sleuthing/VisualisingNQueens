package Backtracking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import Backtracking.Backtracking.action;
import Backtracking.HillClimbing;

import Backtracking.HillClimbing.acon;
import GeneticAlgorithm.*;

public class Start {
	static JButton exc, strt, gen, hc;
	action ac = new action();
	acon ap = new acon();
	JTextField QN = new JTextField();
	JTextArea QQ = new JTextArea(" Number of queens: ");
	JPanel J;
	JLabel q = new JLabel();
	public static ImageIcon img = new ImageIcon("Imgs\\sf.png");

	Start() throws IOException {
		q.setPreferredSize(new Dimension(90, 90));
		q.setIcon(img);
		QN.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (Integer.parseInt(QN.getText()) <= 20 && Integer.parseInt(QN.getText()) >= 4) {
						Backtracking.M = Integer.parseInt(QN.getText());
						Main.M = (Integer.parseInt(QN.getText()));
						HillClimbing.M = Integer.parseInt(QN.getText());
						if (GUI.jFrame != null) {
							GUI.jFrame.dispose();
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (Main.jFrame != null)
								Main.jFrame.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Sorry, number of allowed queens is between 4-20!");
						QN.setText(String.valueOf(Backtracking.M));
					}

				}
			}
		});
		JFrame jFrame = new JFrame("NQueen Solver");
		J = new JPanel();

		jFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Imgs\\white.png")))));
		jFrame.setIconImage(Backtracking.img.getImage());
		jFrame.setLayout(new GridBagLayout());
		jFrame.setSize(500, 500);
		jFrame.setLocation(450, 0);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		strt = new JButton("Backtracking");
		exc = new JButton("Exit");
		gen = new JButton("Genetic");
		hc = new JButton("Hillclimbing");
		exc.setBackground(new Color(192, 20, 20));
		exc.setPreferredSize(new Dimension(70, 40));
		exc.setForeground(Color.WHITE);
		exc.setFocusPainted(false);
		exc.setFont(new Font("Tahoma", Font.BOLD, 16));
		strt.setBackground(new Color(57, 107, 189));
		strt.setPreferredSize(new Dimension(130, 40));
		strt.setForeground(Color.WHITE);
		strt.setFocusPainted(false);
		strt.setFont(new Font("Serif", Font.BOLD, 16));
		gen.setBackground(new Color(57, 107, 189));
		gen.setPreferredSize(new Dimension(120, 40));
		gen.setForeground(Color.WHITE);
		gen.setFocusPainted(false);
		gen.setFont(new Font("Serif", Font.BOLD, 16));
		hc.setBackground(new Color(57, 107, 189));
		hc.setPreferredSize(new Dimension(120, 40));
		hc.setForeground(Color.WHITE);
		hc.setFocusPainted(false);
		hc.setFont(new Font("Serif", Font.BOLD, 16));
		QN.setColumns(5);
		QN.setText(String.valueOf(Backtracking.M));
		QN.setPreferredSize(new Dimension(80, 30));

		strt.addActionListener(ac);
		hc.addActionListener(ap);
		QQ.setBackground(Color.WHITE);
		QQ.setFont(new Font("Serif", Font.BOLD, 14));
		exc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				System.exit(0);
			}
		});
		gen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				new GUI();
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		int X = 3, Y = 3, Z = 3, W = 3;
		gbc.insets = new Insets(X, Y, Z, W);
		gbc.gridx = -1;
		gbc.gridy = 0;
		jFrame.add(q, gbc);
		gbc.insets = new Insets(X, Y, Z, W);
		gbc.gridx = -2;
		gbc.gridy = 0;
		jFrame.add(QN, gbc);
		gbc.insets = new Insets(X, Y, Z, W);
		gbc.gridx = -1;
		gbc.gridy = 1;
		jFrame.add(hc, gbc);
		gbc.insets = new Insets(X, Y, Z, W);
		gbc.gridx = -2;
		gbc.gridy = 1;
		jFrame.add(strt, gbc);
		gbc.insets = new Insets(X, Y, Z, W);
		gbc.gridx = -3;
		gbc.gridy = 1;
		jFrame.add(gen, gbc);
		gbc.insets = new Insets(X, Y, Z, W);
		gbc.gridx = -3;
		gbc.gridy = 3;
		jFrame.add(exc, gbc);
		jFrame.setVisible(true);
	}

	public static void main(String args[]) throws IOException {
		new Start();
	}

}