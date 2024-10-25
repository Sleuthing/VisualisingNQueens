package GeneticAlgorithm;

import java.util.Hashtable;

//java.awt imports
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//javax.swing imports
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

//meta imports
import Backtracking.Start;
import Backtracking.Backtracking;
import GeneticAlgorithm.Main.aktion;

public class GUI {
    static JButton load, exc;
    //static boolean T=false;
    public static JFrame main_gui;
    static JTextField pop_size_text_field = new JTextField();
    static JTextField mutation_text_field = new JTextField();
    static JTextField elite_chrom_text_field = new JTextField();
    static JTextField tourn_size_text_field = new JTextField();
    JLabel pop_size_label = new JLabel("Population size:");
    JLabel mutation_label = new JLabel("Mutation rate:");
    JLabel elite_chrom_label = new JLabel("Elite chromosome:");
    JLabel tourn_size_label = new JLabel("Tournament size:");
    JLabel delay_label = new JLabel("Delay:");
    JSlider points = new JSlider(0, 400, Main.SP);
    Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

    public GUI() {

        aktion bc = new aktion();
        labelTable.put(0, new JLabel("Fast"));
        labelTable.put(200, new JLabel("Average"));
        labelTable.put(400, new JLabel("Slow"));
        points.setLabelTable(labelTable);
        points.setPaintLabels(true);
        points.setMajorTickSpacing(5);
        points.setMinorTickSpacing(1);
        points.setPaintTicks(true);
        points.addChangeListener((event) -> Main.SP = points.getValue());
        points.setPreferredSize(new Dimension(200, 40));
        pop_size_text_field.setText(String.valueOf(Main.M * 1000));
        mutation_text_field.setText(String.valueOf(GeneticAlgo.MUTATE_RATE));
        elite_chrom_text_field.setText(String.valueOf(GeneticAlgo.ELITE_CHROMOSOME));
        tourn_size_text_field.setText(String.valueOf(GeneticAlgo.TOURNAMENT_SELECTION_SIZE));
        pop_size_text_field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Main.PoP = Integer.parseInt(pop_size_text_field.getText());
                }
            }
        });
        mutation_text_field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GeneticAlgo.MUTATE_RATE = Double.parseDouble(mutation_text_field.getText());
                }
            }
        });
        elite_chrom_text_field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GeneticAlgo.ELITE_CHROMOSOME = Integer.parseInt(elite_chrom_text_field.getText());
                }
            }
        });
        tourn_size_text_field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GeneticAlgo.TOURNAMENT_SELECTION_SIZE = Integer.parseInt(tourn_size_text_field.getText());
                }
            }
        });
        main_gui = new JFrame("GeneticAlg Intializer, " + Main.M + "-Queens");
        main_gui.setIconImage(Backtracking.img.getImage());
        main_gui.setLayout(new GridBagLayout());
        main_gui.setSize(500, 500);
        main_gui.setLocation(450, 0);
        load = new JButton(" Go! ");
        exc = new JButton("Stop");
        exc.setEnabled(false);

        load.addActionListener(bc);
        load.addActionListener((event) -> {
            exc.setEnabled(true);
            toggle_fields(false);
        });
        exc.addActionListener((event) -> {
            // ToDo: stop thread in a more standard way
            //thread.interrupt();
            Main.thread.stop();
            toggle_fields(true);
        });

        pop_size_text_field.setColumns(4);
        mutation_text_field.setColumns(4);
        elite_chrom_text_field.setColumns(4);
        tourn_size_text_field.setColumns(4);

        GridBagConstraints gbc = new GridBagConstraints();
        Insets gbc_insets = new Insets(5, 0, 0, 5);

        main_gui.add(mutation_text_field, Start.setGridBagConstraints(2, 4, gbc_insets, gbc));

        main_gui.add(mutation_label, Start.setGridBagConstraints(0, 4, gbc_insets, gbc));

        main_gui.add(pop_size_text_field, Start.setGridBagConstraints(2, 2, gbc_insets, gbc));

        main_gui.add(pop_size_label, Start.setGridBagConstraints(0, 2, gbc_insets, gbc));

        main_gui.add(elite_chrom_text_field, Start.setGridBagConstraints(2, -4, gbc_insets, gbc));

        main_gui.add(elite_chrom_label, Start.setGridBagConstraints(0, -4, gbc_insets, gbc));

        main_gui.add(tourn_size_text_field, Start.setGridBagConstraints(2, -2, gbc_insets, gbc));

        main_gui.add(tourn_size_label, Start.setGridBagConstraints(0, -2, gbc_insets, gbc));

        main_gui.add(points, Start.setGridBagConstraints(2, 0, gbc_insets, gbc));

        main_gui.add(delay_label, Start.setGridBagConstraints(0, 0, gbc_insets, gbc));

        main_gui.add(exc, Start.setGridBagConstraints(0, 10, gbc_insets, gbc));

        main_gui.add(load, Start.setGridBagConstraints(-2, 10, gbc_insets, gbc));
        main_gui.setVisible(true);
    }

    public static void main(String[] args) {

        new GUI();

    }

    public static void toggle_fields(boolean b) {
        pop_size_text_field.setEnabled(b);
        mutation_text_field.setEnabled(b);
        elite_chrom_text_field.setEnabled(b);
        tourn_size_text_field.setEnabled(b);
    }
}