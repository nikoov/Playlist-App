package model;

import java.util.ArrayList;
import java.util.List;



//Represents a category of songs, each categorized by a name and containing a list of songs.

public class Category {
    private String name;
    private List<Song> songs;


     //REQUIRES: name != null
     //MODIFIES: this
     //EFFECTS: constructs a new Category object with an empty list of songs
    public Category(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }


     //EFFECTS: returns the name of the category
    public String getName() {
        return name;
    }


     //REQUIRES: song != null
     //MODIFIES: this
     //EFFECTS: adds the given song to the list of songs in this category
    public void addSong(Song song) {
        songs.add(song);
    }


     //REQUIRES: song != null
     //MODIFIES:this
     //EFFECTS: removes the given song from the list of songs in this category

    public void removeSong(Song song) {
        songs.remove(song);
    }


    //EFFECTS: returns a new list containing all songs in the category
    public List<Song> viewSongs() {
        return new ArrayList<>(songs);
    }


    //REQUIRES:query != null
    //EFFECTS: returns a list of songs in the category that match the given query
    public List<Song> searchSongs(String query) {
        List<Song> results = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(query.toLowerCase())
                    || song.getArtist().toLowerCase().contains(query.toLowerCase())) {
                results.add(song);
            }
        }
        return results;
    }
}
