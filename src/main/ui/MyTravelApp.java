package ui;

import model.Country;
import model.TravelList;
import model.exception.NegativeCostException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


// Travel info application
// This UI is built with reference to the TellAPP application and the JsonSerializationDemo example

public class MyTravelApp {

    private static final String JSON_STORE = "./data/travels.json";
    private TravelList travelList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the travel info application
    public MyTravelApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runAgent();
    }


    //MODIFIES: this
    // EFFECTS: processes user input
    private void runAgent() {
        boolean keepAdding = true;
        String commandA;

        init();

        while (keepAdding) {
            displayMenu();
            System.out.print("\t");
            commandA = input.next();
            commandA = commandA.toLowerCase();

            if (commandA.equals("q")) {
                keepAdding = false;
            } else {
                processCommandA(commandA);
            }
        }
        System.out.println("\n\tGreat! Come back to me in the future to update your travel list!");
        System.out.println("\n-----------------------------------------------------------------------------");
    }


    // EFFECTS: constructs new travel list
    private void init() {
        travelList = new TravelList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view: view the travel countries and budget in my list");
        System.out.println("\tm -> modify: modify the travel list or budget");
        System.out.println("\ts -> modify: save the travel list");
        System.out.println("\tl -> modify: load the travel list");
        System.out.println("\tq -> quit: skip action now and come back later!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommandA(String command) {
        if (command.equals("v")) {
            viewTravelList();
        } else if (command.equals("m")) {
            modifyTravelList();
        } else if (command.equals("l")) {
            loadTravelList();
        } else if (command.equals("s")) {
            saveTravelList();
        } else {
            System.out.println("\tSelection not valid... Return to the main menu");
        }
        System.out.println("\n-----------------------------------------------------------------------------");
    }


    // EFFECTS: print existing travel list to console
    private void viewTravelList() {

        if (travelList.numCountriesVisited() == 0) {
            System.out.println("\n\tYour visited list is empty. You haven't been to any countries... not yet!");
        } else {
            String countriesVisited = travelList.countriesVisited().toString();
            System.out.println("\n\tThe countries on your visited list are:" + countriesVisited);
            int numberOfCountry = travelList.numCountriesVisited();
            System.out.println("\tYou have visited " + numberOfCountry + " countries!");
            int moneySpent = travelList.moneySpentOnTravel();
            System.out.println("\tThe money you have spent on travel is: $" + moneySpent);
        }

        if (travelList.numCountriesToGo() == 0) {
            System.out.println("\n\tYour bucket list is empty. Explore where you want to go!");
        } else {
            String countriesToGo = travelList.countriesToGo().toString();
            System.out.println("\n\tThe countries on your bucket list are:" + countriesToGo);
            int numberOfCountry = travelList.numCountriesToGo();
            System.out.println("\tYou have " + numberOfCountry + " countries on your bucket list");
            int moneyNeedToSave = travelList.moneyNeedToSave();
            System.out.println("\tThe money you need to save for future travel is: $" + moneyNeedToSave);
        }
    }


    // EFFECTS: prompt user for name of the list to modify
    private void modifyTravelList() {

        String commandB;

        System.out.println("\n\tSelect from:");
        System.out.println("\t\tb -> I want to modify my bucket list.");
        System.out.println("\t\tv -> I want to modify my visited list.");
        System.out.print("\t\t");
        commandB = input.next();
        commandB = commandB.toLowerCase();

        if (commandB.equals("b")) {
            modifyBucketList();
        } else if (commandB.equals("v")) {
            modifyVisitedList();
        } else {
            System.out.println("\t\tSelection not valid... Return to the main menu");
        }
    }


    // EFFECTS:  to construct a new country base on user input
    //           throw NegativeCostException
    private Country createCountry() throws NegativeCostException {
        String countryName;
        int travelCost;
        Country myCountry;

        System.out.println("\t\t\tEnter the country name: ");
        System.out.print("\t\t\t");
        countryName = input.next();
        System.out.println("\t\t\tEnter the travel cost as an positive integer: ");
        System.out.print("\t\t\t");
        travelCost = input.nextInt();

        myCountry = new Country(countryName, travelCost);

        return myCountry;
    }


    // EFFECTS:  prompt user for action to bucket list
    private void modifyBucketList() {
        String commandC;

        System.out.println("\n\t\tSelect from:");
        System.out.println("\t\t\ta -> I want to add one country to my bucket list.");
        System.out.println("\t\t\td -> I want to delete one country from my bucket list.");
        System.out.print("\t\t\t");
        commandC = input.next();
        commandC = commandC.toLowerCase();

        if (commandC.equals("a")) {
            modifyBucketListAdd();
        } else if (commandC.equals("d")) {
            modifyBucketListDelete();
        } else {
            System.out.println("\t\t\tSelection not valid... Return to the main menu");
        }
    }


    // MODIFIES: this
    // EFFECTS:  add a new country to the bucket list from console input
    //           catch NegativeCostException
    //           if a user tries to add an existing country onto the bucket list, an error message will display
    private void modifyBucketListAdd() {
        Country myCountry;
        boolean result;

        try {
            myCountry = createCountry();
            result = travelList.addCountryToGo(myCountry);
            if (result) {
                System.out.println("\n\t\t\tThis new country has been added on to your bucket list!");
            } else {
                System.out.println("\n\t\t\tThis country exist on your bucket list already :), plan to go there soon!");
            }
        } catch (NegativeCostException e) {
            System.out.println("Sorry, can't add a country with negative travel cost associated");
        }

    }


    // MODIFIES: this
    // EFFECTS:  delete a country from bucket list from console input
    //           catch NegativeCostException
    //           if a user tries to delete a country that is not ont the bucket list, an error message will display
    private void modifyBucketListDelete() {
        Country myCountry;
        boolean result;

        try {
            myCountry = createCountry();
            result = travelList.deleteCountryToGo(myCountry);
            if (result) {
                System.out.println("\n\t\t\tThis country has been removed from your bucket list!");
            } else {
                System.out.println("\n\t\t\tSorry, I can't remove a country that is not on your bucket list. T_T");
            }
        } catch (NegativeCostException e) {
            System.out.println("Sorry, can't delete a country with negative travel cost associated");
        }
    }


    // MODIFIES: this
    // EFFECTS:  add a new country to the Visited list from console input
    //           catch NegativeCostException
    //           if this country exist on the bucket list, it will be removed from the bucket list
    //           if this country exist on the visited list, travel cost will be updated to the total cost
    private void modifyVisitedList() {
        Country myCountry;
        boolean result;

        try {
            myCountry = createCountry();
            result = travelList.addCountryVisited(myCountry);
            if (result) {
                System.out.println("\n\t\t\tYay! This country has been added to your visit list!");
            } else {
                System.out.println("\n\t\t\tYou have been to this country before, I only updated your travel cost.");
            }
        } catch (NegativeCostException e) {
            System.out.println("Sorry, can't add a country with negative travel cost associated");
        }
    }


    // EFFECTS: saves the travel list to file
    //          catch FileNotFoundException
    private void saveTravelList() {
        try {
            jsonWriter.open();
            jsonWriter.write(travelList);
            jsonWriter.close();
            System.out.println("Saved the travel list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads travel list from file
    //          catch FileNotFoundException and NegativeCostException
    private void loadTravelList() {
        try {
            travelList = jsonReader.read();
            System.out.println("Loaded the travel list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (NegativeCostException ne) {
            System.out.println("Some country appears to have negative travel cost associates...");
        }
    }

}
