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
    private int bucketListTotalCost;
    private int numberOnBucketList;

    private DefaultListModel visitedListModel;
    private JList visitedJList;
    private int visitedListTotalCost;
    private int numberOnVisitedList;

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

        bucketListModel = new DefaultListModel();
        List<String> bl = travelList.countriesToGo();
        for (String s: bl) {
            bucketListModel.addElement(s);
        }
        bucketJList = new JList(bucketListModel);
        bucketListTotalCost = travelList.moneyNeedToSave();
//        numberOnBucketList = travelList.numCountriesToGo();
        numberOnBucketList = bucketListModel.getSize();

        visitedListModel = new DefaultListModel();
        List<String> vl = travelList.countriesVisited();
        for (String s: vl) {
            visitedListModel.addElement(s);
        }
        visitedJList = new JList(visitedListModel);
        visitedListTotalCost = travelList.moneySpentOnTravel();
        numberOnVisitedList = travelList.numCountriesVisited();
    }


    public void addComponentsToPane(Container pane) {

        pane.setLayout(null);
        pane.add(leftPanel()).setBounds(0, 0, 500, 500);
        pane.add(rightPanel()).setBounds(500, 0, 300, 500);

    }

    public JPanel leftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
//        leftPanel.setBackground(new Color(0x929BA7));

        // set up a world map on the top left of the frame
        JLabel worldLabel = new JLabel();
//        worldLabel.setHorizontalTextPosition(SwingConstants.CENTER);
//        worldLabel.setVerticalTextPosition(SwingConstants.CENTER);
        ImageIcon worldMap = createImageIcon();
        worldLabel.setIcon(worldMap);
        leftPanel.add(worldLabel).setBounds(20, 0, 500, 300);

        // set up label and text field on the bottom left of the frame
        JLabel countryNameLabel = new JLabel("Country Name:");
        leftPanel.add(countryNameLabel).setBounds(20, 310, 100, 25);
        countryName = new JTextField();
        leftPanel.add(countryName).setBounds(130, 310, 100,25);
        JLabel countryCostLabel = new JLabel("Country Cost:");
        leftPanel.add(countryCostLabel).setBounds(20, 350, 100, 25);
        countryCost = new JTextField();
        leftPanel.add(countryCost).setBounds(130, 350, 100,25);

        addToBucketList = new JButton("Add to bucket list");
        AddListener addToBucketListener;
        addToBucketListener = new AddListener(addToBucketList, bucketListModel, bucketJList, bucketListTotalCost);
        addToBucketList.addActionListener(addToBucketListener);
        leftPanel.add(addToBucketList).setBounds(250, 310, 150, 25);

        addToVisitedList = new JButton("Add to visited list");
        AddListener addToVisitedListener;
        addToVisitedListener = new AddListener(addToVisitedList, visitedListModel, visitedJList, visitedListTotalCost);
        addToVisitedList.addActionListener(addToVisitedListener);
        leftPanel.add(addToVisitedList).setBounds(250, 350, 150, 25);

//    {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                {
//                    String countryNameString = countryName.getText();
//                    int countryCostInteger = Integer.parseInt(countryCost.getText());
//
//                    try {
//                        Country country = new Country(countryNameString, countryCostInteger);
//                        travelList.addCountryToGo(country);
//                    } catch (NegativeCostException ex) {
//                        ex.printStackTrace();
//                    }
//
//
//                }
//            }
//        });

        return leftPanel;
    }

    // process the intake image to a smaller size
    public ImageIcon createImageIcon() {

        ImageIcon imageIcon = new ImageIcon("./data/world-map-2500.jpg");
        Image bigMap = imageIcon.getImage();
        Image smallMap = bigMap.getScaledInstance(450, 250, java.awt.Image.SCALE_SMOOTH);
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

    public JPanel rightPanel() {

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);

        JLabel buLabel = new JLabel("Your Bucket List");
        rightPanel.add(buLabel).setBounds(0, 20, 100, 30);
        JScrollPane bucketScrollPane = makeScrollPane(bucketJList);
        rightPanel.add(bucketScrollPane).setBounds(0, 60, 100, 140);

        JLabel numCountryBucket = new JLabel("# of countries: ");
        JLabel numCountryBucketNum = new JLabel(String.valueOf(numberOnBucketList));
        rightPanel.add(numCountryBucket).setBounds(120, 50, 120, 25);
        rightPanel.add(numCountryBucketNum).setBounds(240, 50, 20, 25);


        JLabel sumCostBucket = new JLabel("money to save: " + bucketListTotalCost);
        rightPanel.add(sumCostBucket).setBounds(120, 80, 140, 25);


        JLabel viLabel = new JLabel("Your Visited List");
        rightPanel.add(viLabel).setBounds(0,220, 100, 30);
        JScrollPane visitedScrollPane = makeScrollPane(visitedJList);
        rightPanel.add(visitedScrollPane).setBounds(0, 260, 100, 140);

        removeFromBucketList = new JButton("remove");
        removeFromBucketList.addActionListener(new RemoveListener());
        rightPanel.add(removeFromBucketList).setBounds(120, 120, 100, 25);

        return rightPanel;
    }


    public JScrollPane makeScrollPane(JList myJList) {
        myJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myJList.setSelectedIndex(0);
        myJList.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(myJList);
        return listScrollPane;
    }


    @Override
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


    class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove the selected item.
            int index = bucketJList.getSelectedIndex();
            bucketListModel.remove(index);

            int size = bucketListModel.getSize();

            if (size == 0) {                                // empty list, disable remove button
                removeFromBucketList.setEnabled(false);
            } else {                                        // non-empty list, decrement the size of
                if (index == bucketListModel.getSize()) {        // Select an index.
                    index--;                                //removed item in last position
                }
                bucketJList.setSelectedIndex(index);
                bucketJList.ensureIndexIsVisible(index);
            }
        }

    }


    // This listener is shared by the text field and the add button.
    class AddListener implements ActionListener {
//        private boolean alreadyEnabled = false;
        private JButton button;
        private DefaultListModel modelList;
        private JList pairedList;
        private int costSum;

        public AddListener(JButton button, DefaultListModel modelList, JList pairedList, int costSum) {

//        public AddListener(JButton button) {
            this.button = button;
            this.modelList = modelList;
            this.pairedList = pairedList;
            this.costSum = costSum;
        }

        // Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String countryNameString = countryName.getText();
            int countryCostInteger = Integer.parseInt(countryCost.getText());

            // User didn't type in a unique country name, request attention
            if (countryNameString.equals("") || modelList.contains(countryNameString)) {
                Toolkit.getDefaultToolkit().beep();

                // my previous user story says to update travel cost
                countryName.requestFocusInWindow();
                return;
            }

            // always add to the end of the list
            modelList.addElement(countryName.getText());
//            costSum += countryCostInteger;
//            numberOnBucketList++;

            // Select the new item and make it visible.
            pairedList.setSelectedIndex(pairedList.getLastVisibleIndex());
            pairedList.ensureIndexIsVisible(pairedList.getLastVisibleIndex());

            //Reset the text field.
            countryName.requestFocusInWindow();
            countryName.setText("");
            countryCost.requestFocusInWindow();
            countryCost.setText("");
        }

    }

}
