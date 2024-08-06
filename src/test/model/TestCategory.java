package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCategory {
    @Test
    public void testCategory() {
        Category category = new Category("Rock");
        Song song1 = new Song("Imagine", "John Lennon", "Rock");
        Song song2 = new Song("Bohemian Rhapsody", "Queen", "Rock");

        // Test addSong and viewSongs
        category.addSong(song1);
        category.addSong(song2);

        List<Song> songs = category.viewSongs();
        assertEquals(2, songs.size());

        // Test removeSong
        category.removeSong(song1);
        assertEquals(1, category.viewSongs().size());

        // Test searchSongs
        List<Song> searchResults = category.searchSongs("Queen");
        assertEquals(1, searchResults.size());
        assertEquals("Bohemian Rhapsody", searchResults.get(0).getName());
    }
}
