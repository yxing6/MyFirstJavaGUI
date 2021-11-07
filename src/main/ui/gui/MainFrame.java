package ui.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    // create a main frame, add and display panels
    public MainFrame(String name) {

        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(this.getContentPane());

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);

    }

    public void addComponentsToPane(Container pane) {

        pane.add(leftPanel());

//        pane.add(imagePanel(), BorderLayout.WEST);
//        pane.add(textPanel2(), BorderLayout.PAGE_END);

    }

    public JPanel leftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
//        leftPanel.setBackground(new Color(0x929BA7));

        // set up a world map on the top left of the frame
        JLabel worldLabel = new JLabel();
        ImageIcon worldMap = createImageIcon();
        worldLabel.setIcon(worldMap);
        leftPanel.add(worldLabel).setBounds(20, 0, 400, 300);

        // set up label and text field on the bottom left of the frame
        JLabel countryName = new JLabel("Country Name:");
        leftPanel.add(countryName).setBounds(20, 310, 120, 25);
        JTextField countryNameBox = new JTextField();
        leftPanel.add(countryNameBox).setBounds(150, 310, 120,25);
        JLabel countryCost = new JLabel("Country Cost:");
        leftPanel.add(countryCost).setBounds(20, 350, 120, 25);
        JTextField countryCostBox = new JTextField();
        leftPanel.add(countryCostBox).setBounds(150, 350, 120,25);
        return leftPanel;
    }

    // process the intake image to a smaller size
    public ImageIcon createImageIcon() {

        ImageIcon imageIcon = new ImageIcon("./data/world-map-2500.jpg");
        Image bigMap = imageIcon.getImage();
        Image smallMap = bigMap.getScaledInstance(400, 250, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(smallMap);

        return imageIcon;
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



//    public JPanel imagePanel() {
//        JPanel imagePanel = new JPanel();
//
//        JLabel worldLabel = new JLabel(" ");
//        ImageIcon worldMap = createImageIcon();
//        worldLabel.setIcon(worldMap);
//
//        imagePanel.add(worldLabel);
//        return imagePanel;
//    }
}
