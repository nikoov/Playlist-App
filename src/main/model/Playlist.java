package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a playlist having a collection of songs
public class Playlist implements Writable {
    private String name;
    private List<Song> songs;

    // EFFECTS: constructs a playlist with a name and empty list of songs
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    // Getters and other methods
    public String getName() {
        return name;
    }

    public List<Song> viewSongs() {
        return Collections.unmodifiableList(songs);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public void renamePlaylist(String newName) {
        this.name = newName;
    }

    public void shuffleSongs() {
        Collections.shuffle(songs);
    }

    public List<Song> searchSongs(String query) {
        List<Song> results = new ArrayList<>();
        for (Song song : songs) {
            if (song.getName().contains(query) || song.getArtist().contains(query)) {
                results.add(song);
            }
        }
        return results;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("songs", songsToJson());
        return json;
    }

    // EFFECTS: returns songs in this playlist as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: creates a Playlist object from a JSONObject
    public static Playlist fromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Playlist playlist = new Playlist(name);
        JSONArray jsonArray = jsonObject.getJSONArray("songs");

        for (Object json : jsonArray) {
            JSONObject songJson = (JSONObject) json;
            Song song = Song.fromJson(songJson);
            playlist.addSong(song);
        }

        return playlist;
    }
}
