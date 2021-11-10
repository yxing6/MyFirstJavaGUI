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
        bucketListTotalCost = travelList.moneyNeedToSave();
        numberOnBucketList = travelList.numCountriesToGo();

        visitedListModel = new DefaultListModel();
        List<String> vl = travelList.countriesVisited();
        for (String s: vl) {
            visitedListModel.addElement(s);
        }
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
        leftPanel.add(countryNameLabel).setBounds(20, 310, 120, 25);
        countryName = new JTextField();
        leftPanel.add(countryName).setBounds(150, 310, 120,25);
        JLabel countryCostLabel = new JLabel("Country Cost:");
        leftPanel.add(countryCostLabel).setBounds(20, 350, 120, 25);
        countryCost = new JTextField();
        leftPanel.add(countryCost).setBounds(150, 350, 120,25);

        addToBucketList = new JButton("Add to your bucket list");
        AddListener addToBucketListener = new AddListener(addToBucketList);
//        AddListener addToBucketListener;
//        addToBucketListener = new AddListener(addToBucketList, bucketListModel, bucketJList, bucketListTotalCost);
        addToBucketList.addActionListener(addToBucketListener);
        leftPanel.add(addToBucketList).setBounds(200, 400, 150, 25);

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
        bucketJList = new JList(bucketListModel);
        JScrollPane bucketScrollPane = makeScrollPane(bucketJList);

        JLabel viLabel = new JLabel("Your Visited List");
        visitedJList = new JList(visitedListModel);
        JScrollPane visitedScrollPane = makeScrollPane(visitedJList);

        removeFromBucketList = new JButton("remove");
        removeFromBucketList.addActionListener(new RemoveListener());

        rightPanel.add(buLabel).setBounds(0, 20, 100, 30);
        rightPanel.add(bucketScrollPane).setBounds(0, 60, 100, 140);
        rightPanel.add(viLabel).setBounds(0,220, 100, 30);
        rightPanel.add(visitedScrollPane).setBounds(0, 260, 100, 140);
        rightPanel.add(removeFromBucketList).setBounds(120, 60, 90, 25);

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
                //No selection, disable fire button.
                removeFromBucketList.setEnabled(false);
            } else {
                //Selection, enable the fire button.
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

//        public AddListener(JButton button, DefaultListModel modelList, JList pairedList, int costSum) {

        public AddListener(JButton button) {
            this.button = button;
//            this.modelList = modelList;
//            this.pairedList = pairedList;
//            this.costSum = costSum;
        }

        // Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String countryNameString = countryName.getText();
            int countryCostInteger = Integer.parseInt(countryCost.getText());

            // User didn't type in a unique country name, request attention
            if (countryNameString.equals("") || bucketListModel.contains(countryNameString)) {
                Toolkit.getDefaultToolkit().beep();

                // my previous user story says to update travel cost
                countryName.requestFocusInWindow();
                return;
            }

            int index = bucketJList.getSelectedIndex(); // get selected index
            if (index == -1) { // no selection, so insert at beginning
                index = 0;
            } else {           // add after the selected item
                index++;
            }

            bucketListModel.insertElementAt(countryName.getText(), index);
            costSum += countryCostInteger;

            //Select the new item and make it visible.
            bucketJList.setSelectedIndex(index);
            bucketJList.ensureIndexIsVisible(index);

            //Reset the text field.
            countryName.requestFocusInWindow();
            countryName.setText("");
            countryCost.requestFocusInWindow();
            countryCost.setText("");
        }

//        //This method tests for string equality. You could certainly
//        //get more sophisticated about the algorithm.  For example,
//        //you might want to ignore white space and capitalization.
//        protected boolean alreadyInList(String name) {
//            return listModel.contains(name);
//        }
//
//        //Required by DocumentListener.
//        public void insertUpdate(DocumentEvent e) {
//            enableButton();
//        }
//
//        //Required by DocumentListener.
//        public void removeUpdate(DocumentEvent e) {
//            handleEmptyTextField(e);
//        }
//
//        //Required by DocumentListener.
//        public void changedUpdate(DocumentEvent e) {
//            if (!handleEmptyTextField(e)) {
//                enableButton();
//            }
//        }
//
//        private void enableButton() {
//            if (!alreadyEnabled) {
//                button.setEnabled(true);
//            }
//        }
//
//        private boolean handleEmptyTextField(DocumentEvent e) {
//            if (e.getDocument().getLength() <= 0) {
//                button.setEnabled(false);
//                alreadyEnabled = false;
//                return true;
//            }
//            return false;
//        }
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
