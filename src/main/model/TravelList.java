package model;

import java.util.ArrayList;
import java.util.List;

public class TravelList {

    private List<Country> bucketList;
    private List<Country> visitedList;

    public TravelList() {
        bucketList = new ArrayList<>();
        visitedList = new ArrayList<>();
    }

    // REQUIRES: travelCost > 0
    // MODIFIES: this
    // EFFECTS: adds a new country to the visitedList and remove this country from bucketList
    public void addCountryVisited(String countryCode, String countryName, int travelCost) {
        // stub
    }

    // EFFECTS: return a list of strings represent the countries on the bucketList
    public List<String> countriesVisited() {
        List<String> countryNames = new ArrayList<>();
        // stub
        return countryNames;
    }

    // EFFECTS: return the total number of countries visited.
    public int numCountiesVisited() {
        // stud
        return 0;
    }

    // EFFECTS: return the total money spent on travelling
    public int moneySpentOnTravel() {
        // stud
        return 0;
    }



    // REQUIRES: travelCost > 0
    // MODIFIES: this
    // EFFECTS: adds a new country to the bucketList
    public void addCountryToGo(String countryCode, String countryName, int travelCost) {
        // stub
    }

    // EFFECTS: return a list of strings represent the countries on the bucketList
    public List<String> countriesToGo() {
        List<String> countryNames = new ArrayList<>();
        // stub
        return countryNames;
    }

    // EFFECTS: return the total number of countries in bucketList.
    public int numCountiesToGo() {
        // stud
        return 0;
    }

    // EFFECTS: return the total money need to save for future travelling
    public int moneyNeedToSave() {
        // stud
        return 0;
    }
}
