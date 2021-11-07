package ui.gui;

import javax.swing.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 800;

    public MainFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setTitle("My travel list");
        setResizable(false);

        setVisible(true);
    }
}
