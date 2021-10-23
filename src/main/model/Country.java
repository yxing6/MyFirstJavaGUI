package model;

import model.exception.NegativeCostException;
import org.json.JSONObject;
import persistence.Writable;

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
