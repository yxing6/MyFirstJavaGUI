package persistence;

import model.Country;
import model.TravelList;

import static org.junit.jupiter.api.Assertions.*;


// JsonTest.java is built with reference to the JsonSerializationDemo example created by Paul Carter

public class JsonTest {
    protected void checkCountry(String countryName, int travelCost, Country country) {
        assertEquals(countryName, country.getCountryName());
        assertEquals(travelCost, country.getCost());
    }
}
