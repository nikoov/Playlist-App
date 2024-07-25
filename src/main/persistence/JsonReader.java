package persistence;

import model.Playlist;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads playlists from file and returns it as a map
    // THROWS: IOException if an error occurs while reading the file
    public Map<String, Playlist> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlaylists(jsonObject);
    }

    // EFFECTS: reads the source file as a string
    // THROWS: IOException if an error occurs while reading the file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses playlists from JSON object and returns it as a map
    private Map<String, Playlist> parsePlaylists(JSONObject jsonObject) {
        Map<String, Playlist> playlists = new HashMap<>();
        JSONArray jsonArray = jsonObject.getJSONArray("playlists");
        for (Object obj : jsonArray) {
            JSONObject playlistJson = (JSONObject) obj;
            String name = playlistJson.getString("name");
            Playlist playlist = Playlist.fromJson(playlistJson.getJSONObject("songs"));
            playlists.put(name, playlist);
        }
        return playlists;
    }
}
