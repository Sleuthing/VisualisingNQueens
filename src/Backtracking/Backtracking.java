package Backtracking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Random;

public class Backtracking {
    static int delay = 15;
    public static int num_queens = 20;
    private static JLabel[][] jLabel = new JLabel[num_queens][num_queens];
    JSlider points = new JSlider(0, 30, delay);
    Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

    public static ImageIcon img = SolverUtils.imgo;

    private static int[][] board = new int[num_queens][num_queens];
    JPanel board_panel;
    JToolBar toolbar;
    private static JButton modified_start_btn, exit_btn, reset_btn, start_btn;
    acion m = new acion();
    aion c = new aion();
    acton l = new acton();
    static Thread thread;


    //makes sure that the queen in board[row][col] is safe
    static boolean isSafe(int row, int col) {

        try {
            Thread.sleep(delay);
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

        for (int i = row, j = col; i < num_queens && j >= 0; ++i, --j) {

            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    static void markVisitedCell(int row, int col) {
        if ((row % 2) == (col % 2)) {
            jLabel[row][col].setBackground(Color.WHITE);
            jLabel[row][col].setForeground(Color.BLACK);
        } else {
            jLabel[row][col].setBackground(Color.BLACK);
            jLabel[row][col].setForeground(Color.WHITE);
        }
        jLabel[row][col].setText("(" + row + "," + col + ")");
        jLabel[row][col].setIcon(null);
    }

    static void placeQueen(int row, int col) {
        board[row][col] = 1;
        jLabel[row][col].setBackground(Color.ORANGE);
        jLabel[row][col].setIcon(img);
    }

    static boolean findSolution(int col) {
        if (col >= num_queens)
            return true;
        if (col == 0) {
            Random rand = new Random();
            int row = rand.nextInt(num_queens);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSafe(row, col)) {
                placeQueen(row, col);
                if (findSolution(col + 1)) {
                    return true;
                }

                board[row][col] = 0;
                markVisitedCell(row,col);
            }

        } else {
            for (int row = 0; row < num_queens; ++row) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isSafe(row, col)) {
                    placeQueen(row, col);
                    if (findSolution(col + 1)) {
                        return true;
                    }

                    board[row][col] = 0;
                    markVisitedCell(row,col);

                }

            }

        }

        return false;

    }

    static boolean findModifiedSolution(int col) {

        if (col >= num_queens)
            return true;

        int[] MM = SolverUtils.generateRandomState(num_queens);

        int i = 0;
        for (int row = MM[i]; i < num_queens; ++i) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSafe(row, col)) {
                placeQueen(row, col);
                if (findModifiedSolution(col + 1)) {
                    return true;
                }

                board[row][col] = 0;
                markVisitedCell(row,col);
            }
        }

        return false;

    }

    static void giveResult(double startTime, int mode){
        if (mode == 0) {
            JOptionPane.showMessageDialog(null, "No Solution.");
            reset_btn.setEnabled(true);
            return;
        }
        double endTime = System.nanoTime();
        double totalTime = (endTime - startTime) / 1000000000;
        String ss = String.format("Time elapsed: %.3f s", totalTime);
        JOptionPane.showMessageDialog(null, "Solved!" + "\n" + ss);
        reset_btn.setEnabled(true);
    }

    static void solveNQueen(int y) {
        double startTime = System.nanoTime();
        if (y == 0) {
            if (!findSolution(0)) {
                giveResult(0,0);
            } else {
                giveResult(startTime,1);
            }
        } else {
            if (!findModifiedSolution(0)) {
                giveResult(0,0);
            } else {
                giveResult(startTime,1);
            }
        }
    }

    Backtracking() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        //int frame_height = 800, frame_width = 770;

        labelTable.put(0, new JLabel("Fast"));
        labelTable.put(15, new JLabel("Average"));
        labelTable.put(30, new JLabel("Slow"));
        points.setLabelTable(labelTable);
        points.setPaintLabels(true);
        points.setMajorTickSpacing(5);
        points.setMinorTickSpacing(1);
        points.setPaintTicks(true);
        points.addChangeListener(event -> delay = points.getValue());
        points.setPreferredSize(new Dimension(50, 40));

        start_btn = new JButton("Start");
        reset_btn = new JButton("Reset");
        modified_start_btn = new JButton("Modified");
        exit_btn = new JButton("Stop");

        start_btn.addActionListener(m);
        reset_btn.addActionListener(l);
        modified_start_btn.addActionListener(c);
        toolbar = new JToolBar();
        toolbar.setFloatable(true);
        exit_btn.setBackground(new Color(192, 20, 20));
        exit_btn.setForeground(Color.WHITE);
        exit_btn.setFocusPainted(false);
        exit_btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        toolbar.add(start_btn);
        toolbar.add(exit_btn);
        toolbar.add(reset_btn);
        toolbar.add(modified_start_btn);
        toolbar.add(points);
        JFrame jFrame = new JFrame("Backtracking Solver");
        jFrame.setIconImage(img.getImage());
        board_panel = new JPanel(new GridLayout(num_queens, num_queens));
        jFrame.setSize(800, 770);
        jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //exit_btn.setEnabled(false);
        exit_btn.addActionListener(o -> {
            // jFrame.dispose();
            thread.stop();
            reset_btn.setEnabled(true);
        });
        start_btn.addActionListener(o -> {
            // jFrame.dispose();
            reset_btn.setEnabled(false);
            exit_btn.setEnabled(true);
        });
        for (int i = 0; i < num_queens; ++i) {
            for (int j = 0; j < num_queens; ++j) {
                jLabel[i][j] = new JLabel("");
                jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i][j].setSize(50, 50);
                jLabel[i][j].setOpaque(true);
                if ((i % 2) == (j % 2)) {
                    jLabel[i][j].setBackground(Color.WHITE);
                } else {
                    jLabel[i][j].setBackground(Color.BLACK);
                }
                jLabel[i][j].setIcon(null);
                jLabel[i][j].setText("");
                board_panel.add(jLabel[i][j]);
            }
        }
        // buttons_panel.add(toolbar, BorderLayout.NORTH);
        board_panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        jFrame.add(board_panel, BorderLayout.CENTER);
        jFrame.add(toolbar, BorderLayout.NORTH);
        jFrame.setVisible(true);

    }

    static void reset() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < num_queens; ++i) {
            for (int j = 0; j < num_queens; ++j) {
                board[i][j] = 0;
                if ((i % 2) == (j % 2)) {
                    jLabel[i][j].setBackground(Color.WHITE);
                } else {
                    jLabel[i][j].setBackground(Color.BLACK);
                }
                jLabel[i][j].setIcon(null);
                jLabel[i][j].setText("");
            }
        }
    }

    public static class action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Thread thread_0 = new Thread(() -> {
                try {
                    new Backtracking();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                         | UnsupportedLookAndFeelException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            thread_0.start();
        }
    }

    public static class acion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thread = new Thread(() -> solveNQueen(0));
            thread.start();
        }
    }

    public static class aion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thread = new Thread(() -> solveNQueen(1));
            thread.start();
        }
    }

    public static class acton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Thread thread_1 = new Thread(() -> reset());
            thread_1.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Backtracking();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                     | UnsupportedLookAndFeelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

}
