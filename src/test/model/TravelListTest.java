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
    void testConstructor() {
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountriesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
    }

    @Test
    void testAddOneCountryToGoOnEmpty() {
        Country countryA = travelList.newCountry("Canada", 5000);
        boolean result = travelList.addCountryToGo(countryA);
        assertTrue(result);
        assertFalse(travelList.countriesToGo().isEmpty());
        assertEquals(1, travelList.numCountriesToGo());
        assertEquals(5000, travelList.moneyNeedToSave());
    }


    @Test
    void testAddOneCountryToGoWithExistingCountry() {
        Country countryA = travelList.newCountry("Canada", 5000);
        boolean resultA = travelList.addCountryToGo(countryA);
        assertTrue(resultA);
        Country countryB = travelList.newCountry("Canada", 6000);
        boolean resultB = travelList.addCountryToGo(countryB);
        assertFalse(resultB);
    }


    @Test
    void testDeleteOneCountryToGo() {
        Country countryA = travelList.newCountry("Canada", 5000);
        travelList.addCountryToGo(countryA);
        travelList.deleteCountryToGo(countryA);
        assertTrue(travelList.countriesToGo().isEmpty());
        assertEquals(0, travelList.numCountriesToGo());
        assertEquals(0, travelList.moneyNeedToSave());
    }


    @Test
    void testDeleteCountryToGoWithNonExistingCountry() {
        Country countryA = travelList.newCountry("Canada", 5000);
        travelList.addCountryToGo(countryA);
        assertFalse(travelList.countriesToGo().isEmpty());
        assertEquals(1, travelList.numCountriesToGo());
        assertEquals(5000, travelList.moneyNeedToSave());
        Country countryB = travelList.newCountry("China", 5000);
        boolean result = travelList.deleteCountryToGo(countryB);
        assertFalse(result);
    }


    @Test
    void testAddMoreCountryToGo() {
        Country countryA = travelList.newCountry("Canada", 5000);
        Country countryB = travelList.newCountry("China", 6000);
        Country countryC = travelList.newCountry("Belgium", 5000);
        travelList.addCountryToGo(countryA);
        travelList.addCountryToGo(countryB);
        travelList.addCountryToGo(countryC);
        List<String> list = travelList.countriesToGo();
        assertFalse(list.isEmpty());
        assertEquals(3, travelList.numCountriesToGo());
        assertEquals(16000, travelList.moneyNeedToSave());
    }


    @Test
    void testAddCountryVisitedWithUnique() {
        Country countryA = travelList.newCountry("Canada", 5000);
        travelList.addCountryVisited(countryA);
        assertFalse(travelList.countriesVisited().isEmpty());
        assertEquals(1, travelList.numCountriesVisited());
        assertEquals(5000, travelList.moneySpentOnTravel());
    }


    @Test
    void testAddCountryVisitedWithExisting() {
        Country countryA = travelList.newCountry("Canada", 5000);
        Country countryB = travelList.newCountry("China", 6000);
        Country countryC = travelList.newCountry("Belgium", 5000);
        travelList.addCountryVisited(countryA);
        travelList.addCountryVisited(countryB);
        travelList.addCountryVisited(countryC);
        assertFalse(travelList.countriesVisited().isEmpty());
        assertEquals(3, travelList.numCountriesVisited());
        assertEquals(16000, travelList.moneySpentOnTravel());
        Country countryD = travelList.newCountry("China", 4000);
        boolean result = travelList.addCountryVisited(countryD);
        assertEquals(3, travelList.numCountriesVisited());
        assertEquals(20000, travelList.moneySpentOnTravel());

    }

    @Test
    void testAddOneCountryVisitedTypical() {
        Country countryA = travelList.newCountry( "Canada", 5000);
        travelList.addCountryToGo(countryA);
        assertEquals(0, travelList.numCountriesVisited());
        assertEquals(1, travelList.numCountriesToGo());
        assertEquals(0, travelList.moneySpentOnTravel());
        assertEquals(5000, travelList.moneyNeedToSave());
        travelList.addCountryVisited(countryA);
        assertEquals(1, travelList.numCountriesVisited());
        assertEquals(0, travelList.numCountriesToGo());
        assertEquals(5000, travelList.moneySpentOnTravel());
        assertEquals(0, travelList.moneyNeedToSave());
    }

    @Test
    void testAddMoreCountryVisitedTypical() {
        Country countryA = travelList.newCountry("Canada", 4000);
        Country countryB = travelList.newCountry("China", 5000);
        Country countryC = travelList.newCountry("Belgium", 6000);
        travelList.addCountryToGo(countryA);
        travelList.addCountryToGo(countryB);
        travelList.addCountryToGo(countryC);
        assertEquals(0, travelList.numCountriesVisited());
        assertEquals(3, travelList.numCountriesToGo());
        assertEquals(0, travelList.moneySpentOnTravel());
        assertEquals(15000, travelList.moneyNeedToSave());
        travelList.addCountryVisited(countryB);
        travelList.addCountryVisited(countryC);
        assertEquals(2, travelList.numCountriesVisited());
        assertEquals(1, travelList.numCountriesToGo());
        assertEquals(11000, travelList.moneySpentOnTravel());
        assertEquals(4000, travelList.moneyNeedToSave());
    }
}