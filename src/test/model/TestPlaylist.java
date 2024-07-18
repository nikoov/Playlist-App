package model;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlaylist {
    @Test
    public void testPlaylist() {
        Playlist playlist = new Playlist("My Favorites");
        Song song1 = new Song("Imagine", "John Lennon", "Rock");
        Song song2 = new Song("Bohemian Rhapsody", "Queen", "Rock");

        playlist.addSong(song1);
        playlist.addSong(song2);

        List<Song> songs = playlist.viewSongs();
        assertEquals(2, songs.size());

        playlist.removeSong(song1);
        assertEquals(1, playlist.viewSongs().size());

        playlist.renamePlaylist("Rock Classics");
        assertEquals("Rock Classics", playlist.getName());
    }
}


