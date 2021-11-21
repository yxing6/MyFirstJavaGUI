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
        EventLog.getInstance().logEvent(new Event("Travel Cost for " + this.toString() + "has changed to " + newCost));
    }


    // EFFECTS: return true is two country object have the same name
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        // Two countries are the same if their name are the same
        Country anotherCountry = (Country) o;
        return countryName.equals(anotherCountry.getCountryName());
    }

    @Override
    public int hashCode() {
        return countryName.hashCode();
    }

    // EFFECTS: return a String representation of the country;
    @Override
    public String toString() {
        return "Country(" + countryName + ")";
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
