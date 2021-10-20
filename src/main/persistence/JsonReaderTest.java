package persistence;

import model.Country;
import model.TravelList;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNoneExistFileExpectException() {

        JsonReader reader = new JsonReader("./data/noSuchFile.json");

        try {
            TravelList travelListIn = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expecting IOException
        }
    }


    @Test
    void testReaderEmptyTravelListNoException() {

        TravelList travelListOut = new TravelList();
        JsonWriter writer = new JsonWriter("./data/testReaderEmptyTravelList.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Not expecting IOException "); // not the main purpose of this unit test
        }
        writer.write(travelListOut);
        writer.close();


        JsonReader reader = new JsonReader("./data/testReaderEmptyTravelList.json");

        try {
            TravelList travelListIn = reader.read();
            assertEquals(0, travelListIn.numCountriesToGo());
            assertEquals(0, travelListIn.numCountriesVisited());
        } catch (IOException e) {
            fail("Not expecting IOException");
        }
    }


    @Test
    void testReaderGeneralTravelListNoException() {

        TravelList travelListOut = new TravelList();

        Country countryA = new Country("Canada", 4000);
        Country countryB = new Country("China", 5000);
        Country countryC = new Country("Belgium", 6000);
        travelListOut.addCountryToGo(countryA);
        travelListOut.addCountryToGo(countryB);
        travelListOut.addCountryVisited(countryC);


        JsonWriter writer = new JsonWriter("./data/testReaderEmptyTravelList.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Not expecting IOException "); // not the main purpose of this unit test
        }
        writer.write(travelListOut);
        writer.close();

        JsonReader reader = new JsonReader("./data/testReaderEmptyTravelList.json");

        try {
            TravelList travelListIn = reader.read();

            assertFalse(travelListIn.countriesToGo().isEmpty());
            assertEquals(2, travelListIn.numCountriesToGo());
            assertEquals(9000, travelListIn.moneyNeedToSave());
            assertTrue(travelListIn.countriesToGo().contains("China"));

            assertFalse(travelListIn.countriesVisited().isEmpty());
            assertEquals(1, travelListIn.numCountriesVisited());
            assertEquals(6000, travelListIn.moneySpentOnTravel());
            assertTrue(travelListIn.countriesVisited().contains("Belgium"));

        } catch (IOException e) {
            fail("Not expecting IOException");
        }
    }


}
