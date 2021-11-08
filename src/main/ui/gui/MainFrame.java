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
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class MainFrame extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private TravelList travelList;
    private DefaultListModel bucketList;
    private DefaultListModel visitedList;
    private JTextField countryName;
    private JTextField countryCost;
    private JButton addToBucketList;
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

        bucketList = new DefaultListModel();
        List<String> bl = travelList.countriesToGo();
        for (String s: bl) {
            bucketList.addElement(s);
        }

        visitedList = new DefaultListModel();
        List<String> vl = travelList.countriesVisited();
        for (String s: vl) {
            visitedList.addElement(s);
        }
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
        JLabel countryName = new JLabel("Country Name:");
        leftPanel.add(countryName).setBounds(20, 310, 120, 25);
        this.countryName = new JTextField();
        leftPanel.add(this.countryName).setBounds(150, 310, 120,25);
        JLabel countryCost = new JLabel("Country Cost:");
        leftPanel.add(countryCost).setBounds(20, 350, 120, 25);
        this.countryCost = new JTextField();
        leftPanel.add(this.countryCost).setBounds(150, 350, 120,25);

        this.addToBucketList = new JButton("Add this country to your bucket list");
        leftPanel.add(addToBucketList).setBounds(200, 400, 150, 25);
        addToBucketList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    String countryNameString = countryName.getText();
                    int countryCostInteger = Integer.parseInt(countryCost.getText());

                    try {
                        Country country = new Country(countryNameString, countryCostInteger);
                    } catch (NegativeCostException ex) {
                        ex.printStackTrace();
                    }


                }
            }
        });

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
        JList buList = new JList(bucketList);
        buList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        buList.setSelectedIndex(0);
//        buList.addListSelectionListener(this);
//        buList.setVisibleRowCount(5);
//        JScrollPane listScrollPane = new JScrollPane(buList);

        JLabel viLabel = new JLabel("Your Visited List");
        JList viList = new JList(visitedList);
        viList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        viList.setSelectedIndex(0);
//        viList.addListSelectionListener(this);
//        viList.setVisibleRowCount(5);
//        JScrollPane listScrollPane2 = new JScrollPane(viList);
        rightPanel.add(buLabel).setBounds(0, 0, 100, 30);
        rightPanel.add(buList).setBounds(0, 40, 100, 150);
        rightPanel.add(viLabel).setBounds(0,200, 100, 30);
        rightPanel.add(viList).setBounds(0, 240, 100, 150);

        return rightPanel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

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
