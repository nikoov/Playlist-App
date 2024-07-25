package persistence;

import model.Playlist;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of playlists to file
    public void write(Map<String, Playlist> playlists) {
        JSONObject json = new JSONObject();
        json.put("playlists", toJsonArray(playlists));
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: saves string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // EFFECTS: returns playlists as a JSON array
    private JSONArray toJsonArray(Map<String, Playlist> playlists) {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Playlist> entry : playlists.entrySet()) {
            JSONObject playlistJson = new JSONObject();
            playlistJson.put("name", entry.getKey());
            playlistJson.put("songs", entry.getValue().toJson());
            jsonArray.put(playlistJson);
        }
        return jsonArray;
    }
}
