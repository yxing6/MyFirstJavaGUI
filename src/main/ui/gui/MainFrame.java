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

        pane.add(textPanel());
        pane.add(textPanel2(),BorderLayout.WEST);

    }

    public JPanel textPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBackground(new Color(0x929BA7));

        JLabel countryName = new JLabel("Country Name:");
        textPanel.add(countryName).setBounds(10, 20, 120, 25);
        JTextField countryNameBox = new JTextField();
        textPanel.add(countryNameBox).setBounds(100, 20, 120,25);
        JLabel countryCost = new JLabel("Country Cost:");
        textPanel.add(countryCost).setBounds(10, 50, 120, 25);
        JTextField countryCostBox = new JTextField();
        textPanel.add(countryCostBox).setBounds(100, 50, 120,25);
        return textPanel;
    }

    public JPanel textPanel2() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
//        textPanel.setLayout(new GridLayout(0, 1));
        textPanel.setBackground(new Color(0x929BA7));

        JLabel countryName = new JLabel("Country Name:");
        textPanel.add(countryName);
        JTextField countryNameBox = new JTextField();
        textPanel.add(countryNameBox);
        JLabel countryCost = new JLabel("Country Cost:");
        textPanel.add(countryCost);
        JTextField countryCostBox = new JTextField();
        textPanel.add(countryCostBox);
        return textPanel;
    }
}
