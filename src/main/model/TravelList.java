package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/* CPSC 210 Term Project:
 * Travel Record
 * Author: Yun Xing
 * Date: October 10, 2021

 * TravelList contain two lists:
 * one is bucket list - containing countries the user wish to go in the future
 * one is visited list - containing countries the user have been to in the past

 * In bucket list:
 * countries can be added or removed from this list
 * countries need to be unique

 * In visited list:
 * countries can be added but not removed from this list
 * countries need to be unique

 * For both lists, a user will be able to
 * - view all the country names
 * - get number of countries on the list and
 * - get the total travel cost
 */

// Represents a travel list with two lists and each containing a collection of countries
public class TravelList implements Writable {

    private List<Country> bucketList;
    private List<Country> visitedList;


    // EFFECTS: construct a travelList with empty bucketList and visitedList
    public TravelList() {
        bucketList = new ArrayList<>();
        visitedList = new ArrayList<>();
    }

    // getter for bucketList
    public List<Country> getBucketList() {
        return bucketList;
    }

    // getter for visitedList
    public List<Country> getVisitedList() {
        return visitedList;
    }

    // MODIFIES: this
    // EFFECTS: a country can only on be present on the bucket list once
    //          if the country is not on the bucket list, add the country to the bucketList and return true
    //          if the country is on the bucket list, return false
    public boolean addCountryToGo(Country country) {
        if (bucketList.contains(country)) {
            return false;
        } else {
            bucketList.add(country);
            return true;
        }
    }


    // MODIFIES: this
    // EFFECTS: if the country is on the bucket list, delete the country from the bucket list and return true
    //          if the country is not on the bucket list, return false
    public boolean deleteCountryToGo(Country country) {
        if (bucketList.contains(country)) {
            bucketList.remove(country);
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
    private List<String> countriesNames(List<Country> list) {
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
    public int numCountriesToGo() {
        return bucketList.size();
    }


    // EFFECTS: return the total number of countries visited.
    public int numCountriesVisited() {
        return visitedList.size();
    }


    // helper method
    // EFFECTS: return the total travel cost of a list of Countries
    private int travelCost(List<Country> list) {
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


    // EFFECTS: return a JSONObject containing two lists
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Bucket List", countriesToJson(bucketList));
        json.put("Visited List", countriesToJson(visitedList));
        return json;
    }


    // EFFECTS: return a JSONArray containing countries
    public JSONArray countriesToJson(List<Country> list) {
        JSONArray jsonArray = new JSONArray();
        for (Country c : list) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
