package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TravelListTest {
    TravelList travelList;

    @BeforeEach
    void setup() {
        travelList = new TravelList();
    }

    @Test
    void testAddOneCountryToGoOnEmpty() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        Boolean result = travelList.addCountryToGo(countryA);
        assertTrue(result);
        assertFalse(travelList.countriesToGo().isEmpty());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(5000, travelList.moneyNeedToSave());
    }


    @Test
    void testAddOneCountryToGoWithExistingCountry() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        Boolean resultA = travelList.addCountryToGo(countryA);
        assertTrue(resultA);
        Country countryB = travelList.newCountry("CAD", "Canada", 6000);
        Boolean resultB = travelList.addCountryToGo(countryB);
        assertFalse(resultB);
    }


    @Test
    void testDeleteOneCountryToGo() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        travelList.addCountryToGo(countryA);
        travelList.deleteCountryToGo(countryA);
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
    }


    @Test
    void testDeleteCountryToGoWithNonExistingCountry() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        travelList.addCountryToGo(countryA);
        assertFalse(travelList.countriesToGo().isEmpty());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(5000, travelList.moneyNeedToSave());
        Country countryB = travelList.newCountry("CHN", "China", 5000);
        Boolean result = travelList.deleteCountryToGo(countryB);
        assertFalse(result);
    }


    @Test
    void testAddMoreCountryToGo() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        Country countryB = travelList.newCountry("CHN", "China", 6000);
        Country countryC = travelList.newCountry("BEL", "Belgium", 5000);
        travelList.addCountryToGo(countryA);
        travelList.addCountryToGo(countryB);
        travelList.addCountryToGo(countryC);
        List<String> list = travelList.countriesToGo();
        assertFalse(list.isEmpty());
        assertEquals(3, travelList.numCountiesToGo());
        assertEquals(16000, travelList.moneyNeedToSave());
    }


    @Test
    void testAddCountryVisitedWithUnique() {
        assertTrue(travelList.countriesVisited().isEmpty());
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(0, travelList.moneySpentOnTravel());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        travelList.addCountryVisited(countryA);
        assertFalse(travelList.countriesVisited().isEmpty());
        assertEquals(1, travelList.numCountiesVisited());
        assertEquals(5000, travelList.moneySpentOnTravel());
    }


    @Test
    void testAddCountryVisitedWithExisting() {
        assertTrue(travelList.countriesVisited().isEmpty());
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(0, travelList.moneySpentOnTravel());
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        Country countryB = travelList.newCountry("CHN", "China", 6000);
        Country countryC = travelList.newCountry("BEL", "Belgium", 5000);
        travelList.addCountryVisited(countryA);
        travelList.addCountryVisited(countryB);
        travelList.addCountryVisited(countryC);
        assertFalse(travelList.countriesVisited().isEmpty());
        assertEquals(3, travelList.numCountiesVisited());
        assertEquals(16000, travelList.moneySpentOnTravel());
        Country countryD = travelList.newCountry("CHN", "China", 4000);
        Boolean result = travelList.addCountryVisited(countryD);
        assertEquals(3, travelList.numCountiesVisited());
        assertEquals(20000, travelList.moneySpentOnTravel());

    }

    @Test
    void testAddOneCountryVisitedTypical() {
        Country countryA = travelList.newCountry("CAD", "Canada", 5000);
        travelList.addCountryToGo(countryA);
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneySpentOnTravel());
        assertEquals(5000, travelList.moneyNeedToSave());
        travelList.addCountryVisited(countryA);
        assertEquals(1, travelList.numCountiesVisited());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(5000, travelList.moneySpentOnTravel());
        assertEquals(0, travelList.moneyNeedToSave());
    }

    @Test
    void testAddMoreCountryVisitedTypical() {
        Country countryA = travelList.newCountry("CAD", "Canada", 4000);
        Country countryB = travelList.newCountry("CHN", "China", 5000);
        Country countryC = travelList.newCountry("BEL", "Belgium", 6000);
        travelList.addCountryToGo(countryA);
        travelList.addCountryToGo(countryB);
        travelList.addCountryToGo(countryC);
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(3, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneySpentOnTravel());
        assertEquals(15000, travelList.moneyNeedToSave());
        travelList.addCountryVisited(countryB);
        travelList.addCountryVisited(countryC);
        assertEquals(2, travelList.numCountiesVisited());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(11000, travelList.moneySpentOnTravel());
        assertEquals(4000, travelList.moneyNeedToSave());
    }
}