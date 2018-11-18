package GeneticAlgorithm;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Backtracking.Backtracking;
import Backtracking.Start;
import Backtracking.Backtracking.action;
import GeneticAlgorithm.Main;
import GeneticAlgorithm.Main.aktion;

public class GUI {
	static JButton load,exc;
	static boolean T=false;
	public static JFrame jFrame;
	static JTextField QN = new JTextField();
	static JTextField QZ = new JTextField();
	static JTextField QX = new JTextField();
	static JTextField QC = new JTextField();
	JLabel dn = new JLabel("Population size:");
	JLabel dz = new JLabel("Mutation rate:");
	JLabel dx = new JLabel("Elite chromosome:");
	JLabel dc = new JLabel("Tournament size:");
	JLabel dv = new JLabel("Delay:");
	JSlider points = new JSlider(0, 400,  Main.SP);
	Hashtable<Integer, JLabel> labelTable  = new Hashtable<Integer, JLabel>();
	public GUI() {

		aktion bc = new aktion();
		labelTable.put(new Integer(0), new JLabel("Fast"));
		labelTable.put(new Integer(200), new JLabel("Average"));
		labelTable.put(new Integer(400), new JLabel("Slow"));
		points.setLabelTable(labelTable);
		points.setPaintLabels(true);
		points.setMajorTickSpacing(5);
		points.setMinorTickSpacing(1);
		points.setPaintTicks(true);
		points.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				Main.SP = points.getValue();
			}
		});
		points.setPreferredSize(new Dimension(200,40));
		QN.setText(String.valueOf(Main.M*1000));
		QZ.setText(String.valueOf(GeneticAlgo.MUTATE_RATE));
		QX.setText(String.valueOf(GeneticAlgo.ELITE_CHROMOSOME));
		QC.setText(String.valueOf(GeneticAlgo.TOURNAMENT_SELECTION_SIZE));
		QN.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Main.PoP = Integer.parseInt(QN.getText());
				}
			}
		});
		QZ.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					GeneticAlgo.MUTATE_RATE = Double.parseDouble(QZ.getText());
				}
			}
		});
		QX.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					GeneticAlgo.ELITE_CHROMOSOME = Integer.parseInt(QX.getText());
				}
			}
		});
		QC.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					GeneticAlgo.TOURNAMENT_SELECTION_SIZE = Integer.parseInt(QC.getText());
				}
			}
		});
		jFrame = new JFrame("GeneticAlg Intializer, "+Main.M+"-Queens");
		jFrame.setIconImage(Backtracking.img.getImage());
		jFrame.setLayout(new GridBagLayout());
		jFrame.setSize(500, 500);
		jFrame.setLocation(450, 0);
		load = new JButton(" Go! ");
		exc = new JButton("Stop");
		exc.setEnabled(false);
		load.addActionListener(bc);
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				exc.setEnabled(true);
				QN.setEnabled(false);
				QZ.setEnabled(false);
				QX.setEnabled(false);
				QC.setEnabled(false);
				
			}
		});
		exc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				Main.m.stop();
				QN.setEnabled(true);
				QZ.setEnabled(true);
				QX.setEnabled(true);
				QC.setEnabled(true);
			}
		});
		QN.setColumns(4);
		QZ.setColumns(4);
		QX.setColumns(4);
		QC.setColumns(4);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 2;
		gbc.gridy = 4;	
		jFrame.add(QZ, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 4;	
		jFrame.add(dz, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 2;
		gbc.gridy = 2;	
		jFrame.add(QN, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		jFrame.add(dn, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 2;
		gbc.gridy = -4;
		jFrame.add(QX, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = -4;
		jFrame.add(dx, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 2;
		gbc.gridy = -2;
		jFrame.add(QC, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = -2;	
		jFrame.add(dc, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 2;	
		gbc.gridy =0;   	
		jFrame.add(points, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;	
		jFrame.add(dv, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 10;
		jFrame.add(exc, gbc);
		gbc.insets = new Insets(5, 0, 0, 5);
		gbc.gridx = -2;
		gbc.gridy = 10;
		jFrame.add(load, gbc);
		jFrame.setVisible(true);
	}

	public static void main(String args[]) {

		new GUI();

	}
}