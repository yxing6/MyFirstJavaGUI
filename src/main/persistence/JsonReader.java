package persistence;

import model.Country;
import model.TravelList;

import model.exception.NegativeCostException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


// Represents a reader that reads a JSON representation of travel list from a file to program
// JsonRead.java is built with reference to the JsonSerializationDemo example created by Paul Carter

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads travelList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TravelList read() throws IOException, NegativeCostException {

        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseTravelList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses travel list from JSON object and returns it
    private TravelList parseTravelList(JSONObject jsonObject) throws NegativeCostException {

        TravelList travelList = new TravelList();
        addToBucketList(travelList, jsonObject);
        addToVisitedList(travelList,jsonObject);

        return travelList;
    }

    // MODIFIES: travelList
    // EFFECTS: parses countries from JSON object and adds them on to bucket list
    private void addToBucketList(TravelList travelList, JSONObject jsonObject) throws NegativeCostException {

        JSONArray jsonArray = jsonObject.getJSONArray("Bucket List");

        for (Object json : jsonArray) {
            JSONObject nextCountry = (JSONObject) json;
            travelList.addCountryToGo(getCountry(nextCountry));
        }
    }


    // MODIFIES: travelList
    // EFFECTS: parses countries from JSON object and adds them on to bucket list
    private void addToVisitedList(TravelList travelList, JSONObject jsonObject) throws NegativeCostException {

        JSONArray jsonArray = jsonObject.getJSONArray("Visited List");

        for (Object json : jsonArray) {
            JSONObject nextCountry = (JSONObject) json;
            travelList.addCountryVisited(getCountry(nextCountry));
        }
    }


    // EFFECTS: parses and return a country from JSON object
    private Country getCountry(JSONObject jsonObject) throws NegativeCostException {

        String countryName = jsonObject.getString("Country Name");
        int travelCost = jsonObject.getInt("Travel Cost");

        return new Country(countryName, travelCost);
    }
}
