package ui.gui;

import model.Country;
import model.TravelList;
import model.exception.NegativeCostException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Visibility;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.fail;

public class MainGUI extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;
    private TravelList travelList;
    private JList bucketList;
    private JList visitedList;

    private static final String addToBucketListString = "Add to bucket list";
    private static final String addToVisitedListString = "Add to visited list";
    private JButton addButton;
    private JTextField countryName;
    private JTextField countryCost;

    public MainGUI() {

//        super(new BorderLayout());

        travelList = new TravelList();
        try {
            Country countryA = new Country("Canada", 4000);
            Country countryB = new Country("China", 5000);
            Country countryC = new Country("Belgian", 6000);
            Country countryD = new Country("Belgia", 6000);
            Country countryE = new Country("Belgi", 6000);
            Country countryF = new Country("Belg", 6000);
            Country countryG = new Country("Bel", 6000);

            travelList.addCountryToGo(countryA);
            travelList.addCountryVisited(countryB);
            travelList.addCountryVisited(countryC);
            travelList.addCountryVisited(countryD);
            travelList.addCountryVisited(countryE);
            travelList.addCountryVisited(countryF);
            travelList.addCountryVisited(countryG);

        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }


//        bucketList = new JList((Vector) travelList.getBucketList());
//        visitedList = new JList((Vector) travelList.getVisitedList());
//        JScrollPane bucketScrollPane = createScrollPanel(new JList((Vector) travelList.getBucketList()));
//
//        JScrollPane visitedScrollPane = createScrollPanel(new JList((Vector) travelList.getVisitedList()));

        JLabel countryName = createLabel("Country Name", 10, 20, 80, 25);
        JLabel countryCost = createLabel("Country Cost", 10, 50, 80, 25);
        JTextField countryNameField = createTextFiled(100, 20, 80, 25);
        JTextField countryCostField = createTextFiled(100, 50, 80, 25);
        JButton addBucketButton = createButton(addToBucketListString);
        JButton addVisitedButton = createButton(addToVisitedListString);

//        add(bucketScrollPane, BorderLayout.NORTH);
//        add(visitedScrollPane, BorderLayout.CENTER);
        add(countryName);
        add(countryCost);
        add(countryNameField);
        add(countryCostField);
        add(addBucketButton);
        addBucketButton.setBounds(10, 80, 100, 25);
        add(addVisitedButton);
        addVisitedButton.setBounds(10, 110, 100, 25);
    }

    public JScrollPane createScrollPanel(JList list) {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
//        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        return listScrollPane;
    }

    public JLabel createLabel(String string, int x, int y, int width, int height) {
        JLabel label = new JLabel(string);
        label.setBounds(x, y, width, height);
        return label;
    }

    public JTextField createTextFiled(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        return textField;
    }

    public JButton createButton(String string) {
        JButton button = new JButton(string);
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                String name = countryName.getText();
////                String cost = countryCost.getText();
//            }
//        });
//        button.setActionCommand(string);
        button.setEnabled(true);
        return button;
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the travel list GUI desktop frame
        JFrame frame = new JFrame("Travel List");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new MainGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


}
