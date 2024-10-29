package Backtracking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class Backtracking {
    static int se=15;
	public static int M= 20;
	private static JLabel[][] jLabel = new JLabel[M][M];
	JSlider points = new JSlider(0, 30,   se);
	Hashtable<Integer, JLabel> labelTable  = new Hashtable<Integer, JLabel>();
	
	public static ImageIcon img = SolverUtils.imgo;
	
	private static int board[][] = new int[M][M];
	JPanel Q;JToolBar tb;
	private static JButton trst, exc, rest, trt;
	acion m = new acion();
	aion  c = new aion();
	acton l = new acton();
	static Thread t2;
	
	
    //makes sure that the queen in board[row][col] is safe
	static boolean isSafe(int row, int col) {

		try {
			Thread.sleep(se);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < col; ++i)
			if (board[row][i] == 1)
				return false;

		for (int i = row, j = col; i >= 0 && j >= 0; --i, --j) {

			if (board[i][j] == 1)
				return false;

		}

		for (int i = row, j = col; i < M && j >= 0; ++i, --j) {

			if (board[i][j] == 1)
				return false;
		}

		return true;
	}

	static boolean findSolution(int col) {

		if (col >= M)
			return true;
		if (col==0) {
	    Random rand = new Random(); 
		int G = rand.nextInt(M); 
			try {
				Thread.sleep(se);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isSafe(G, col) == true) {
				board[G][col] = 1;
				jLabel[G][col].setBackground(Color.ORANGE);
				jLabel[G][col].setIcon(img);
				if (findSolution(col + 1) == true)
					{return true;}
				
				board[G][col] = 0;
				if ((G % 2) == (col % 2)) {
					jLabel[G][col].setBackground(Color.WHITE);
					jLabel[G][col].setText("(" + G + "," + col + ")");
					jLabel[G][col].setForeground (Color.BLACK);
					jLabel[G][col].setIcon(null);}
					else {
						jLabel[G][col].setBackground(Color.BLACK);
						jLabel[G][col].setText("(" + G + "," + col + ")");
						jLabel[G][col].setForeground (Color.WHITE);
						jLabel[G][col].setIcon(null);	
					}

			}

		}
		else {
			for (int G = 0; G < M; ++G) {
				try {
					Thread.sleep(se);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (isSafe(G, col) == true) {
					board[G][col] = 1;
					jLabel[G][col].setBackground(Color.ORANGE);
					jLabel[G][col].setIcon(img);
					if (findSolution(col + 1) == true)
						{return true;}
					
					board[G][col] = 0;
					if ((G % 2) == (col % 2)) {
						jLabel[G][col].setBackground(Color.WHITE);
						jLabel[G][col].setText("(" + G + "," + col + ")");
						jLabel[G][col].setForeground (Color.BLACK);
						jLabel[G][col].setIcon(null);}
						else {
							jLabel[G][col].setBackground(Color.BLACK);
							jLabel[G][col].setText("(" + G + "," + col + ")");
							jLabel[G][col].setForeground (Color.WHITE);
							jLabel[G][col].setIcon(null);	
						}

				}

			}
					
		}

		return false;

	}
	
    static boolean findSSolution(int col) {

			if (col >= M)
				return true;
			
				int[] MM=SolverUtils.generateRandomState(M);
				/*for(int f=0;f<num_queens;f++) {
					System.out.println(delay_value[f]);
				
				}	System.out.println("End.");*/
			int i = 0;
			for (int G = MM[i]; i < M; ++i) {
				try {
					Thread.sleep(se);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (isSafe(G, col) == true) {
					board[G][col] = 1;
					jLabel[G][col].setBackground(Color.ORANGE);
					jLabel[G][col].setIcon(img);
					if (findSSolution(col + 1) == true)
						{return true;}
					
					board[G][col] = 0;
					if ((G % 2) == (col % 2)) {
						jLabel[G][col].setBackground(Color.WHITE);
						jLabel[G][col].setText("(" + G + "," + col + ")");
						jLabel[G][col].setForeground (Color.BLACK);
						jLabel[G][col].setIcon(null);}
						else {
							jLabel[G][col].setBackground(Color.BLACK);
							jLabel[G][col].setText("(" + G + "," + col + ")");
							jLabel[G][col].setForeground (Color.WHITE);
							jLabel[G][col].setIcon(null);	
						}

				}
			}

			return false;

		}
    
    
    static void  solveNQueen(int y) {
			if(y==0) { double startTime = System.nanoTime();
			if (findSolution(0) == false) {
				JOptionPane.showMessageDialog(null, "No Solution.");
				//s = "";
				rest.setEnabled(true);
			}

			else {
				//printSolution();
					double  endTime = System.nanoTime();
					double totalTime = (endTime - startTime) / 1000000000;
					String ss= String.format("Time elapsed: %.3f s", totalTime);
				JOptionPane.showMessageDialog(null, "Solved!" + "\n" +ss);
				rest.setEnabled(true);
				
				//s = "";
				// System.exit(0);
			}}
			else {
				double startTime = System.nanoTime();
				if (findSSolution(0) == false) {
					JOptionPane.showMessageDialog(null, "No Solution.");
					//s = "";
					rest.setEnabled(true);
				}

				else {
					//printSolution();
						double  endTime = System.nanoTime();
						double totalTime = (endTime - startTime) / 1000000000;
						String ss= String.format("Time elapsed: %.3f s", totalTime);
					JOptionPane.showMessageDialog(null, "Solved!" + "\n" +ss);
					rest.setEnabled(true);
					
					//s = "";
					// System.exit(0);
				}
			}
		}
	 
	Backtracking() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		int w = 800, h = 770;
		// if (num_queens>9) {h+=(num_queens-2)*15;}
		labelTable.put(new Integer(0), new JLabel("Fast"));
		labelTable.put(new Integer(15), new JLabel("Average"));
		labelTable.put(new Integer(30), new JLabel("Slow"));
		points.setLabelTable(labelTable);
		points.setPaintLabels(true);
		points.setMajorTickSpacing(5);
		points.setMinorTickSpacing(1);
		points.setPaintTicks(true);
		points.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				se = points.getValue();
			}
		});
		points.setPreferredSize(new Dimension(50, 40));
		trt = new JButton("Start");
		rest = new JButton("Reset");
		trst = new JButton("Modified");
		exc = new JButton("Stop");
		trt.addActionListener(m);
		rest.addActionListener(l);
		trst.addActionListener(c);
		tb = new JToolBar();
		tb.setFloatable(true);
		exc.setBackground(new Color(192, 20, 20));
		exc.setForeground(Color.WHITE);
		exc.setFocusPainted(false);
		exc.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbe = new GridBagConstraints();
		tb.add(trt);
		tb.add(exc);
		tb.add(rest);
		tb.add(trst);
		tb.add(points);
		JFrame jFrame = new JFrame("Backtracking Solver");
		jFrame.setIconImage(img.getImage());
		Q = new JPanel(new GridLayout(M, M));
		jFrame.setSize(h, w);
		jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		//exc.setEnabled(false);
		exc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				// jFrame.dispose();
				t2.stop();
				rest.setEnabled(true);
			}
		});
		trt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				// jFrame.dispose();
				rest.setEnabled(false);
				exc.setEnabled(true);
			}
		});
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
		// buttons_panel.add(tb, BorderLayout.NORTH);
		Q.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		jFrame.add(Q, BorderLayout.CENTER);
		jFrame.add(tb, BorderLayout.NORTH);
		jFrame.setVisible(true);

	}
	static void  reset() {
		try {
			Thread.sleep(se);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < M; ++j) {
				board[i][j] = 0;
				if ((i % 2) == (j % 2)) {
				jLabel[i][j].setBackground(Color.WHITE);
				jLabel[i][j].setIcon(null);
				jLabel[i][j].setText("");}
				else {
					jLabel[i][j].setBackground(Color.BLACK);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				}
			}
		}
	}
	
	public static  class action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						new Backtracking();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t1.start();
		}
	}

	public static class acion implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			 t2 = new Thread(new Runnable() {
				@Override
				public void run() {
					solveNQueen(0);
				}
			});
			t2.start();
		}
	}
	public static class aion implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			 t2 = new Thread(new Runnable() {
				@Override
				public void run() {
					solveNQueen(1);
				}
			});
			t2.start();
		}
	}
	
	public static class acton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Thread re = new Thread(new Runnable() {
				@Override
				public void run() {
					reset();
					
				}
			});
			re.start();
		}
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Backtracking();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
