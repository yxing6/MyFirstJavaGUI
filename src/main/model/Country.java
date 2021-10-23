package model;

import model.exception.NegativeCostException;
import org.json.JSONObject;
import persistence.Writable;


/* CPSC 210 Term Project:
 * Travel Record - Country Class
 * Author: Yun Xing
 * Date: October 10, 2021
 *
 * A country contain two fields - country name and travel cost
 * User should be able to construct a new country with country name and positive travel cost,
 * NegativeCostException will be thrown if travel cost is negative
 * Country is writable class can be written to external files.
 */

public class Country implements Writable {

    private String countryName;
    private int travelCost;


    // EFFECTS: constructs a country with associated code and trip cost
    //          throw NegativeCostException if trying to construct a country with negative travel cost
    public Country(String countryName, int travelCost) throws NegativeCostException {

        if (travelCost <= 0) {
            throw new NegativeCostException();
        }
        this.countryName = countryName;
        this.travelCost = travelCost;
    }


    // EFFECTS: return the country name
    public String getCountryName() {
        return countryName;
    }


    // EFFECTS: return the cost associated to this country
    public int getCost() {
        return travelCost;
    }


    // MODIFIES: this
    // EFFECTS:  change the cost associated to this country
    public void changeCost(int newCost) {
        travelCost = newCost;
    }


    // EFFECTS: return a JSONObject representing a country
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Country Name", countryName);
        json.put("Travel Cost", travelCost);

        return json;
    }
}
