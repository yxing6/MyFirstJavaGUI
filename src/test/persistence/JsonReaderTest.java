package persistence;

import model.Country;
import model.TravelList;
import model.exception.NegativeCostException;
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
        } catch (NegativeCostException e) {
            fail("IO Exception should be caught, and should not reach this line of code.");
        }
    }

    @Test
    void testReaderEmptyTravelListNoException() {

        try {
            TravelList travelListOut = new TravelList();
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyTravelList.json");
            writer.open();
            writer.write(travelListOut);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyTravelList.json");

            TravelList travelListIn = reader.read();
            assertEquals(0, travelListIn.numCountriesToGo());
            assertEquals(0, travelListIn.numCountriesVisited());
        } catch (IOException e) {
            fail("Not expecting IOException");
        } catch (NegativeCostException e) {
            fail("NegativeCostException should not been thrown");
        }
    }


    @Test
    void testReaderGeneralTravelListNoException() {

        try {
            TravelList travelListOut = new TravelList();
            Country countryA = new Country("Germany", 4000);
            Country countryB = new Country("Thailand", 5000);
            Country countryC = new Country("Russia", 6000);
            travelListOut.addCountryToGo(countryA);
            travelListOut.addCountryToGo(countryB);
            travelListOut.addCountryVisited(countryC);

            JsonWriter writer = new JsonWriter("./data/testReaderGeneralTravelList.json");
            writer.open();
            writer.write(travelListOut);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralTravelList.json");
            TravelList travelListIn = reader.read();

            assertEquals(2, travelListIn.numCountriesToGo());
            assertEquals(9000, travelListIn.moneyNeedToSave());
            assertTrue(travelListIn.countriesToGo().contains("Germany"));

            assertEquals(1, travelListIn.numCountriesVisited());
            assertEquals(6000, travelListIn.moneySpentOnTravel());
            assertTrue(travelListIn.countriesVisited().contains("Russia"));

        } catch (IOException e) {
            fail("Not expecting IOException");
        } catch (NegativeCostException e) {
            fail("NegativeCostException should not been thrown");
        }
    }


}
