package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Song> songs;

    public Category(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public List<Song> viewSongs() {
        return new ArrayList<>(songs);
    }
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
