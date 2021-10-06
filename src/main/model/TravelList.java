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
    // EFFECTS: adds a new country to the bucketList
    public void addCountryToGo(String countryCode, String countryName, int travelCost) {
        Country country = new Country(countryCode, countryName, travelCost);
        bucketList.add(country);
    }

    // EFFECTS: return a list of strings represent the countries on the bucketList
    public List<String> countriesToGo() {
        List<String> countryNames = new ArrayList<>();
        for (Country next: bucketList) {
            countryNames.add(next.getCountryName());
        }
        return countryNames;
    }

    // EFFECTS: return the total number of countries in bucketList.
    public int numCountiesToGo() {
        return bucketList.size();
    }

    // EFFECTS: return the total money need to save for future travelling
    public int moneyNeedToSave() {
        int totalMoney = 0;
        for (Country next: bucketList) {
            totalMoney += next.getCost();
        }
        return totalMoney;
    }

    // REQUIRES: travelCost > 0
    // MODIFIES: this
    // EFFECTS: adds a new country to the visitedList and
    //          if the country is on the bucket list remove this country from bucketList
    public void addCountryVisited(String countryCode, String countryName, int travelCost) {
        Country country = new Country(countryCode, countryName, travelCost);
        visitedList.add(country);
        bucketList.remove(country);
    }

    // EFFECTS: return a list of strings represent the countries on the bucketList
    public List<String> countriesVisited() {
        List<String> countryNames = new ArrayList<>();
        for (Country next: visitedList) {
            countryNames.add(next.getCountryName());
        }
        return countryNames;
    }

    // EFFECTS: return the total number of countries visited.
    public int numCountiesVisited() {
        return visitedList.size();
    }

    // EFFECTS: return the total money spent on travelling
    public int moneySpentOnTravel() {
        int totalMoney = 0;
        for (Country next: visitedList) {
            totalMoney += next.getCost();
        }
        return totalMoney;
    }

}
