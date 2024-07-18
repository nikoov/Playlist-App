
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void renamePlaylist(String newName) {
        this.name = newName;
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

    public void shuffleSongs() {
        Collections.shuffle(songs);
    }
}



