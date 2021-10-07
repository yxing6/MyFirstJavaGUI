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
    void testAddOneCountryToGo() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        travelList.addCountryToGo("CAD", "Canada", 5000);
        assertFalse(travelList.countriesToGo().isEmpty());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(5000, travelList.moneyNeedToSave());
    }

    @Test
    void testAddMoreCountryToGo() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
        travelList.addCountryToGo("CAD", "Canada", 5000);
        travelList.addCountryToGo("CHN", "China", 6000);
        travelList.addCountryToGo("BEL", "Belgium", 5000);
        List<String> list = travelList.countriesToGo();
        assertFalse(list.isEmpty());
        assertEquals(3, travelList.numCountiesToGo());
        assertEquals(16000, travelList.moneyNeedToSave());
    }

    @Test
    void testAddOneCountryVisitedOnEmpty() {
        assertTrue(travelList.countriesVisited().isEmpty());
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(0, travelList.moneySpentOnTravel());
        travelList.addCountryVisited("CAD", "Canada", 5000);
        assertFalse(travelList.countriesVisited().isEmpty());
        assertEquals(1, travelList.numCountiesVisited());
        assertEquals(5000, travelList.moneySpentOnTravel());
    }


    @Test
    void testAddMoreCountryVisitedOnEmpty() {
        assertTrue(travelList.countriesVisited().isEmpty());
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(0, travelList.moneySpentOnTravel());
        travelList.addCountryVisited("CAD", "Canada", 5000);
        travelList.addCountryVisited("CHN", "China", 6000);
        travelList.addCountryVisited("BEL", "Belgium", 5000);
        assertFalse(travelList.countriesVisited().isEmpty());
        assertEquals(3, travelList.numCountiesVisited());
        assertEquals(16000, travelList.moneySpentOnTravel());
    }

    @Test
    void testAddOneCountryVisitedTypical() {
        travelList.addCountryToGo("CAD", "Canada", 5000);
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneySpentOnTravel());
        assertEquals(5000, travelList.moneyNeedToSave());
        travelList.addCountryVisited("CAD", "Canada", 5000);
        assertEquals(1, travelList.numCountiesVisited());
        assertEquals(0, travelList.numCountiesToGo());
        assertEquals(5000, travelList.moneySpentOnTravel());
        assertEquals(0, travelList.moneyNeedToSave());
    }

    @Test
    void testAddMoreCountryVisitedTypical() {
        travelList.addCountryToGo("CAD", "Canada", 5000);
        travelList.addCountryToGo("CHN", "China", 6000);
        travelList.addCountryToGo("BEL", "Belgium", 5000);
        assertEquals(0, travelList.numCountiesVisited());
        assertEquals(3, travelList.numCountiesToGo());
        assertEquals(0, travelList.moneySpentOnTravel());
        assertEquals(16000, travelList.moneyNeedToSave());
        travelList.addCountryVisited("CHN", "China", 6000);
        travelList.addCountryVisited("BEL", "Belgium", 5000);
        assertEquals(2, travelList.numCountiesVisited());
        assertEquals(1, travelList.numCountiesToGo());
        assertEquals(11000, travelList.moneySpentOnTravel());
        assertEquals(5000, travelList.moneyNeedToSave());
    }
}