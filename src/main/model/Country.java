package model;

public class Country {

    private String countryCode;
    private String countryName;
    private int travelCost;

    //REQUIRED: countryCode need to be a three letter String in CAPITAL contained in CountryCodeList
    // EFFECTS: constructs a country with associated code and trip cost
    public Country(String countryCode, String countryName, int travelCost) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.travelCost = travelCost;
    }

    // EFFECTS: return the country name
    public String getCountryName() {
        return countryName;
    }


    // EFFECTS: return the unique countryCode
    public String getCountryCode() {
        return countryCode;
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
