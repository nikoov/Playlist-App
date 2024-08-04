package persistence;

import model.Playlist;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderEmptyPlaylists() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlaylists.json");
        try {
            Map<String, Playlist> playlists = reader.read();
            assertFalse(playlists.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderInvalidPlaylists() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidPlaylists.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderMalformedJson() {
        JsonReader reader = new JsonReader("./data/testReaderMalformedJson.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderCorruptedFile() {
        JsonReader reader = new JsonReader("./data/testReaderCorruptedFile.json");
        assertThrows(IOException.class, reader::read);
    }
}
