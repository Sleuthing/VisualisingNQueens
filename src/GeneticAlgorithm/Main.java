package GeneticAlgorithm;

import javax.swing.*;

import Backtracking.SolverUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static JFrame main_gui;
    public static int PoP = 20000;
    public static int SP = 200;
    public static int M = 20;
    static Thread thread;
    private static JLabel[][] jLabel;
    static JPanel Q, P;
    public static ImageIcon img = SolverUtils.imgo;

    public static void reset() {
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {

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

    public Main() {
        main_gui = new JFrame("GeneticAlg Solver");
        main_gui.setResizable(true);
        main_gui.setLayout(new BorderLayout());
        main_gui.setLocation(550, 0);
        main_gui.setIconImage(img.getImage());
        main_gui.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jLabel = new JLabel[M][M];
        P = new JPanel(new GridBagLayout());
        Q = new JPanel(new GridLayout(M, M));

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
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
                Q.add(jLabel[i][j]);
            }
        }

        main_gui.setSize(800, 770);
        main_gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Q.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        main_gui.add(Q, BorderLayout.CENTER);
        main_gui.add(P, BorderLayout.SOUTH);
        main_gui.setVisible(true);

    }

    public static void loop_over_cells(GeneticAlgo geneticAlgo) {
        for (int i = 0; i < M; i++) {
            jLabel[geneticAlgo.getFittestChromosome().genes[i]][i].setBackground(Color.ORANGE);
            jLabel[geneticAlgo.getFittestChromosome().genes[i]][i].setIcon(img);
        }
    }

    public static void solveG() {

        GeneticAlgo geneticAlgo = new GeneticAlgo(PoP);
        int generation = 1;

        loop_over_cells(geneticAlgo);


        double startTime = System.nanoTime();
        while (geneticAlgo.getFittestChromosome().getFitness() != 0) {
            main_gui.setTitle("GeneticAlg Solver, generation: " + generation + " Fitness:"
                    + geneticAlgo.getFittestChromosome().getFitness());

            geneticAlgo.naturalSelection();

            reset();
            loop_over_cells(geneticAlgo);

            try {
                Thread.sleep(SP);
            } catch (Exception e) {
                // Do nothing
            }
            generation++;

        }
        double endTime = System.nanoTime();
        double totalTime = (endTime - startTime) / 1000000000;
        if (geneticAlgo.getFittestChromosome().getFitness() == 0) {
            String ss = String.format("Time elapsed: %.3f s", totalTime);
            JOptionPane.showMessageDialog(null, "Done!" + "\n" + ss);
            main_gui.setTitle("GeneticAlg Solver, generation: " + generation + " Fitness: "
                    + geneticAlgo.getFittestChromosome().getFitness());
        }
        GUI.toggle_fields(true);
    }

    public static void main(String[] args) {
        new Main();
        solveG();
    }

    public static class aktion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thread = new Thread(() -> {

                new Main();
                solveG();

            });

            thread.start();

        }
    }
}
