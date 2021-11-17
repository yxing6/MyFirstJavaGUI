package ui.gui;

import model.Country;
import model.TravelList;
import model.exception.NegativeCostException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainFrame extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private TravelList travelList;
    private TravelList travelListOut;

    private JMenuBar menuBar;

    private JPanel bucketListPanel;
    private DefaultListModel bucketListModel;
    private JList bucketJList;
//    private JScrollPane bucketScrollPane;
    private JLabel bucketSizeLabel;
    private JLabel bucketTotalCostLabel;
    private int bucketSizeInt;
    private int bucketTotalCost;

    private JPanel visitedListPanel;
    private DefaultListModel visitedListModel;
    private JList visitedJList;
    private JLabel visitedSizeLabel;
    private JLabel visitedTotalCostLabel;
    private int visitedSizeInt;
    private int visitedTotalCost;

    private JTextField countryName;
    private JTextField countryCost;
    private String countryNameString;
    private int countryCostInteger;

    private JButton addToBucketList;
    private JButton addToVisitedList;
    private JButton removeFromBucketList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/travels.json";


    // create a main frame, add and display panels
    public MainFrame(String name) {

        super(name);

        initContentFields();
        createMenu();
        setJMenuBar(menuBar);
        addComponentsToPane(this.getContentPane());


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);
    }

    // EFFECT: initiate all label, text, & button fields
    public void initContentFields() {

        bucketListModel = new DefaultListModel();
        bucketJList = new JList(bucketListModel);
        bucketSizeLabel = new JLabel();
        bucketTotalCostLabel = new JLabel();
        bucketSizeInt = 0;
        bucketTotalCost = 0;

        visitedListModel = new DefaultListModel();
        visitedJList = new JList(visitedListModel);
        visitedSizeLabel = new JLabel();
        visitedTotalCostLabel = new JLabel();
        visitedSizeInt = 0;
        visitedTotalCost = 0;

        countryName = new JTextField();
        countryCost = new JTextField();
//        countryNameString = countryName.getText();
//        countryCostInteger = Integer.parseInt(countryCost.getText());

        addToBucketList = new JButton("Add to bucket list");
        addToVisitedList = new JButton("Add to visited list");
        removeFromBucketList = new JButton("remove");

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        travelList = new TravelList();
        travelListOut = new TravelList();

    }

    public void createMenu() {

        menuBar = new JMenuBar();
        JMenuItem loadMenu = new JMenuItem("LOAD");
        JMenuItem saveMenu = new JMenuItem("SAVE");
        menuBar.add(loadMenu);
        menuBar.add(saveMenu);

        loadMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTravelList();
            }
        });

        saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeTravelList();
            }
        });

    }

    private void loadTravelList() {

        try {
            travelList = jsonReader.read();
            travelListOut = travelList;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (NegativeCostException ne) {
            System.out.println("Some country appears to have negative travel cost associates...");
        }

        // load data from travelList into bucketList
        List<String> bl = travelList.countriesToGo();
        for (String s: bl) {
            bucketListModel.addElement(s);
        }
        bucketSizeInt += travelList.numCountriesToGo();
        bucketTotalCost += travelList.moneyNeedToSave();
        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
        bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);


        // load data from travelList into visitedList
        visitedSizeInt += travelList.numCountriesVisited();
        visitedTotalCost += travelList.moneySpentOnTravel();
        visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
        visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        List<String> vl = travelList.countriesVisited();
        for (String s: vl) {
            visitedListModel.addElement(s);
        }
    }


    private void writeTravelList() {

        travelListOut = travelList;
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(travelListOut);
            jsonWriter.close();
            System.out.println("Saved the travel list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // add each individual panel tp the container
    public void addComponentsToPane(Container pane) {

        pane.setLayout(null);
        pane.add(imagePanel()).setBounds(0, 0, 500, 300);
        pane.add(countryPanel()).setBounds(0, 300, 500, 200);
        setUpBucketListPanel();
        pane.add(bucketListPanel).setBounds(500, 0, 300, 200);
        setupVisitedListPanel();
        pane.add(visitedListPanel).setBounds(500, 200, 300, 200);

    }


    // construct an image panel to the top left of the frame and add an image
    public JPanel imagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);

        // set up a world map on the top left of the frame
        JLabel worldLabel = new JLabel();
        ImageIcon worldMap = createImageIcon();
        worldLabel.setIcon(worldMap);
        imagePanel.add(worldLabel).setBounds(20, 0, 500, 300);

        return imagePanel;
    }


    // process the intake image to a smaller size
    public ImageIcon createImageIcon() {

        ImageIcon imageIcon = new ImageIcon("./data/world-map-2500.jpg");
        Image bigMap = imageIcon.getImage();
        Image smallMap = bigMap.getScaledInstance(450, 250, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(smallMap);

        return imageIcon;
    }


    // construct a panel to the bottom left of the frame
    // set up label and text field for adding a country into either one of the lists
    public JPanel countryPanel() {

        JPanel countryPanel = new JPanel();
        countryPanel.setLayout(null);

        JLabel countryNameLabel = new JLabel("Country Name:");
        countryPanel.add(countryNameLabel).setBounds(20, 10, 100, 25);
        countryPanel.add(countryName).setBounds(130, 10, 100, 25);

        JLabel countryCostLabel = new JLabel("Country Cost:");
        countryPanel.add(countryCostLabel).setBounds(20, 50, 100, 25);
        countryPanel.add(countryCost).setBounds(130, 50, 100, 25);

        addToBucketList.addActionListener(new AddListener(bucketListModel, bucketJList, 1));
        countryPanel.add(addToBucketList).setBounds(250, 10, 150, 25);

        addToVisitedList.addActionListener(new AddListener(visitedListModel, visitedJList, 2));
        countryPanel.add(addToVisitedList).setBounds(250, 50, 150, 25);

        return countryPanel;
    }


    // a JScrollPane to be used by both BucketList and VisitedList
    public JScrollPane makeScrollPane(JList myJList) {
        myJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myJList.setSelectedIndex(0);
        myJList.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(myJList);
        return listScrollPane;
    }


    // construct a panel to the top right of the frame
    // place bucket list, it's associated information, and a remove button special to bucketList
    public void setUpBucketListPanel() {

        bucketListPanel = new JPanel();
        bucketListPanel.setLayout(null);

        JLabel buLabel = new JLabel("Your Bucket List");
        bucketListPanel.add(buLabel).setBounds(0, 20, 100, 30);
        JScrollPane bucketScrollPane = makeScrollPane(bucketJList);
        bucketListPanel.add(bucketScrollPane).setBounds(0, 60, 100, 140);

        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
        bucketListPanel.add(bucketSizeLabel).setBounds(120, 50, 200, 30);

        bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        bucketListPanel.add(bucketTotalCostLabel).setBounds(120, 90, 200, 30);

        removeFromBucketList.addActionListener(new RemoveListener());
        bucketListPanel.add(removeFromBucketList).setBounds(120, 130, 100, 25);
    }


    // construct a panel to the bottom right of the frame
    // place visited list, and it's associated information.
    public void setupVisitedListPanel() {

        visitedListPanel = new JPanel();
        visitedListPanel.setLayout(null);

        JLabel viLabel = new JLabel("Your Visited List");
        visitedListPanel.add(viLabel).setBounds(0, 20, 100, 30);
        JScrollPane visitedScrollPanel = makeScrollPane(visitedJList);
        visitedListPanel.add(visitedScrollPanel).setBounds(0, 60, 100, 140);

        visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
        visitedListPanel.add(visitedSizeLabel).setBounds(120, 50, 200, 30);

        visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        visitedListPanel.add(visitedTotalCostLabel).setBounds(120, 90, 200, 30);
    }


    // Override method required by ListSelectionListener
    public void valueChanged(ListSelectionEvent e) {

        if (!e.getValueIsAdjusting()) {

            if (bucketJList.getSelectedIndex() == -1) {
                //No selection, disable remove button
                removeFromBucketList.setEnabled(false);
            } else {
                //Selection, enable the remove button.
                removeFromBucketList.setEnabled(true);
            }
        }
    }


    // This listener is built following the Java8 Component-ListDemo Project.
    // Remove listener is only used by bucket list. The country on visited list can not be removed.
    class RemoveListener implements ActionListener {

        // Remove button will be functional only if an item is selected
        public void actionPerformed(ActionEvent e) {

            // get index of the selected item and remove by index
            int index = bucketJList.getSelectedIndex();
            bucketListModel.remove(index);

            // decrement and display number
            bucketSizeInt--;
            bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
//            bucketTotalCost -= Integer.parseInt(countryCost.getText());
//            bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);

            // once empty, disable the remove button
            if (bucketSizeInt == 0) {
                removeFromBucketList.setEnabled(false);
            }
        }
    }


    //     This listener is built following the Java8 Component-ListDemo Project
//     AddListener is shared by the text field and the add button for Bucket and Visited Lists
    class AddListener implements ActionListener {

        private DefaultListModel modelList;
        private JList pairedJList;
        private int whichList;
        String countryNameString;
        int countryCostInteger;

        // at construction, programmer identify the list they intended to modify
        public AddListener(DefaultListModel modelList, JList pairedJList, int whichList) {
            this.modelList = modelList;
            this.pairedJList = pairedJList;
            this.whichList = whichList;
        }

        public void actionPerformed(ActionEvent e) {
            countryNameString = countryName.getText();
            countryCostInteger = Integer.parseInt(countryCost.getText());

            // request attention if the user entered non-unique country
            if (countryNameString.equals("") || modelList.contains(countryNameString)) {
                Toolkit.getDefaultToolkit().beep();
                countryName.requestFocusInWindow();
                return;
            }
            // otherwise, add the country name to the end of the display list
            modelList.addElement(countryNameString);

            // construct a country, add it to display list also the list for date save
            try {
                Country country = new Country(countryNameString, countryCostInteger);
                if (whichList == 1) {
                    travelListOut.addCountryToGo(country);
                    updateBucketListLabels();
                } else if (whichList == 2) {
                    travelListOut.addCountryVisited(country);
                    updateVisitedListLabels();
                }
            } catch (NegativeCostException ex) {
                System.out.println("Cost cannot be negative");
            }

            // reset the text field.
            countryName.requestFocusInWindow();
            countryName.setText("");
            countryCost.setText("");
        }

        public void updateBucketListLabels() {
            bucketSizeInt++;
            bucketTotalCost += countryCostInteger;
            bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
            bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        }

        public void updateVisitedListLabels() {
            visitedSizeInt++;
            visitedTotalCost += countryCostInteger;
            visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
            visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        }


    }

}
