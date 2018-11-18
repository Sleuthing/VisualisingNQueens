package Backtracking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Backtracking.HillClimbing.acn;

import GeneticAlgorithm.GUI;
import GeneticAlgorithm.Main;

public class HillClimbing {
	static int M = 20;
	static int LM = 1000;
	static int MM = 25;
	static JPanel Q, P;
	static String s;
	static JTextField QN = new JTextField();
	private static JLabel[][] jLabel;
	static Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
	public static ImageIcon img = SolverUtils.imgo;
	private static JButton exc, rest, trt;
	static JSlider points = new JSlider(0, 50, MM);
	static JLabel dc = new JLabel("Number of iterations: ");
	static JFrame jFrame;
	static Thread fch;

	// make a move for hill climbing
	public static void constructor() throws IOException {
		jFrame = new JFrame("HillClimbing Solver");
		jFrame.setIconImage(img.getImage());
		jLabel = new JLabel[M][M];
		P = new JPanel(new GridBagLayout());
		Q = new JPanel(new GridLayout(M, M));
		acn ao = new acn();
		ntio re = new ntio();
		QN.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (Integer.parseInt(QN.getText()) < 2000000000) {
						LM = Integer.parseInt(QN.getText());
					} else {
						JOptionPane.showMessageDialog(null, "Maximum number of iterations is 2 million!");
						QN.setText(String.valueOf(2000000000));
						LM = 2000000000;
					}
				}
			}
		});
		QN.setColumns(8);
		QN.setText(String.valueOf(LM));
		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < M; ++j) {
				jLabel[i][j] = new JLabel("");
				jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				jLabel[i][j].setSize(50, 50);
				jLabel[i][j].setOpaque(true);
				if ((i % 2) == (j % 2)) {
					jLabel[i][j].setBackground(Color.WHITE);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				} else {
					jLabel[i][j].setBackground(Color.BLACK);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				}
				Q.add(jLabel[i][j]);
			}
		}
		labelTable.put(new Integer(0), new JLabel("Fast"));
		labelTable.put(new Integer(25), new JLabel("Average"));
		labelTable.put(new Integer(50), new JLabel("Slow"));
		points.setLabelTable(labelTable);
		points.setPaintLabels(true);
		points.setMajorTickSpacing(5);
		points.setMinorTickSpacing(1);
		points.setPaintTicks(true);
		points.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				MM = points.getValue();
			}
		});
		points.setPreferredSize(new Dimension(250, 40));
		trt = new JButton("Start");
		rest = new JButton("Reset");
		exc = new JButton("Stop");
		exc.setBackground(new Color(192, 20, 20));
		exc.setForeground(Color.WHITE);
		exc.setFocusPainted(false);
		exc.setFont(new Font("Tahoma", Font.BOLD, 12));
		trt.addActionListener(ao);
		rest.addActionListener(re);
		trt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				rest.setEnabled(false);
			}
		});
		exc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				fch.stop();
				rest.setEnabled(true);
			}
		});
		GridBagConstraints gbe = new GridBagConstraints();
		gbe.insets = new Insets(0, 0, 0, 5);
		gbe.gridx = -2;
		gbe.gridy = 0;
		P.add(exc, gbe);
		gbe.insets = new Insets(0, 0, 0, 5);
		gbe.gridx = 0;
		gbe.gridy = 0;
		P.add(trt, gbe);
		gbe.insets = new Insets(0, 0, 0, 5);
		gbe.gridx = 2;
		gbe.gridy = 0;
		P.add(rest, gbe);
		gbe.insets = new Insets(0, 0, 0, 5);
		gbe.gridx = 4;
		gbe.gridy = 0;
		P.add(points, gbe);
		gbe.insets = new Insets(0, 0, 0, 5);
		gbe.gridx = 8;
		gbe.gridy = 0;
		P.add(dc, gbe);
		gbe.gridx = 10;
		gbe.gridy = 0;
		P.add(QN, gbe);
		jFrame.setSize(800, 770);
		jFrame.setDefaultCloseOperation(jFrame.DISPOSE_ON_CLOSE);
		Q.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		jFrame.add(Q, BorderLayout.CENTER);
		jFrame.add(P, BorderLayout.SOUTH);
		jFrame.setVisible(true);
	}

	public static void firstChoiceHillClimbing(int maxNumOfIterations) {
		double startTime = System.nanoTime();
		int[] r = SolverUtils.generateRandomState(M);
		int costToBeat = SolverUtils.getHeuristicCost(r);
		int y = 0;
		for (int j = 0; j < M; j++) {
			for (int i = 0; i < M; i++) {
				if (r[y] == i) {
					jLabel[i][j].setBackground(Color.ORANGE);
					jLabel[i][j].setIcon(img);
					y++;
					break;
				}
			}

		}
		// terminate when it reaches max num of iterations or problem is solved.
		for (int x = 0; x <= maxNumOfIterations && costToBeat > 0; x++) {
			boolean flag = true;
			int tempCostToBeat = costToBeat;
			for (int col = 0; col < M && flag; col++) {
				for (int row = 0; row < M; row++) {
					// we do not need to evaluate because we already know current cost by
					// costToBeat.
					if (row == r[col])
						continue;
					// init new copy
					int[] rc = Arrays.copyOf(r, M);
					rc[col] = row;
					int cost = SolverUtils.getHeuristicCost(rc);
					if (costToBeat > cost) {
						r[col] = row;
						costToBeat = cost;
						flag = false;
						y = 0;
						for (int j = 0; j < M; j++) {
							for (int i = 0; i < M; i++) {
								try {
									Thread.sleep(MM);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								if (r[y] == i) {
									jLabel[i][j].setBackground(Color.RED);
									jLabel[i][j].setIcon(img);
									y++;
									break;
								}
							}
						}

						break;

					}

				}
			}

			// if it gets stuck at local maxima
			if (tempCostToBeat == costToBeat)
				r = SolverUtils.generateRandomState(M);

			y = 0;
			reset();

			for (int j = 0; j < M; j++) {
				for (int i = 0; i < M; i++) {
					if (r[y] == i) {
						jLabel[i][j].setBackground(Color.ORANGE);
						jLabel[i][j].setIcon(img);
						y++;
						break;
					}
				}
			}
			jFrame.setTitle("HillClimbing Solver, iteration: " + x + " Cost to beat: " + costToBeat);

		}
		if (costToBeat == 0) {
			double endTime = System.nanoTime();
			double totalTime = (endTime - startTime) / 1000000000;
			String ss = String.format("Time elapsed: %.3f s", totalTime);
			JOptionPane.showMessageDialog(null, "Finished!" + "\n" + ss);
		} else {
			double endTime = System.nanoTime();
			double totalTime = (endTime - startTime) / 1000000000;
			String ss = String.format("Time elapsed: %.3f s", totalTime);
			JOptionPane.showMessageDialog(null, "Failed, reached iteration limit!" + "\n" + ss);
		}
		rest.setEnabled(true);
	}

	public static void reset() {
		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < M; ++j) {
				try {
					Thread.sleep(MM);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if ((i % 2) == (j % 2)) {
					jLabel[i][j].setBackground(Color.WHITE);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				} else {
					jLabel[i][j].setBackground(Color.BLACK);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				}
			}
		}
	}

	public static void firstChoiceHillClimbingPara(int maxNumOfIterations) {
		/*
		 * long startTime = System.nanoTime();
		 * 
		 * ExecutorService exec =
		 * Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); try
		 * { for (final Object o : list) { exec.submit(new Runnable() {
		 * 
		 * @Override public void run() { // do stuff with o. } }); } } finally {
		 * 
		 * exec.shutdown(); }
		 * 
		 * 
		 */

		int[] r = SolverUtils.generateRandomState(M);
		int costToBeat = SolverUtils.getHeuristicCost(r);
		int y = 0;
		for (int j = 0; j < M; j++) {
			for (int i = 0; i < M; i++) {
				if (r[y] == i) {
					jLabel[i][j].setBackground(Color.ORANGE);
					jLabel[i][j].setIcon(img);
					y++;
					break;
				}
			}

		}
		// terminate when it reaches max num of iterations or problem is solved.
		for (int x = 0; x <= maxNumOfIterations && costToBeat > 0; x++) {

			boolean flag = true;
			int tempCostToBeat = costToBeat;
			for (int col = 0; col < M && flag; col++) {
				for (int row = 0; row < M; row++) {
					// we do not need to evaluate because we already know current cost by
					// costToBeat.
					if (row == r[col])
						continue;
					// init new copy
					int[] rc = Arrays.copyOf(r, M);
					rc[col] = row;
					int cost = SolverUtils.getHeuristicCost(rc);
					if (costToBeat > cost) {
						// reset(row, col,n);
						r[col] = row;
						costToBeat = cost;
						flag = false;
						y = 0;
						for (int j = 0; j < M; j++) {
							for (int i = 0; i < M; i++) {
								System.out.println("moving");
								try {
									Thread.sleep(MM);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								if (r[y] == i) {
									jLabel[i][j].setBackground(Color.RED);
									jLabel[i][j].setIcon(img);
									y++;
									break;
								}
							}

						}

						break;

					}

				}
			}

			// if it gets stuck at local maxima
			if (tempCostToBeat == costToBeat)
				r = SolverUtils.generateRandomState(M);

			y = 0;
			reset();

			for (int j = 0; j < M; j++) {
				for (int i = 0; i < M; i++) {
					if (r[y] == i) {
						jLabel[i][j].setBackground(Color.ORANGE);
						jLabel[i][j].setIcon(img);
						y++;
						break;
					}
				}
			}
			jFrame.setTitle("HillClimbing Solver, iteration: " + x);

		}

		// return solution if solved
	}

	public static class acon implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Thread cn = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						constructor();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			cn.start();

		}
	}

	public static class acn implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fch = new Thread(new Runnable() {
				@Override
				public void run() {
					firstChoiceHillClimbing(LM);
				}
			});
			fch.start();

		}
	}

	public static class ntio implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Thread reh = new Thread(new Runnable() {
				@Override
				public void run() {
					reset();
				}
			});
			reh.start();

		}
	}

	public static void main(String[] args) throws IOException {
		constructor();
		firstChoiceHillClimbing(500000);
	}
}