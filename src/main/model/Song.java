package model;

import org.json.JSONObject;
import persistence.Writable;

public class Song implements Writable {
    private String name;
    private String artist;
    private String category;

    public Song(String name, String artist, String category) {
        this.name = name;
        this.artist = artist;
        this.category = category;
    }

    public String getName() {
        return name;
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
        json.put("name", name);
        json.put("artist", artist);
        json.put("category", category);
        return json;
    }

    public static Song fromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artist = jsonObject.getString("artist");
        String category = jsonObject.getString("category");
        return new Song(name, artist, category);
    }

    @Override
    public String toString() {
        return name + " by " + artist + " [" + category + "]";
    }
}
