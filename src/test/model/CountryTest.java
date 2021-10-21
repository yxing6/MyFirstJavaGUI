package model;

import model.exception.NegativeCostException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CountryTest {
    Country country;

    @BeforeEach
    void setup() {
        try {
            country = new Country("Canada", 5000);
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testCreateCountryWithNegativeCostExpectException() {
        try {
            Country countryA = new Country("Canada", -5000);
            fail("Should not reach this line when travel cost is negative");
        } catch (NegativeCostException e) {
            // expected
        }

    }


    @Test
    void testGetCountryName() {
        assertEquals("Canada", country.getCountryName());
    }


    @Test
    void testGetCost() {
        assertEquals(5000, country.getCost());
    }


    @Test
    void testChangeCost() {
        country.changeCost(6500);
        assertEquals(6500, country.getCost());
    }


    @Test
    void testToJson() {
        JSONObject json = country.toJson();
        assertEquals("Canada", json.get("Country Name"));
        assertEquals(5000, json.get("Travel Cost"));
    }

}
