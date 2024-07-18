package model;


import model.Category;
import model.Song;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestCategory {
    @Test
    public void testCategory() {
        Category category = new Category("Rock");
        Song song1 = new Song("Imagine", "John Lennon", "Rock");
        Song song2 = new Song("Bohemian Rhapsody", "Queen", "Rock");

        category.addSong(song1);
        category.addSong(song2);

        List<Song> songs = category.viewSongs();
        assertEquals(2, songs.size());

        category.removeSong(song1);
        assertEquals(1, category.viewSongs().size());
    }
}