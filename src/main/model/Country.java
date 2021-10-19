package model;

public class Country {

    private String countryName;
    private int travelCost;

    //REQUIRED: travelCost should be a positive integer value
    // EFFECTS: constructs a country with associated code and trip cost
    public Country(String countryName, int travelCost) {
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
}
