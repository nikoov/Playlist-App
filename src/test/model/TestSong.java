package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSong {

    @Test
    public void testSongConstructorAndGetters() {
        // Test constructor and getters
        Song song = new Song("Imagine", "John Lennon", "Rock");

        assertEquals("Imagine", song.getName());
        assertEquals("John Lennon", song.getArtist());
        assertEquals("Rock", song.getCategory());
    }
}
