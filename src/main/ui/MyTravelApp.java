package ui;

import model.Country;
import model.TravelList;

import java.util.Scanner;


// Travel info application
// This UI is built with reference to the TellAPP application

public class MyTravelApp {
    private TravelList travelList;
    private Scanner input;

    // EFFECTS: runs the travel info application
    public MyTravelApp() {
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


    private void init() {
        travelList = new TravelList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view: I want to view the travel countries and budget in my list");
        System.out.println("\tm -> modify: I want to modify the travel list or budget");
        System.out.println("\tq -> quit: I will skip action now and come back later!");
    }


    private void processCommandA(String command) {
        if (command.equals("v")) {
            viewTravelList();
            System.out.println("\n-----------------------------------------------------------------------------");
        } else if (command.equals("m")) {
            modifyTravelList();
            System.out.println("\n-----------------------------------------------------------------------------");
        } else {
            System.out.println("\tSelection not valid... Return to the main menu");
            System.out.println("\n-----------------------------------------------------------------------------");
        }
    }


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
    private Country createCountry() {
        String countryName;
        int travelCost;
        Country myCountry;

        System.out.println("\t\t\tEnter the country name: ");
        System.out.print("\t\t\t");
        countryName = input.next();
        System.out.println("\t\t\tEnter the travel cost as an positive integer: ");
        System.out.print("\t\t\t");
        travelCost = input.nextInt();

        if (travelCost <= 0) {
            System.out.println("\t\t\tEnter the travel cost as an POSITIVE integer: ");
            System.out.print("\t\t\t");
            travelCost = input.nextInt();
        }

        myCountry = travelList.newCountry(countryName, travelCost);
        return myCountry;

    }


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

    private void modifyBucketListAdd() {
        Country myCountry;
        boolean result;

        myCountry = createCountry();
        result = travelList.addCountryToGo(myCountry);
        if (result) {
            System.out.println("\n\t\t\tThis new country has been added on to your bucket list!");
        } else {
            System.out.println("\n\t\t\tThis country exist on your bucket list already :), plan to go there soon!");
        }

    }

    private void modifyBucketListDelete() {
        Country myCountry;
        boolean result;

        myCountry = createCountry();
        result = travelList.deleteCountryToGo(myCountry);
        if (result) {
            System.out.println("\n\t\t\tThis country has been removed from your bucket list!");
        } else {
            System.out.println("\n\t\t\tSorry, I can't remove a country that is not on your bucket list. T_T");
        }
    }


    private void modifyVisitedList() {
        Country myCountry;
        boolean result;

        myCountry = createCountry();
        result = travelList.addCountryVisited(myCountry);
        if (result) {
            System.out.println("\n\t\t\tYay! This country has been added to your visit list!");
        } else {
            System.out.println("\n\t\t\tYou have been to this country before, I only updated your travel cost.");
        }
    }
}
