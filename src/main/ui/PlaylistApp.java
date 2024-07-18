package ui;


import model.Category;
import model.Playlist;
import model.Song;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

   // creates a ui for the playlist app
public class PlaylistApp {
    private Map<String, Playlist> playlists;
    private Map<String, Category> categories;
    private Scanner scanner;

    //constructs a playlist app
    public PlaylistApp() {
        playlists = new HashMap<>();
        categories = new HashMap<>();
        scanner = new Scanner(System.in);
        runPlaylistApp();
    }
    //runs the playlist app menu
    private void runPlaylistApp() {
        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            String command = scanner.nextLine();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }
    // provides the display menu
    private void displayMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("\tcreate -> Create a new playlist");
        System.out.println("\tview -> View a playlist");
        System.out.println("\tadd -> Add a song to a playlist");
        System.out.println("\tremove -> Remove a song from a playlist");
        System.out.println("\trename -> Rename a playlist");
        System.out.println("\tcreateCat -> Create a new category");
        System.out.println("\taddCat -> Add a song to a category");
        System.out.println("\tviewCat -> View a category");
        System.out.println("\tshuffle -> Shuffle a playlist");
        System.out.println("\tsearch -> Search for a song in a playlist");
        System.out.println("\tquit -> Quit the application");
    }

    private void processCommand(String command) {
        switch (command) {
            case "create":
                createPlaylist();
                break;
            case "view":
                viewPlaylist();
                break;
            case "add":
                addSongToPlaylist();
                break;
            case "remove":
                removeSongFromPlaylist();
                break;
            case "rename":
                renamePlaylist();
                break;
            case "createCat":
                createCategory();
                break;
            case "addCat":
                addSongToCategory();
                break;
            case "viewCat":
                viewCategory();
                break;
            case "shuffle":
                shufflePlaylist();
                break;
            case "search":
                searchSongInPlaylist();
                break;
            default:
                System.out.println("Invalid command");
        }
    }
    //creates a playlist in my UI and asks for the user to enter playlist name
    private void createPlaylist() {
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        playlists.put(name, new Playlist(name));
        System.out.println("Playlist created.");
    }
    //views the playlist
    private void viewPlaylist() {
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        Playlist playlist = playlists.get(name);
        if (playlist != null) {
            for (Song song : playlist.viewSongs()) {
                System.out.println(song.getTitle() + " by " + song.getArtist());
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }
    //adds songs to the playlist
    private void addSongToPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            System.out.print("Enter song title: ");
            String title = scanner.nextLine();
            System.out.print("Enter song artist: ");
            String artist = scanner.nextLine();
            System.out.print("Enter song category: ");
            String category = scanner.nextLine();
            Song song = new Song(title, artist, category);
            playlist.addSong(song);
            System.out.println("Song added to playlist.");
        } else {
            System.out.println("Playlist not found.");
        }
    }
    //removes songs from the playlist
    private void removeSongFromPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            System.out.print("Enter song title to remove: ");
            String title = scanner.nextLine();
            boolean removed = false;
            for (Song song : playlist.viewSongs()) {
                if (song.getTitle().equals(title)) {
                    playlist.removeSong(song);
                    removed = true;
                    System.out.println("Song removed from playlist.");
                    break;
                }
            }
            if (!removed) {
                System.out.println("Song not found in playlist.");
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }
    //Allows the user to rename the playlist
    private void renamePlaylist() {
        System.out.print("Enter current playlist name: ");
        String currentName = scanner.nextLine();
        Playlist playlist = playlists.get(currentName);
        if (playlist != null) {
            System.out.print("Enter new playlist name: ");
            String newName = scanner.nextLine();
            playlist.renamePlaylist(newName);
            playlists.put(newName, playlist);
            playlists.remove(currentName);
            System.out.println("Playlist renamed.");
        } else {
            System.out.println("Playlist not found.");
        }
    }
    //allows the user to create categories for music
    private void createCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        categories.put(name, new Category(name));
        System.out.println("Category created.");
    }

    //allows user to input a song to a specific category
    private void addSongToCategory() {
        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();
        Category category = categories.get(categoryName);
        if (category != null) {
            System.out.print("Enter song title: ");
            String title = scanner.nextLine();
            System.out.print("Enter song artist: ");
            String artist = scanner.nextLine();
            System.out.print("Enter song category: ");
            String songCategory = scanner.nextLine();
            Song song = new Song(title, artist, songCategory);
            category.addSong(song);
            System.out.println("Song added to category.");
        } else {
            System.out.println("Category not found.");
        }
    }

    //allows the user to view a certain category
    private void viewCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        Category category = categories.get(name);
        if (category != null) {
            for (Song song : category.viewSongs()) {
                System.out.println(song.getTitle() + " by " + song.getArtist());
            }
        } else {
            System.out.println("Category not found.");
        }
    }

    //allows the user to shuffle the playlist
    //Requires the playlist to not be null

    private void shufflePlaylist() {
        System.out.print("Enter playlist name to shuffle: ");
        String name = scanner.nextLine();
        Playlist playlist = playlists.get(name);
        if (playlist != null) {
            playlist.shuffleSongs();
            System.out.println("Playlist shuffled.");
        } else {
            System.out.println("Playlist not found.");
        }
    }

    //Requires: Playlist to not be null
    // EFFECTS: searches for songs in the playlist by their name or their artist
    private void searchSongInPlaylist() {
        System.out.print("Enter playlist name to search in: ");
        String playlistName = scanner.nextLine();
        Playlist playlist = playlists.get(playlistName);

        if (playlist != null) {
            System.out.print("Enter search query (title or artist): ");
            String query = scanner.nextLine();
            boolean found = false;

            for (Song song : playlist.viewSongs()) {
                if (song.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        song.getArtist().toLowerCase().contains(query.toLowerCase())) {
                    System.out.println(song.getTitle() + " by " + song.getArtist());
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Song or artist '" + query + "' not found in playlist.");
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }


    public static void main(String[] args) {
        new PlaylistApp();
    }
}

