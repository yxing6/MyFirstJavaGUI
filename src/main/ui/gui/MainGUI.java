package ui.gui;


public class MainGUI {
    private static final String frameTitle = "My Travel App";

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(frameTitle);
    }

}

////        travelList = new TravelList();
////        try {
////            Country countryA = new Country("Canada", 4000);
////            Country countryB = new Country("China", 5000);
////            Country countryC = new Country("Belgian", 6000);
////            Country countryD = new Country("Belgia", 6000);
////            Country countryE = new Country("Belgi", 6000);
////            Country countryF = new Country("Belg", 6000);
////            Country countryG = new Country("Bel", 6000);
////
////            travelList.addCountryToGo(countryA);
////            travelList.addCountryVisited(countryB);
////            travelList.addCountryVisited(countryC);
////            travelList.addCountryVisited(countryD);
////            travelList.addCountryVisited(countryE);
////            travelList.addCountryVisited(countryF);
////            travelList.addCountryVisited(countryG);
////
////        } catch (NegativeCostException e) {
////            fail("Caught unexpected NegativeCostException while the travel cost is valid");
////        }
//
//
////        bucketList = new JList((Vector) travelList.getBucketList());
////        visitedList = new JList((Vector) travelList.getVisitedList());
////        JScrollPane bucketScrollPane = createScrollPanel(new JList((Vector) travelList.getBucketList()));
////
////        JScrollPane visitedScrollPane = createScrollPanel(new JList((Vector) travelList.getVisitedList()));



