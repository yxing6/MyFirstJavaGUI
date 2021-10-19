package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CountryTest {
    Country country;

    @BeforeEach
    void setup() {
        country = new Country("Canada", 5000);
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
}
