package ui.gui;

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
import java.io.IOException;
import java.util.List;


public class MainFrame extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private TravelList travelList;

    private DefaultListModel bucketListModel;
    private JList bucketJList;
    private JLabel bucketSizeLabel;
    private JLabel bucketTotalCostLabel;
    private int bucketSizeInt;
    private int bucketTotalCost;

    private DefaultListModel visitedListModel;
    private JList visitedJList;
    private JLabel visitedSizeLabel;
    private JLabel visitedTotalCostLabel;
    private int visitedSizeInt;
    private int visitedTotalCost;

    private JTextField countryName;
    private JTextField countryCost;

    private JButton addToBucketList;
    private JButton addToVisitedList;
    private JButton removeFromBucketList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/travels.json";


    // create a main frame, add and display panels
    public MainFrame(String name) {

        super(name);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadTravelList();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(this.getContentPane());
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);

    }


    private void loadTravelList() {

        try {
            travelList = jsonReader.read();
            System.out.println("Loaded the travel list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (NegativeCostException ne) {
            System.out.println("Some country appears to have negative travel cost associates...");
        }

        // initiate every field associated with bucket list
        bucketSizeLabel = new JLabel();
        bucketTotalCostLabel = new JLabel();
        bucketListModel = new DefaultListModel();
        bucketSizeInt = travelList.numCountriesToGo();
        bucketTotalCost = travelList.moneyNeedToSave();
        List<String> bl = travelList.countriesToGo();
        for (String s: bl) {
            bucketListModel.addElement(s);
        }
        bucketJList = new JList(bucketListModel);


        // initiate every field associated with visited list
        visitedSizeLabel = new JLabel();
        visitedTotalCostLabel = new JLabel();
        visitedListModel = new DefaultListModel();
        List<String> vl = travelList.countriesVisited();
        for (String s: vl) {
            visitedListModel.addElement(s);
        }
        visitedJList = new JList(visitedListModel);
        visitedSizeInt = travelList.numCountriesVisited();
        visitedTotalCost = travelList.moneySpentOnTravel();
    }


    // add each individual panel tp the container
    public void addComponentsToPane(Container pane) {

        pane.setLayout(null);
        pane.add(imagePanel()).setBounds(0, 0, 500, 300);
        pane.add(addCountryPanel()).setBounds(0, 300, 500, 200);
        pane.add(bucketListPanel()).setBounds(500, 0, 300, 200);
        pane.add(visitedListPanel()).setBounds(500, 200, 300, 200);

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
    public JPanel addCountryPanel() {

        JPanel countryPanel = new JPanel();
        countryPanel.setLayout(null);

        JLabel countryNameLabel = new JLabel("Country Name:");
        countryPanel.add(countryNameLabel).setBounds(20, 10, 100, 25);
        countryName = new JTextField();
        countryPanel.add(countryName).setBounds(130, 10, 100,25);
        JLabel countryCostLabel = new JLabel("Country Cost:");
        countryPanel.add(countryCostLabel).setBounds(20, 50, 100, 25);
        countryCost = new JTextField();
        countryPanel.add(countryCost).setBounds(130, 50, 100,25);

        addToBucketList = new JButton("Add to bucket list");
        addToBucketList.addActionListener(new AddListener(bucketListModel, bucketJList, 1));
        countryPanel.add(addToBucketList).setBounds(250, 10, 150, 25);

        addToVisitedList = new JButton("Add to visited list");
        addToVisitedList.addActionListener(new AddListener(visitedListModel, visitedJList, 2));
        countryPanel.add(addToVisitedList).setBounds(250, 50, 150, 25);

        return countryPanel;
    }


    // construct a panel to the top right of the frame
    // place bucket list, it's associated information, and a remove button special to bucketList
    public JPanel bucketListPanel() {

        JPanel bucketListPanel = new JPanel();
        bucketListPanel.setLayout(null);

        JLabel buLabel = new JLabel("Your Bucket List");
        bucketListPanel.add(buLabel).setBounds(0, 20, 100, 30);
        JScrollPane bucketScrollPane = makeScrollPane(bucketJList);
        bucketListPanel.add(bucketScrollPane).setBounds(0, 60, 100, 140);

        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
        bucketListPanel.add(bucketSizeLabel).setBounds(120, 50, 200, 30);

        bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        bucketListPanel.add(bucketTotalCostLabel).setBounds(120, 90, 200, 30);

        removeFromBucketList = new JButton("remove");
        removeFromBucketList.addActionListener(new RemoveListener());
        bucketListPanel.add(removeFromBucketList).setBounds(120, 130, 100, 25);

        return bucketListPanel;
    }


    // construct a panel to the bottom right of the frame
    // place visited list, and it's associated information.
    public JPanel visitedListPanel() {

        JPanel visitedListPanel = new JPanel();
        visitedListPanel.setLayout(null);

        JLabel viLabel = new JLabel("Your Visited List");
        visitedListPanel.add(viLabel).setBounds(0, 20, 100, 30);
        JScrollPane visitedScrollPanel = makeScrollPane(visitedJList);
        visitedListPanel.add(visitedScrollPanel).setBounds(0, 60, 100, 140);

        visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
        visitedListPanel.add(visitedSizeLabel).setBounds(120, 50, 200, 30);

        visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        visitedListPanel.add(visitedTotalCostLabel).setBounds(120, 90, 200, 30);

        return visitedListPanel;
    }


    // a JScrollPane to be used by both BucketList and VisitedList
    public JScrollPane makeScrollPane(JList myJList) {
        myJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myJList.setSelectedIndex(0);
        myJList.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(myJList);
        return listScrollPane;
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

            // once empty, disable the remove button
            if (bucketSizeInt == 0) {
                removeFromBucketList.setEnabled(false);
            }
        }
    }


    // This listener is built following the Java8 Component-ListDemo Project
    // AddListener is shared by the text field and the add button for Bucket and Visited Lists
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

            // update fields according to method call
            if (whichList == 1) {
                updateBucketList();
            } else if (whichList == 2) {
                updateVisitedList();
            }

            // reset the text field.
            countryName.requestFocusInWindow();
            countryName.setText("");
            countryCost.requestFocusInWindow();
            countryCost.setText("");
        }

        public void updateBucketList() {
            bucketSizeInt++;
            bucketTotalCost += countryCostInteger;
            bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
            bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        }

        public void updateVisitedList() {
            visitedSizeInt++;
            visitedTotalCost += countryCostInteger;
            visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
            visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        }


    }

}
