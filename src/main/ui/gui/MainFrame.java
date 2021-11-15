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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;


public class MainFrame extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private TravelList travelList;

    private DefaultListModel bucketListModel;
    private JList bucketJList;
    private JLabel bucketSizeLabel;
    private int bucketSizeInt;

    private DefaultListModel visitedListModel;
    private JList visitedJList;
    private JLabel visitedSizeLabel;
    private int visitedSizeInt;

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
        bucketSizeLabel = new JLabel();
        visitedSizeLabel = new JLabel();

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
        bucketSizeInt = bucketListModel.getSize();

        visitedListModel = new DefaultListModel();
        List<String> vl = travelList.countriesVisited();
        for (String s: vl) {
            visitedListModel.addElement(s);
        }
        visitedJList = new JList(visitedListModel);
        visitedSizeInt = visitedListModel.getSize();
    }


    public void addComponentsToPane(Container pane) {

        pane.setLayout(null);
        pane.add(imagePanel()).setBounds(0, 0, 500, 300);
        pane.add(addCountryPanel()).setBounds(0, 300, 500, 200);
        pane.add(bucketListPanel()).setBounds(500, 0, 300, 200);
        pane.add(visitedListPanel()).setBounds(500, 200, 300, 200);

    }


    public JPanel imagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
//        imagePanel.setBackground(new Color(0x929BA7));

        // set up a world map on the top left of the frame
        JLabel worldLabel = new JLabel();
//        worldLabel.setHorizontalTextPosition(SwingConstants.CENTER);
//        worldLabel.setVerticalTextPosition(SwingConstants.CENTER);
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


    // set up label and text field for adding a country in one panel to be located on the bottom left of the frame
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
//        addToBucketList.addActionListener(new AddListener(bucketListModel, bucketJList));
//        addToBucketList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                bucketSizeInt++;
//            }
//        });
        countryPanel.add(addToBucketList).setBounds(250, 10, 150, 25);

        addToVisitedList = new JButton("Add to visited list");
//        addToVisitedList.addActionListener(new AddListener(visitedListModel, visitedJList));
//        addToVisitedList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                visitedSizeInt++;
//            }
//        });
        countryPanel.add(addToVisitedList).setBounds(250, 50, 150, 25);

        return countryPanel;
    }


    public JPanel bucketListPanel() {

        JPanel bucketListPanel = new JPanel();
        bucketListPanel.setLayout(null);

        JLabel buLabel = new JLabel("Your Bucket List");
        bucketListPanel.add(buLabel).setBounds(0, 20, 100, 30);
        JScrollPane bucketScrollPane = makeScrollPane(bucketJList);
        bucketListPanel.add(bucketScrollPane).setBounds(0, 60, 100, 140);

        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
        bucketListPanel.add(bucketSizeLabel).setBounds(120, 50, 200, 25);

        removeFromBucketList = new JButton("remove");
//        removeFromBucketList.addActionListener(new RemoveListener());
//        removeFromBucketList.addMouseListener(new RemoveAdapter());
        bucketListPanel.add(removeFromBucketList).setBounds(120, 120, 100, 25);

        return bucketListPanel;
    }


    public JPanel visitedListPanel() {

        JPanel visitedListPanel = new JPanel();
        visitedListPanel.setLayout(null);

        JLabel viLabel = new JLabel("Your Visited List");
        visitedListPanel.add(viLabel).setBounds(0, 20, 100, 30);
        JScrollPane visitedScrollPanel = makeScrollPane(visitedJList);
        visitedListPanel.add(visitedScrollPanel).setBounds(0, 60, 100, 140);

        visitedSizeLabel.setText("# of countries: " + visitedSizeInt);
        visitedListPanel.add(visitedSizeLabel).setBounds(120, 50, 200, 25);

        return visitedListPanel;
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


    public void updateDisplay() {

        removeFromBucketList.addActionListener(new RemoveListener());
//        removeFromBucketList.addMouseListener(new RemoveAdapter());
//        bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
//        addToBucketList.addActionListener(new AddListener(bucketListModel, bucketJList));
//        addToBucketList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                bucketSizeInt++;
//                bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
//                System.out.println("number on bucketlist:" + bucketSizeInt);
//            }
//        });

    }


//    class RemoveAdapter extends MouseAdapter {
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//
//            if (bucketSizeInt > 0) {
//                bucketSizeInt--;
//                bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
//            }
//            System.out.println("number on bucketlist:" + bucketSizeInt);
//        }
//    }


    class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove the selected item.
            int index = bucketJList.getSelectedIndex();                     // get index of the selected item
            bucketListModel.remove(index);                                  // remove item by index in the ListModel
            bucketSizeInt--;                                                // decrement and display number
            bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
            if (bucketSizeInt == 0) {                                       // once empty, disable remove button
                removeFromBucketList.setEnabled(false);


                //            int size = bucketListModel.getSize();


            }
//            else {                                        // non-empty list, decrement the size of
//                if (index == bucketListModel.getSize()) {        // Select an index.
//                    index--;                                //removed item in last position
//                }
//                bucketJList.setSelectedIndex(index);
//                bucketJList.ensureIndexIsVisible(index);
//            }

//            if (bucketSizeInt > 0) {
//                bucketSizeInt--;
//                bucketSizeLabel.setText("# of countries: " + bucketSizeInt);
//            }
        }

    }


//    class AddAdapter extends MouseAdapter {
//
//        private JLabel myLabel;
////        private int size;
//
//        public AddAdapter(JLabel myLabel, int size, int whichList) {
//            this.myLabel = myLabel;
//            if (whichList == 0) {
//
//            }
////            this.size = size;
//
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            size++;
//            myLabel.setText("# of countries: " + size);
//        }
//    }


    // This listener is shared by the text field and the add button.
    class AddListener implements ActionListener {

        private DefaultListModel modelList;
        private JList pairedJList;

        public AddListener(DefaultListModel modelList, JList pairedJList) {
            this.modelList = modelList;
            this.pairedJList = pairedJList;
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

            // Select the new item and make it visible.
//            pairedJList.setSelectedIndex(pairedJList.getLastVisibleIndex());
//            pairedJList.ensureIndexIsVisible(pairedJList.getLastVisibleIndex());

            //Reset the text field.
            countryName.requestFocusInWindow();
            countryName.setText("");
            countryCost.requestFocusInWindow();
            countryCost.setText("");
        }

    }

}
