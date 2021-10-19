package persistence;

import org.json.JSONObject;

// Writable.java follows example from JsonSerializationDemo created by Paul Carter

public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
