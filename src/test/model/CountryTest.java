package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CountryTest {
    Country country;

    @Test
    void testGetCountryCode() {
        country = new Country("CAD", "Canada", 5000);
        assertEquals("CAD", country.getCountryCode());
    }

    @Test
    void testGetCost() {
        country = new Country("CAD", "Canada", 5000);
        assertEquals(5000, country.getCost());
    }

    @Test
    void testChangeCost() {
        country = new Country("CAD", "Canada", 5000);
        country.changeCost(6500);
        assertEquals(6500, country.getCost());
    }
}
