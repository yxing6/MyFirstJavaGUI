package ui.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;


    public MainFrame(String name) {

        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().setBackground(new Color(10, 180, 180));
        addComponentsToPane(this.getContentPane());

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);

    }

    public void addComponentsToPane(final Container pane) {

        pane.add(leftPanel(), BorderLayout.WEST);
//        pane.add(textPanel(),BorderLayout.SOUTH);
//        pane.add(labelPanel(), BorderLayout.SOUTH);
//        pane.add(labelPanel(), BorderLayout.WEST);
    }

    public JPanel leftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(0, 1));
        leftPanel.setBackground(new Color(0x929BA7));

        JLabel countryName = new JLabel("Country Name:");
        leftPanel.add(countryName);
        JTextField countryNameBox = new JTextField();
        leftPanel.add(countryNameBox);
        JLabel countryCost = new JLabel("Country Cost:");
        leftPanel.add(countryCost);
        JTextField countryCostBox = new JTextField();
        leftPanel.add(countryCostBox);
        return leftPanel;
    }

    public JPanel textPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(0, 2));
        textPanel.setBackground(new Color(0xB0A69B));



        return textPanel;
    }
}
