package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a playlist containing a list of songs.
 */
public class Playlist {
    private String name;
    private List<Song> songs;

    /**
     * Constructs a playlist with the given name.
     *
     * @param name the name of the playlist
     * @requires name != null
     * @modifies this
     * @effects constructs a new Playlist object with an empty list of songs
     */
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    /**
     * Returns the name of the playlist.
     *
     * @return the name of the playlist
     * @effects returns the name of the playlist
     */
    public String getName() {
        return name;
    }

    /**
     * Renames the playlist to the given new name.
     *
     * @param newName the new name for the playlist
     * @modifies this
     * @effects updates the name of the playlist to the given new name
     */
    public void renamePlaylist(String newName) {
        this.name = newName;
    }

    /**
     * Adds a song to the playlist.
     *
     * @param song the song to add
     * @requires song != null
     * @modifies this
     * @effects adds the given song to the playlist
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Removes a song from the playlist.
     *
     * @param song the song to remove
     * @requires song != null
     * @modifies this
     * @effects removes the given song from the playlist
     */
    public void removeSong(Song song) {
        songs.remove(song);
    }

    /**
     * Returns a new list containing all songs in the playlist.
     *
     * @return a list of songs in the playlist
     * @effects returns a new list containing all songs in the playlist
     */
    public List<Song> viewSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Shuffles the order of songs in the playlist.
     *
     * @modifies this
     * @effects randomly shuffles the order of songs in the playlist
     */
    public void shuffleSongs() {
        Collections.shuffle(songs);
    }
}

