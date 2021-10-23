package model;

import model.exception.NegativeCostException;
import org.json.JSONArray;
import org.json.JSONObject;
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
        assertTrue(travelList.countriesVisited().isEmpty());
        assertEquals(0, travelList.numCountriesVisited());
        assertEquals(0, travelList.moneySpentOnTravel());
    }


    @Test
    void testAddOneCountryToGoOnEmpty() {
        try {
            Country countryA = new Country("Canada", 5000);
            boolean result = travelList.addCountryToGo(countryA);
            assertTrue(result);
            assertFalse(travelList.countriesToGo().isEmpty());
            assertEquals(1, travelList.numCountriesToGo());
            assertEquals(5000, travelList.moneyNeedToSave());
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testAddOneCountryToGoWithExistingCountry() {
        try {
            Country countryA = new Country("Canada", 5000);
            boolean resultA = travelList.addCountryToGo(countryA);
            assertTrue(resultA);
            Country countryB = new Country("Canada", 1000);
            boolean resultB = travelList.addCountryToGo(countryB);
            assertFalse(resultB);
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testDeleteOneCountryToGo() {
        try {
            Country countryA = new Country("Canada", 5000);
            travelList.addCountryToGo(countryA);
            travelList.deleteCountryToGo(countryA);
            assertTrue(travelList.countriesToGo().isEmpty());
            assertEquals(0, travelList.numCountriesToGo());
            assertEquals(0, travelList.moneyNeedToSave());
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testDeleteCountryToGoWithNonExistingCountry() {
        try {
            Country countryA = new Country("Canada", 5000);
            travelList.addCountryToGo(countryA);
            assertFalse(travelList.countriesToGo().isEmpty());
            assertEquals(1, travelList.numCountriesToGo());
            assertEquals(5000, travelList.moneyNeedToSave());
            Country countryB = new Country("China", 5000);
            boolean result = travelList.deleteCountryToGo(countryB);
            assertFalse(result);
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testAddMoreCountryToGo() {
        try {
            Country countryA = new Country("Canada", 5000);
            Country countryB = new Country("China", 6000);
            Country countryC = new Country("Belgium", 5000);
            travelList.addCountryToGo(countryA);
            travelList.addCountryToGo(countryB);
            travelList.addCountryToGo(countryC);
            List<String> list = travelList.countriesToGo();
            assertFalse(list.isEmpty());
            assertEquals(3, travelList.numCountriesToGo());
            assertEquals(16000, travelList.moneyNeedToSave());
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testAddCountryVisitedWithUnique() {
        try {
            Country countryA = new Country("Canada", 5000);
            travelList.addCountryVisited(countryA);
            assertFalse(travelList.countriesVisited().isEmpty());
            assertEquals(1, travelList.numCountriesVisited());
            assertEquals(5000, travelList.moneySpentOnTravel());
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testAddCountryVisitedWithExisting() {
        try {
            Country countryA = new Country("Canada", 5000);
            Country countryB = new Country("China", 6000);
            Country countryC = new Country("Belgium", 5000);
            travelList.addCountryVisited(countryA);
            travelList.addCountryVisited(countryB);
            travelList.addCountryVisited(countryC);
            assertFalse(travelList.countriesVisited().isEmpty());
            assertEquals(3, travelList.numCountriesVisited());
            assertEquals(16000, travelList.moneySpentOnTravel());
            Country countryD = new Country("China", 4000);
            boolean result = travelList.addCountryVisited(countryD);
            assertEquals(3, travelList.numCountriesVisited());
            assertEquals(20000, travelList.moneySpentOnTravel());
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }

    }


    @Test
    void testAddOneCountryVisitedTypical() {
        try {
            Country countryA = new Country( "Canada", 5000);
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
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testAddMoreCountryVisitedTypical() {
        try {
            Country countryA = new Country("Canada", 4000);
            Country countryB = new Country("China", 5000);
            Country countryC = new Country("Belgium", 6000);
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
        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }
    }


    @Test
    void testToJson() {
        try {
            Country countryA = new Country("Canada", 4000);
            Country countryB = new Country("China", 5000);
            Country countryC = new Country("Belgium", 6000);
            travelList.addCountryToGo(countryA);
            travelList.addCountryToGo(countryB);
            travelList.addCountryVisited(countryC);

            JSONObject jsonObject = travelList.toJson();
            JSONArray jsonArrayBucket = jsonObject.getJSONArray("Bucket List");
            JSONObject jsonCountryA = jsonArrayBucket.getJSONObject(0);
            JSONObject jsonCountryB = jsonArrayBucket.getJSONObject(1);
            JSONArray jsonArrayVisited = jsonObject.getJSONArray("Visited List");
            assertEquals("Canada", jsonCountryA.getString("Country Name"));
            assertEquals(5000, jsonCountryB.getInt("Travel Cost"));
            assertEquals(1, jsonArrayVisited.toList().size());

        } catch (NegativeCostException e) {
            fail("Caught unexpected NegativeCostException while the travel cost is valid");
        }

    }

}