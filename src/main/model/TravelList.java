package model;

import java.util.ArrayList;
import java.util.List;

public class TravelList {

    private List<Country> bucketList;
    private List<Country> visitedList;

    // EFFECTS: construct a travelList with empty bucketList and visitedList
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


    // REQUIRES: travelCost > 0
    // MODIFIES: this
    // EFFECTS: adds a new country to the visitedList and
    //          if the country is on the bucket list remove this country from bucketList
    public void addCountryVisited(String countryCode, String countryName, int travelCost) {
        Country country = new Country(countryCode, countryName, travelCost);
        visitedList.add(country);
        List<String> names = countriesToGo();
        if (names.contains(countryName)) {
            int index = names.indexOf(countryName);
            bucketList.remove(index);
        }
    }


    // a helper method
    // EFFECTS: return a list of country names on a list of Countries
    public List<String> countriesNames(List<Country> list) {
        List<String> names = new ArrayList<>();
        for (Country next: list) {
            names.add(next.getCountryName());
        }
        return names;
    }


    // EFFECTS: return a list of strings represent the countries on the bucketList
    public List<String> countriesToGo() {
        return countriesNames(bucketList);
    }


    // EFFECTS: return a list of strings represent the countries on the visitedList
    public List<String> countriesVisited() {
        return countriesNames(visitedList);
    }



    // EFFECTS: return the total number of countries in bucketList.
    public int numCountiesToGo() {
        return bucketList.size();
    }


    // EFFECTS: return the total number of countries visited.
    public int numCountiesVisited() {
        return visitedList.size();
    }


    // helper method
    // EFFECTS: return the total travel cost of a list of Countries
    public int travelCost(List<Country> list) {
        int totalMoney = 0;
        for (Country next: list) {
            totalMoney += next.getCost();
        }
        return totalMoney;
    }


    // EFFECTS: return the total money need to save for future travelling
    public int moneyNeedToSave() {
        return travelCost(bucketList);
    }


    // EFFECTS: return the total money spent on travelling
    public int moneySpentOnTravel() {
        return travelCost(visitedList);
    }

}
