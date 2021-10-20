package persistence;

import model.Country;
import model.TravelList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


// JsonWriteTest.java is built with reference to the JsonSerializationDemo example created by Paul Carter

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFileExpectException() {
        try {
            TravelList travelList = new TravelList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expecting IOException
        }
    }

    @Test
    void testWriterEmptyTravelListNoException() {
        try {
            TravelList travelListOut = new TravelList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTravelList.json");
            writer.open();
            writer.write(travelListOut);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTravelList.json");
            TravelList travelListIn = reader.read();
            assertEquals(0, travelListIn.numCountriesToGo());
            assertEquals(0, travelListIn.numCountriesVisited());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTravelListNoException() {
        try {

            TravelList travelListOut = new TravelList();

            Country countryA = new Country("Canada", 4000);
            Country countryB = new Country("China", 5000);
            Country countryC = new Country("Belgium", 6000);
            travelListOut.addCountryToGo(countryA);
            travelListOut.addCountryToGo(countryB);
            travelListOut.addCountryVisited(countryC);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTravelList.json");
            writer.open();
            writer.write(travelListOut);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTravelList.json");

            TravelList travelListIn = reader.read();

            assertEquals(2, travelListIn.numCountriesToGo());
            assertEquals(9000, travelListIn.moneyNeedToSave());
            assertTrue(travelListIn.countriesToGo().contains("China"));

            assertEquals(1, travelListIn.numCountriesVisited());
            assertEquals(6000, travelListIn.moneySpentOnTravel());
            assertTrue(travelListIn.countriesVisited().contains("Belgium"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}