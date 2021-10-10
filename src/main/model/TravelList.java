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
    // EFFECTS:  to create a new country to be added or deleted from travel lists.
    public Country newCountry(String countryCode, String countryName, int travelCost) {
        return new Country(countryCode, countryName, travelCost);
    }


    // MODIFIES: this
    // EFFECTS: if the country is not on the bucket list, add the country to the bucketList and return true
    //          if the country is on the bucket list, return false
    public boolean addCountryToGo(Country country) {
        List<String> names = countriesToGo();
        String countryName = country.getCountryName();
        if (names.contains(countryName)) {
            return false;
        } else {
            bucketList.add(country);
            return true;
        }
    }


    // MODIFIES: this
    // EFFECTS: if the country is on the bucket list, delete a country from the bucket list and return true
    //          if the country is not on the bucket list, return false
    public boolean deleteCountryToGo(Country country) {
        List<String> names = countriesToGo();
        String countryName = country.getCountryName();
        if (names.contains(countryName)) {
            int index = names.indexOf(countryName);
            bucketList.remove(index);
            return true;
        } else {
            return false;
        }
    }


    // MODIFIES: this
    // EFFECTS: if the country is on the bucket list remove this country from bucketList
    //          if the country is on the visitedList, only update the travel cost return false
    //          if the country is not on the visitedList, add this new country to the visitedList return true
    public boolean addCountryVisited(Country country) {
        List<String> names = countriesVisited();
        String countryName = country.getCountryName();
        deleteCountryToGo(country);
        if (names.contains(countryName)) {
            int index = names.indexOf(countryName);         // find the index of object and modify its fields
            int oldCost = visitedList.get(index).getCost();
            int newCost = country.getCost();
            int totalCost = oldCost + newCost;
            visitedList.get(index).changeCost(totalCost);
            return false;
        } else {
            visitedList.add(country);
            return true;
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