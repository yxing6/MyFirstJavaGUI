package ui.gui;

import model.Country;
import model.Event;
import model.EventLog;
import model.TravelList;
import model.exception.NegativeCostException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private TravelList travelListIn;
    private TravelList travelListOut;

    private JMenuBar menuBar;

    private JPanel bucketListPanel;
    private DefaultListModel bucketListModel;
    private JList bucketJList;
    private JLabel bucketSizeLabel;
    private JLabel bucketTotalCostLabel;
    private ArrayList<Integer> bucketCountryCost;
    private int bucketSizeInt;
    private int bucketTotalCost;

    private JPanel visitedListPanel;
    private DefaultListModel visitedListModel;
    private JList visitedJList;
    private JLabel visitedSizeLabel;
    private JLabel visitedTotalCostLabel;
    private List visitedCountryCost;
    private int visitedSizeInt;
    private int visitedTotalCost;

    private JTextField countryName;
    private JTextField countryCost;

    private JButton addToBucketList;
    private JButton addToVisitedList;
    private JButton removeFromBucketList;
    private JButton moveToVisitedList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String JSON_STORE = "./data/travels.json";
    private static final String ADD_TO_BUCKET_LIST = "Add to bucket list";
    private static final String ADD_TO_VISITED_LIST = "Add to visited list";
    private static final String REMOVE_FROM_BUCKET_LIST = "Remove from bucket list";
    private static final String MOVE_TO_VISITED_LIST = "Move to visited list";


    // MODIFIES: this
    // EFFECTS: create a main frame, add and display menu bar and panels
    public MainFrame(String name) {

        super(name);

        initContentFields();
        createMenu();
        addComponentsToPane(this.getContentPane());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);


    }

    public void printLog() {
        EventLog el = EventLog.getInstance();
        for (Event event: el) {
            System.out.println(event.toString());
        }
    }


    // MODIFIES: this
    // EFFECTS: initiate all label, text, & button fields
    public void initContentFields() {

        bucketListModel = new DefaultListModel();
        bucketJList = new JList(bucketListModel);
        bucketSizeLabel = new JLabel();
        bucketTotalCostLabel = new JLabel();
        bucketCountryCost = new ArrayList();
        bucketSizeInt = 0;
        bucketTotalCost = 0;

        visitedListModel = new DefaultListModel();
        visitedJList = new JList(visitedListModel);
        visitedSizeLabel = new JLabel();
        visitedTotalCostLabel = new JLabel();
        visitedCountryCost = new ArrayList();
        visitedSizeInt = 0;
        visitedTotalCost = 0;

        countryName = new JTextField();
        countryCost = new JTextField();

        addToBucketList = new JButton(ADD_TO_BUCKET_LIST);
        addToVisitedList = new JButton(ADD_TO_VISITED_LIST);
        removeFromBucketList = new JButton(REMOVE_FROM_BUCKET_LIST);
        moveToVisitedList = new JButton(MOVE_TO_VISITED_LIST);

        travelListIn = new TravelList();
        travelListOut = new TravelList();
    }


    // MODIFIES: this
    // EFFECTS: initiate and set up a menu bar containing menuItems
    //          Each menuItem is associated with an ActionListener for file load and save
    public void createMenu() {

        menuBar = new JMenuBar();
        JMenu topMenu = new JMenu("FILE");
        menuBar.add(topMenu);

        JMenuItem load = new JMenuItem("LOAD");
        JMenuItem save = new JMenuItem("SAVE");
        topMenu.add(load);
        topMenu.add(save);

        this.setJMenuBar(menuBar);

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTravelList();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeTravelList();
            }
        });

    }


    // MODIFIES: this
    // EFFECTS: Load in travelList.json and handle the negativeCostException and file IO Exception
    private void loadTravelList() {

        jsonReader = new JsonReader(JSON_STORE);

        try {
            travelListIn = jsonReader.read();
            travelListOut = travelListIn;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (NegativeCostException ne) {
            System.out.println("Some country appears to have negative travel cost associates...");
        }

        loadingBucketData();
        loadingVisitedData();
    }


    // MODIFIES: this
    // EFFECTS: loading data in the BucketList and assign to the corresponding GUI fields
    public void loadingBucketData() {
        List<Country> bl = travelListIn.getBucketList();
        for (Country c: bl) {
            bucketListModel.addElement(c.getCountryName());
            bucketCountryCost.add(c.getCost());
        }
        bucketSizeInt += travelListIn.numCountriesToGo();
        bucketTotalCost += travelListIn.moneyNeedToSave();
        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
        bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
    }


    // MODIFIES: this
    // EFFECTS: loading data in the visitedList and assign to the corresponding GUI fields
    public void loadingVisitedData() {
        List<Country> vl = travelListIn.getVisitedList();
        for (Country c: vl) {
            visitedListModel.addElement(c.getCountryName());
            visitedCountryCost.add(c.getCost());
        }
        visitedSizeInt += travelListIn.numCountriesVisited();
        visitedTotalCost += travelListIn.moneySpentOnTravel();
        visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
        visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
    }


    // MODIFIES: this
    // EFFECTS: write all panel content to the travelList.json file
    private void writeTravelList() {

        jsonWriter = new JsonWriter(JSON_STORE);

        try {
            jsonWriter.open();
            jsonWriter.write(travelListOut);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: add each individual panel to the container
    //          this pane uses manual coordinates
    public void addComponentsToPane(Container pane) {

        pane.setLayout(null);
        pane.add(imagePanel()).setBounds(0, 0, 500, 300);
        pane.add(countryPanel()).setBounds(0, 300, 500, 200);
        setupBucketListPanel();
        pane.add(bucketListPanel).setBounds(500, 0, 300, 230);
        setupVisitedListPanel();
        pane.add(visitedListPanel).setBounds(500, 230, 300, 200);

    }


    // MODIFIES: this
    // EFFECTS: construct an image panel to the top left of the frame and
    //          process the intake image to a smaller size as an image icon to the image panel
    public JPanel imagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);

        // set up a world map on the top left of the frame
        JLabel worldLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./data/world-map-2500.jpg");
        Image bigMap = imageIcon.getImage();
        Image smallMap = bigMap.getScaledInstance(450, 250, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(smallMap);
        worldLabel.setIcon(imageIcon);
        imagePanel.add(worldLabel).setBounds(20, 0, 500, 300);

        return imagePanel;
    }


    // MODIFIES: this
    // EFFECTS: construct a panel to the bottom left of the frame
    //          set up label and text field for create a new country in this application
    //          set up two buttons with action listener to add the country into either one of the lists
    public JPanel countryPanel() {

        JPanel countryPanel = new JPanel();
        countryPanel.setLayout(null);

        JLabel countryNameLabel = new JLabel("Country Name:");
        countryPanel.add(countryNameLabel).setBounds(20, 10, 100, 25);
        countryPanel.add(countryName).setBounds(130, 10, 100, 25);

        JLabel countryCostLabel = new JLabel("Country Cost:");
        countryPanel.add(countryCostLabel).setBounds(20, 50, 100, 25);
        countryPanel.add(countryCost).setBounds(130, 50, 100, 25);

        addToBucketList.addActionListener(new AddListener(bucketListModel, bucketJList, bucketCountryCost,1));
        countryPanel.add(addToBucketList).setBounds(250, 10, 150, 25);

        addToVisitedList.addActionListener(new AddListener(visitedListModel, visitedJList, visitedCountryCost, 2));
        countryPanel.add(addToVisitedList).setBounds(250, 50, 150, 25);

        return countryPanel;
    }


    // EFFECTS: return a JScrollPane
    //          this is a helper method to be used by both BucketList and VisitedList
    public JScrollPane makeScrollPane(JList myJList) {
        myJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myJList.setSelectedIndex(0);
        myJList.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(myJList);
        return listScrollPane;
    }

    // MODIFIES: this
    // EFFECTS: construct a panel to the top right of the frame
    //          place bucket list, it's associated information, and a remove button special to bucketList
    public void setupBucketListPanel() {

        bucketListPanel = new JPanel();
        bucketListPanel.setLayout(null);

        JLabel buLabel = new JLabel("Your Bucket List");
        bucketListPanel.add(buLabel).setBounds(0, 20, 100, 20);

        JScrollPane bucketScrollPane = makeScrollPane(bucketJList);
        bucketListPanel.add(bucketScrollPane).setBounds(0, 50, 100, 100);

        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
        bucketListPanel.add(bucketSizeLabel).setBounds(110, 50, 140, 30);

        bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        bucketListPanel.add(bucketTotalCostLabel).setBounds(110, 90, 140, 30);

        removeFromBucketList.setEnabled(false);
        removeFromBucketList.addMouseListener(new RemoveAdapter());
        bucketListPanel.add(removeFromBucketList).setBounds(10, 165, 200, 25);

        moveToVisitedList.setEnabled(false);
        moveToVisitedList.addMouseListener(new RemoveAdapter());
        bucketListPanel.add(moveToVisitedList).setBounds(10, 200, 200, 25);
    }

    // MODIFIES: this
    // EFFECTS: construct a panel to the bottom right of the frame
    //          place visited list, and it's associated information, no remove button associated
    public void setupVisitedListPanel() {

        visitedListPanel = new JPanel();
        visitedListPanel.setLayout(null);

        JLabel viLabel = new JLabel("Your Visited List");
        visitedListPanel.add(viLabel).setBounds(0, 20, 100, 20);

        JScrollPane visitedScrollPanel = makeScrollPane(visitedJList);
        visitedListPanel.add(visitedScrollPanel).setBounds(0, 50, 100, 120);

        visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
        visitedListPanel.add(visitedSizeLabel).setBounds(110, 50, 140, 30);

        visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        visitedListPanel.add(visitedTotalCostLabel).setBounds(110, 90, 140, 30);
    }

    //  method required by ListSelectionListener
    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (!e.getValueIsAdjusting()) {

            if (bucketJList.getSelectedIndex() == -1) {
                //No selection, disable remove and move button
                removeFromBucketList.setEnabled(false);
                moveToVisitedList.setEnabled(false);
            } else {
                //Selection, enable the remove and move button.
                removeFromBucketList.setEnabled(true);
                moveToVisitedList.setEnabled(true);
            }
        }
    }


    // This listener Class is built following the Java8 Component-ListDemo Project.
    // Remove listener is only used by bucket list. The country on visited list can not be removed.
    private class RemoveAdapter extends MouseAdapter {

        private int index;
        private String name;
        private int selectedCost;

        public void mouseClicked(MouseEvent e) {

            // get index and value of the selected item and remove by index
            index = bucketJList.getSelectedIndex();
            name = bucketJList.getSelectedValue().toString();
            selectedCost = bucketCountryCost.get(index);

            bucketListModel.remove(index);
            bucketCountryCost.remove(index);
            updateBucketListLabels();

            // no matter which button clicked, remove this item in the output travelList bucket list
            try {
                Country tempCountry = new Country(name, selectedCost);
                travelListOut.deleteCountryToGo(tempCountry);

                // if clicked moveToVisitedList, update the visited list labels and add to output visited list
                if (e.getComponent().getBounds().equals(moveToVisitedList.getBounds())) {
                    visitedListModel.addElement(name);
                    visitedCountryCost.add(selectedCost);
                    updateVisitedListLabels();
                    travelListOut.addCountryVisited(tempCountry);
                }
            } catch (NegativeCostException ex) {
                // this won't happen if a country is already displaying on the screen
            }

            // once empty, disable the remove button
            if (bucketSizeInt == 0) {
                removeFromBucketList.setEnabled(false);
            }
        }


        // MODIFIES: this
        // EFFECTS: update the label displays for bucket list
        public void updateBucketListLabels() {
            bucketSizeInt--;
            bucketTotalCost -= selectedCost;
            bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
            bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        }

        // MODIFIES: this
        // EFFECTS: update the label display for visited list
        public void updateVisitedListLabels() {
            visitedSizeInt++;
            visitedTotalCost += selectedCost;
            visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
            visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        }
    }


    // This listener is built following the Java8 Component-ListDemo Project
    // Class AddListener is shared by the text field and the add button for Bucket and Visited Lists
    private class AddListener implements ActionListener {

        private DefaultListModel modelList;
        private List costList;
        private JList pairedJList;
        private int whichList;
        String countryNameString;
        int countryCostInteger;

        // Class constructor
        // at construction, programmer identify the list they intended to modify
        public AddListener(DefaultListModel modelList, JList pairedJList, List costList, int whichList) {
            this.modelList = modelList;
            this.pairedJList = pairedJList;
            this.costList = costList;
            this.whichList = whichList;
        }

        // MODIFIES: this
        // EFFECTS: construct a new country and add the country to one of the two lists
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
            costList.add(countryCostInteger);

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
            countryName.setText("");
            countryCost.setText("");
        }


        // MODIFIES: this
        // EFFECTS: update the label displays for bucket list
        public void updateBucketListLabels() {
            bucketSizeInt++;
            bucketTotalCost += countryCostInteger;
            bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
            bucketTotalCostLabel.setText("$ need to save: " + bucketTotalCost);
        }


        // MODIFIES: this
        // EFFECTS: update the label display for visited list
        public void updateVisitedListLabels() {
            visitedSizeInt++;
            visitedTotalCost += countryCostInteger;
            visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
            visitedTotalCostLabel.setText("$ spent on travel: " + visitedTotalCost);
        }
    }
}
