package persistence;

import model.Playlist;
import model.Song;
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

            // Write to file
            JsonWriter writer = new JsonWriter("./data/testWriterPlaylists.json");
            writer.open();
            writer.write(playlists);
            writer.close();

            // Read back from file
            JsonReader reader = new JsonReader("./data/testWriterPlaylists.json");
            Map<String, Playlist> readPlaylists = reader.read();
            assertEquals(playlists.size(), readPlaylists.size());
            for (String key : playlists.keySet()) {
                assertTrue(readPlaylists.containsKey(key));
                assertEquals(playlists.get(key).toJson().toString(), readPlaylists.get(key).toJson().toString());
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

            // Write to file
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlaylists.json");
            writer.open();
            writer.write(playlists);
            writer.close();

            // Read back from file
            JsonReader reader = new JsonReader("./data/testWriterEmptyPlaylists.json");
            Map<String, Playlist> readPlaylists = reader.read();
            assertTrue(readPlaylists.isEmpty());
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

            // Write to file
            JsonWriter writer = new JsonWriter("./data/testWriterSinglePlaylist.json");
            writer.open();
            writer.write(playlists);
            writer.close();

            // Read back from file
            JsonReader reader = new JsonReader("./data/testWriterSinglePlaylist.json");
            Map<String, Playlist> readPlaylists = reader.read();
            assertEquals(1, readPlaylists.size());
            assertTrue(readPlaylists.containsKey("Solo Hits"));
            assertEquals(playlists.get("Solo Hits").toJson().toString(), readPlaylists.get("Solo Hits").toJson().toString());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
