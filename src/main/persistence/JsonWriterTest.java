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

    private static final String FILE_PATH_PLAYLISTS =
            "./data/testWriterPlaylists.json";
    private static final String FILE_PATH_EMPTY_PLAYLISTS =
            "./data/testWriterEmptyPlaylists.json";
    private static final String FILE_PATH_SINGLE_PLAYLIST =
            "./data/testWriterSinglePlaylist.json";

    @Test
    void testWriterPlaylists() {
        Map<String, Playlist> playlists = createSamplePlaylists();

        try {
            writePlaylistsToFile(playlists, FILE_PATH_PLAYLISTS);
            Map<String, Playlist> readPlaylists =
                    readPlaylistsFromFile(FILE_PATH_PLAYLISTS);

            assertEquals(playlists.size(), readPlaylists.size());

            for (String key : playlists.keySet()) {
                assertTrue(readPlaylists.containsKey(key));
                assertEquals(playlists.get(key).toJson().toString(),
                        readPlaylists.get(key).toJson().toString());
            }
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyPlaylists() {
        Map<String, Playlist> playlists = new HashMap<>();

        try {
            writePlaylistsToFile(playlists, FILE_PATH_EMPTY_PLAYLISTS);
            Map<String, Playlist> readPlaylists =
                    readPlaylistsFromFile(FILE_PATH_EMPTY_PLAYLISTS);

            assertTrue(readPlaylists.isEmpty());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterSinglePlaylist() {
        Map<String, Playlist> playlists = createSinglePlaylist();

        try {
            writePlaylistsToFile(playlists, FILE_PATH_SINGLE_PLAYLIST);
            Map<String, Playlist> readPlaylists =
                    readPlaylistsFromFile(FILE_PATH_SINGLE_PLAYLIST);

            assertEquals(1, readPlaylists.size());
            assertTrue(readPlaylists.containsKey("Solo Hits"));
            assertEquals(playlists.get("Solo Hits").toJson().toString(),
                    readPlaylists.get("Solo Hits").toJson().toString());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    private Map<String, Playlist> createSamplePlaylists() {
        Map<String, Playlist> playlists = new HashMap<>();
        Playlist playlist1 = new Playlist("Rock Classics");
        playlist1.addSong(new Song("Bohemian Rhapsody", "Queen", "Rock"));
        playlist1.addSong(new Song("Hotel California", "Eagles", "Rock"));

        Playlist playlist2 = new Playlist("Jazz Favorites");
        playlist2.addSong(new Song("So What", "Miles Davis", "Jazz"));
        playlist2.addSong(new Song("Take Five", "Dave Brubeck", "Jazz"));

        playlists.put("Rock Classics", playlist1);
        playlists.put("Jazz Favorites", playlist2);

        return playlists;
    }

    private Map<String, Playlist> createSinglePlaylist() {
        Map<String, Playlist> playlists = new HashMap<>();
        Playlist playlist = new Playlist("Solo Hits");
        playlist.addSong(new Song("Yesterday", "The Beatles", "Pop"));
        playlists.put("Solo Hits", playlist);

        return playlists;
    }

    private void writePlaylistsToFile(Map<String, Playlist> playlists,
                                      String filePath)
            throws FileNotFoundException, IOException {
        JsonWriter writer = new JsonWriter(filePath);
        writer.open();
        writer.write(playlists);
        writer.close();
    }

    private Map<String, Playlist> readPlaylistsFromFile(String filePath)
            throws FileNotFoundException, IOException {
        JsonReader reader = new JsonReader(filePath);
        return reader.read();
    }
}
