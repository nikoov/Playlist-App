/**
 * Represents a song with a title, artist, and category.
 */
public class Song {
    private String title;
    private String artist;
    private String category;

    /**
     * Constructs a song with the given title, artist, and category.
     *
     * @param title    the title of the song
     * @param artist   the artist of the song
     * @param category the category of the song
     * @requires title != null && artist != null && category != null
     * @modifies this
     * @effects constructs a new Song object with the given title, artist, and category
     */
    public Song(String title, String artist, String category) {
        this.title = title;
        this.artist = artist;
        this.category = category;
    }

    /**
     * Returns the title of the song.
     *
     * @return the title of the song
     * @effects returns the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the artist of the song.
     *
     * @return the artist of the song
     * @effects returns the artist of the song
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Returns the category of the song.
     *
     * @return the category of the song
     * @effects returns the category of the song
     */
    public String getCategory() {
        return category;
    }
}
