package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import model.Event; // Import Event class
import model.EventLog; // Import EventLog class
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.EventLog;


// Represents a playlist having a collection of songs
public class Playlist implements Writable {
    private String name;
    private List<Song> songs;
    private EventLog eventLog;

    // EFFECTS: constructs a playlist with a name and empty list of songs
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
        logEvent("Playlist created: " + name); // Log playlist creation
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
        logEvent("Song added: " + song.getName() + " by " + song.getArtist() + " to playlist " + name); // Log song addition
    }

    public void removeSong(Song song) {
        if (songs.remove(song)) {
            logEvent("Song removed: " + song.getName() + " by " + song.getArtist() + " from playlist " + name); // Log song removal
        }
    }

    public void renamePlaylist(String newName) {
        logEvent("Playlist renamed from " + name + " to " + newName); // Log playlist renaming
        this.name = newName;
    }

    public void shuffleSongs() {
        Collections.shuffle(songs);
        logEvent("Playlist shuffled: " + name); // Log playlist shuffle
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

    public void printLog() {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.getDescription());
        }
    }


    // Logs events to the EventLog
    private void logEvent(String message) {
        Event event = new Event(message); // Create an Event object with the message
        EventLog.getInstance().logEvent(event); // Log the Event object
    }
}
