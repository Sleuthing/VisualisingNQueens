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
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

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

public class HillClimbing {
	static int num_queens = 20;
	static int max_iterations = 1000;
	static int delay_value = 25;
	static JPanel board_panel, buttons_panel;
	//static String s;
	static JTextField max_iterations_txt_fld = new JTextField();
	private static JLabel[][] jLabel;
	static Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
	public static ImageIcon img = SolverUtils.imgo;
	private static JButton exit_btn, reset_btn, start_btn;
	static JSlider points = new JSlider(0, 50, delay_value);
	static JLabel delay_label = new JLabel("Number of iterations: ");
	static JFrame jFrame;
	static Thread thread;

	// make a move for hill climbing
	public static void constructor() throws IOException {
		jFrame = new JFrame("HillClimbing Solver");
		jFrame.setIconImage(img.getImage());
		jLabel = new JLabel[num_queens][num_queens];
		buttons_panel = new JPanel(new GridBagLayout());
		board_panel = new JPanel(new GridLayout(num_queens, num_queens));
		acn ao = new acn();
		ntio re = new ntio();
		max_iterations_txt_fld.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (Integer.parseInt(max_iterations_txt_fld.getText()) < 2000000000) {
						max_iterations = Integer.parseInt(max_iterations_txt_fld.getText());
					} else {
						JOptionPane.showMessageDialog(null, "Maximum number of iterations is 2 million!");
						max_iterations_txt_fld.setText(String.valueOf(2000000000));
						max_iterations = 2000000000;
					}
				}
			}
		});
		max_iterations_txt_fld.setColumns(8);
		max_iterations_txt_fld.setText(String.valueOf(max_iterations));
		setup_board("first_setup");

		labelTable.put(0, new JLabel("Fast"));
		labelTable.put(25, new JLabel("Average"));
		labelTable.put(50, new JLabel("Slow"));
		points.setLabelTable(labelTable);
		points.setPaintLabels(true);
		points.setMajorTickSpacing(5);
		points.setMinorTickSpacing(1);
		points.setPaintTicks(true);
		points.addChangeListener(event -> delay_value = points.getValue());
		points.setPreferredSize(new Dimension(250, 40));

		start_btn = new JButton("Start");
		start_btn.addActionListener(ao);
		start_btn.addActionListener(o -> reset_btn.setEnabled(false));

		reset_btn = new JButton("Reset");
		reset_btn.addActionListener(re);

		exit_btn = new JButton("Stop");
		exit_btn.setBackground(new Color(192, 20, 20));
		exit_btn.setForeground(Color.WHITE);
		exit_btn.setFocusPainted(false);
		exit_btn.setFont(new Font("Tahoma", Font.BOLD, 12));
		exit_btn.addActionListener(o -> {
            thread.stop();
            reset_btn.setEnabled(true);
        });

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 5);

		buttons_panel.add(exit_btn, Start.setGridBagConstraints(-2, 0, gbc.insets, gbc));

		buttons_panel.add(start_btn, Start.setGridBagConstraints(0, 0, gbc.insets, gbc));

		buttons_panel.add(reset_btn, Start.setGridBagConstraints(2, 0, gbc.insets, gbc));

		buttons_panel.add(points, Start.setGridBagConstraints(4, 0, gbc.insets, gbc));

		buttons_panel.add(delay_label, Start.setGridBagConstraints(8, 0, gbc.insets, gbc));

		buttons_panel.add(max_iterations_txt_fld, Start.setGridBagConstraints(10, 0, gbc.insets, gbc));

		jFrame.setSize(800, 770);
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		board_panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		jFrame.add(board_panel, BorderLayout.CENTER);
		jFrame.add(buttons_panel, BorderLayout.SOUTH);
		jFrame.setVisible(true);
	}

	public static void setup_board(String mode) {
		for (int i = 0; i < HillClimbing.num_queens; ++i) {
			for (int j = 0; j < HillClimbing.num_queens; ++j) {
				if (Objects.equals(mode, "first_setup")) {
					jLabel[i][j] = new JLabel("");
					jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					jLabel[i][j].setSize(50, 50);
					jLabel[i][j].setOpaque(true);
				} else {
					try {
						Thread.sleep(delay_value);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if ((i % 2) == (j % 2)) {
					jLabel[i][j].setBackground(Color.WHITE);
				} else {
					jLabel[i][j].setBackground(Color.BLACK);
				}
				jLabel[i][j].setIcon(null);
				jLabel[i][j].setText("");
				if (Objects.equals(mode, "first_setup")) board_panel.add(jLabel[i][j]);
			}
		}
	}

	public static void display_queens(int[] board_state) {
		for (int i = 0; i < num_queens; i++) {
			jLabel[board_state[i]][i].setBackground(Color.ORANGE);
			jLabel[board_state[i]][i].setIcon(img);
		}
	}

	public static void firstChoiceHillClimbing(int maxNumOfIterations) {
		double startTime = System.nanoTime();
		int[] board_state = SolverUtils.generateRandomState(num_queens);
		int costToBeat = SolverUtils.getHeuristicCost(board_state);
		display_queens(board_state);

		// terminate when it reaches max num of iterations or problem is solved.
		for (int iteration = 0; iteration <= maxNumOfIterations && costToBeat > 0; iteration++) {
			boolean flag = true;
			int tempCostToBeat = costToBeat;
			for (int col = 0; col < num_queens && flag; col++) {
				for (int row = 0; row < num_queens; row++) {
					// we do not need to evaluate because we already know current cost by
					// costToBeat.
					if (row == board_state[col])
						continue;
					// init new copy
					int[] rc = Arrays.copyOf(board_state, num_queens);
					rc[col] = row;
					int cost = SolverUtils.getHeuristicCost(rc);
					if (costToBeat > cost) {
						board_state[col] = row;
						costToBeat = cost;
						flag = false;
						int y = 0;
						for (int j = 0; j < num_queens; j++) {
							for (int i = 0; i < num_queens; i++) {
								try {
									Thread.sleep(delay_value);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								if (board_state[y] == i) {
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
				board_state = SolverUtils.generateRandomState(num_queens);

			setup_board("reset");

			display_queens(board_state);
			jFrame.setTitle("HillClimbing Solver, iteration: " + iteration + " Cost to beat: " + costToBeat);

		}
		double endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000;
		String run_duration = String.format("Time elapsed: %.3f s", totalTime);
		if (costToBeat == 0) {
			JOptionPane.showMessageDialog(null, "Finished!" + "\n" + run_duration);
		} else {
			JOptionPane.showMessageDialog(null, "Failed, reached iteration limit!" + "\n" + run_duration);
		}
		reset_btn.setEnabled(true);
	}

	public static class acon implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Thread constructor_thread = new Thread(() -> {
                try {
                    constructor();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
			constructor_thread.start();

		}
	}

	public static class acn implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			thread = new Thread(() -> firstChoiceHillClimbing(max_iterations));
			thread.start();

		}
	}

	public static class ntio implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Thread board_setter = new Thread(() -> setup_board("reset"));
			board_setter.start();

		}
	}

	public static void main(String[] args) throws IOException {
		constructor();
		firstChoiceHillClimbing(500000);
	}
}