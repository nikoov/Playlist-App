package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a song with a title, artist, and category
public class Song implements Writable {
    private String title;
    private String artist;
    private String category;

    // EFFECTS: constructs a song with title, artist, and category
    public Song(String title, String artist, String category) {
        this.title = title;
        this.artist = artist;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("artist", artist);
        json.put("category", category);
        return json;
    }

    // EFFECTS: creates a Song object from a JSONObject
    public static Song fromJson(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        String category = jsonObject.getString("category");
        return new Song(title, artist, category);
    }
}
