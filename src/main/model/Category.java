package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a category of songs, each categorized by a name and containing a list of songs.
 */
public class Category {
    private String name;
    private List<Song> songs;

    /**
     * Constructs a category with the given name.
     *
     * @param name the name of the category
     * @requires name != null
     * @modifies this
     * @effects constructs a new Category object with an empty list of songs
     */
    public Category(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    /**
     * Returns the name of the category.
     *
     * @return the name of the category
     * @effects returns the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a song to the category.
     *
     * @param song the song to add
     * @requires song != null
     * @modifies this
     * @effects adds the given song to the list of songs in this category
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Removes a song from the category.
     *
     * @param song the song to remove
     * @requires song != null
     * @modifies this
     * @effects removes the given song from the list of songs in this category
     */
    public void removeSong(Song song) {
        songs.remove(song);
    }

    /**
     * Returns a new list containing all songs in the category.
     *
     * @return a list of songs in the category
     * @effects returns a new list containing all songs in the category
     */
    public List<Song> viewSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Searches for songs in the category that match the given query string.
     * Matches are case-insensitive and can be either in the song title or artist.
     *
     * @param query the search query (song title or artist)
     * @return a list of songs matching the query
     * @requires query != null
     * @effects returns a list of songs in the category that match the given query
     */
    public List<Song> searchSongs(String query) {
        List<Song> results = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    song.getArtist().toLowerCase().contains(query.toLowerCase())) {
                results.add(song);
            }
        }
        return results;
    }
}
