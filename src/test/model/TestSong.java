package model;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestSong {
    @Test
    public void testSong() {
        Song song = new Song("Imagine", "John Lennon", "Rock");
        assertEquals("Imagine", song.getTitle());
        assertEquals("John Lennon", song.getArtist());
        assertEquals("Rock", song.getCategory());
    }
}
