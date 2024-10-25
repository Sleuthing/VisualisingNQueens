package Backtracking;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

//java.awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//javax.swing imports
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//meta imports
import Backtracking.Backtracking.action;
import Backtracking.HillClimbing.acon;
import GeneticAlgorithm.*;

class formatButton extends JButton {
    formatButton(String name) {
        super(name);
    }

    public void setFormat(int[] color, String fontName, int width) {
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setBackground(new Color(color[0], color[1], color[2]));
        this.setFont(new Font(fontName, Font.BOLD, 16));
        this.setPreferredSize(new Dimension(width, 40));
    }
}

public class Start {
    //static JButton exit_button, strt, gen, hc;
    action ac = new action();
    acon ap = new acon();
    GridBagConstraints gbc = new GridBagConstraints();
    Insets gbc_insets = new Insets(3, 3, 3, 3);
    JTextField queen_number_text_field = new JTextField();
    JTextArea queen_number_text_area = new JTextArea(" Number of queens: ");
    JFrame main_gui = new JFrame("NQueen Solver");
    JPanel J;
    JLabel queen_image_label = new JLabel();
    public static ImageIcon img = new ImageIcon("Imgs\\sf.png");

    Start() throws IOException {
        queen_image_label.setPreferredSize(new Dimension(90, 90));
        queen_image_label.setIcon(img);
        queen_number_text_field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (Integer.parseInt(queen_number_text_field.getText()) <= 20 && Integer.parseInt(queen_number_text_field.getText()) >= 4) {
                        Backtracking.M = Integer.parseInt(queen_number_text_field.getText());
                        Main.M = (Integer.parseInt(queen_number_text_field.getText()));
                        HillClimbing.M = Integer.parseInt(queen_number_text_field.getText());
                        if (GUI.main_gui != null) {
                            GUI.main_gui.dispose();
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e1) {
                                // ToDo: replace this with a better logging method
                                //Logger.log(e1..printStackTrace(), e1);
                                e1.printStackTrace();
                            }
                            if (Main.main_gui != null)
                                Main.main_gui.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Sorry, number of allowed queens is between 4-20!");
                        queen_number_text_field.setText(String.valueOf(Backtracking.M));
                    }

                }
            }
        });

        J = new JPanel();

        main_gui.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Imgs\\white.png")))));
        main_gui.setIconImage(Backtracking.img.getImage());
        main_gui.setLayout(new GridBagLayout());
        main_gui.setSize(500, 500);
        main_gui.setLocation(450, 0);
        main_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons and customize appearance
        formatButton exit_button = new formatButton("Exit");
        exit_button.setFormat(new int[]{192, 20, 20}, "Tahoma", 70);

        formatButton start_button = new formatButton("Backtracking");
        start_button.setFormat(new int[]{57, 107, 189}, "Serif", 130);

        formatButton genetic_button = new formatButton("Genetic");
        genetic_button.setFormat(new int[]{57, 107, 189}, "Serif", 120);

        formatButton hillclimb_button = new formatButton("Hillclimbing");
        hillclimb_button.setFormat(new int[]{57, 107, 189}, "Serif", 120);

        queen_number_text_field.setColumns(5);
        queen_number_text_field.setText(String.valueOf(Backtracking.M));
        queen_number_text_field.setPreferredSize(new Dimension(80, 30));

        start_button.addActionListener(ac);
        hillclimb_button.addActionListener(ap);
        queen_number_text_area.setBackground(Color.WHITE);
        queen_number_text_area.setFont(new Font("Serif", Font.BOLD, 14));
        exit_button.addActionListener((event) -> {
            //public void actionPerformed(ActionEvent o) {
            System.exit(0);
            //}
        });
        genetic_button.addActionListener((event) -> {
            //public void actionPerformed(event) {
            new GUI();
            //}
        });

        main_gui.add(queen_image_label, setGridBagConstraints(-1, 0, gbc_insets, gbc));
        main_gui.add(queen_number_text_field, setGridBagConstraints(-2, 0, gbc_insets, gbc));
        main_gui.add(hillclimb_button, setGridBagConstraints(-1, 1, gbc_insets, gbc));
        main_gui.add(start_button, setGridBagConstraints(-2, 1, gbc_insets, gbc));
        main_gui.add(genetic_button, setGridBagConstraints(-3, 1, gbc_insets, gbc));
        main_gui.add(exit_button, setGridBagConstraints(-3, 3, gbc_insets, gbc));

        main_gui.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Start();
    }

    public static GridBagConstraints setGridBagConstraints(int x, int y, Insets insets, GridBagConstraints gbc) {
        gbc.insets = insets;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

}