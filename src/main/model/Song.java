package model;

public class Song {
    private String title;
    private String artist;
    private String category;

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
}