package model;

import model.exception.NegativeCostException;
import org.json.JSONObject;
import persistence.Writable;

public class Country implements Writable {

    private String countryName;
    private int travelCost;

    //REQUIRED: travelCost should be a positive integer value
    // EFFECTS: constructs a country with associated code and trip cost
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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Country Name", countryName);
        json.put("Travel Cost", travelCost);

        return json;
    }
}
