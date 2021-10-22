package persistence;

import model.Country;
import model.TravelList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkCountry(String countryName, int travelCost, Country country) {
        assertEquals(countryName, country.getCountryName());
        assertEquals(travelCost, country.getCost());
    }
}
