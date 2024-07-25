package persistence;

import model.Playlist;
import model.Song;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    void testWriterPlaylists() {
        try {
            // Create a sample map of playlists
            Map<String, Playlist> playlists = new HashMap<>();
            Playlist playlist1 = new Playlist("Rock Classics");
            playlist1.addSong(new Song("Bohemian Rhapsody", "Queen", "Rock"));
            playlist1.addSong(new Song("Hotel California", "Eagles", "Rock"));

            Playlist playlist2 = new Playlist("Jazz Favorites");
            playlist2.addSong(new Song("So What", "Miles Davis", "Jazz"));
            playlist2.addSong(new Song("Take Five", "Dave Brubeck", "Jazz"));

            playlists.put("Rock Classics", playlist1);
            playlists.put("Jazz Favorites", playlist2);

            // Convert the map to a JSONObject
            JSONObject json = new JSONObject();
            for (Map.Entry<String, Playlist> entry : playlists.entrySet()) {
                json.put(entry.getKey(), entry.getValue().toJson());
            }

            // Write to file
            JsonWriter writer = new JsonWriter("./data/testWriterPlaylists.json");
            writer.open();
            writer.write(json);
            writer.close();

            // Read back from file
            JsonReader reader = new JsonReader("./data/testWriterPlaylists.json");
            JSONObject readJson = reader.read();
            for (String key : json.keySet()) {
                JSONObject expectedJson = json.getJSONObject(key);
                JSONObject actualJson = readJson.getJSONObject(key);
                assertEquals(expectedJson.toString(), actualJson.toString());
            }
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyPlaylists() {
        try {
            // Create an empty map of playlists
            Map<String, Playlist> playlists = new HashMap<>();
            JSONObject json = new JSONObject(); // Empty JSON object

            // Write to file
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlaylists.json");
            writer.open();
            writer.write(json);
            writer.close();

            // Read back from file
            JsonReader reader = new JsonReader("./data/testWriterEmptyPlaylists.json");
            JSONObject readJson = reader.read();
            assertTrue(readJson.isEmpty());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterSinglePlaylist() {
        try {
            // Create a map with a single playlist
            Map<String, Playlist> playlists = new HashMap<>();
            Playlist playlist = new Playlist("Solo Hits");
            playlist.addSong(new Song("Yesterday", "The Beatles", "Pop"));
            playlists.put("Solo Hits", playlist);

            // Convert the map to a JSONObject
            JSONObject json = new JSONObject();
            json.put("Solo Hits", playlist.toJson());

            // Write to file
            JsonWriter writer = new JsonWriter("./data/testWriterSinglePlaylist.json");
            writer.open();
            writer.write(json);
            writer.close();

            // Read back from file
            JsonReader reader = new JsonReader("./data/testWriterSinglePlaylist.json");
            JSONObject readJson = reader.read();
            JSONObject expectedJson = json.getJSONObject("Solo Hits");
            JSONObject actualJson = readJson.getJSONObject("Solo Hits");
            assertEquals(expectedJson.toString(), actualJson.toString());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }


}
