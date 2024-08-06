package persistence;

import model.Song;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSong(String title, String artist, String category, Song song) {
        assertEquals(title, song.getName());
        assertEquals(artist, song.getArtist());
        assertEquals(category, song.getCategory());
    }
}
