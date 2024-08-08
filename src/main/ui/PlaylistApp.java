package ui;

import model.Category;
import model.Event;
import model.EventLog;
import model.Playlist;
import model.Song;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PlaylistApp {
    private static final String JSON_STORE = "./data/testReaderEmptyPlaylists.json";
    private Map<String, Playlist> playlists;
    private Map<String, Category> categories;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Map<String, Runnable> commandMap;

    public PlaylistApp() {
        playlists = new HashMap<>();
        categories = new HashMap<>();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeCommands();
        runPlaylistApp();
    }

    private void initializeCommands() {
        commandMap = new HashMap<>();
        commandMap.put("create", this::createPlaylist);
        commandMap.put("view", this::viewPlaylist);
        commandMap.put("add", this::addSongToPlaylist);
        commandMap.put("remove", this::removeSongFromPlaylist);
        commandMap.put("rename", this::renamePlaylist);
        commandMap.put("createCat", this::createCategory);
        commandMap.put("addCat", this::addSongToCategory);
        commandMap.put("viewCat", this::viewCategory);
        commandMap.put("shuffle", this::shufflePlaylist);
        commandMap.put("search", this::searchSongInPlaylist);
        commandMap.put("save", this::savePlaylists);
        commandMap.put("load", this::loadPlaylists);
    }

    private void runPlaylistApp() {
        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            String command = scanner.nextLine();

            if (command.equals("quit")) {
                keepGoing = false;
                printEventLog();
            } else {
                processCommand(command);
            }
        }
    }

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
        System.out.println("\tsave -> Save your playlists");
        System.out.println("\tload -> Load your playlists");
        System.out.println("\tquit -> Quit the application");
    }

    private void processCommand(String command) {
        Runnable action = commandMap.get(command);
        if (action != null) {
            action.run();
        } else {
            System.out.println("Invalid command");
        }
    }

    private void createPlaylist() {
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        playlists.put(name, new Playlist(name));
        EventLog.getInstance().logEvent(new Event("Created playlist: " + name));
        System.out.println("Playlist created.");
    }

    private void viewPlaylist() {
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        Playlist playlist = playlists.get(name);
        if (playlist != null) {
            for (Song song : playlist.viewSongs()) {
                System.out.println(song.getName() + " by " + song.getArtist());
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }

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
            EventLog.getInstance().logEvent(new Event("Added song: "
                    + title + " to playlist: " + playlistName));
            System.out.println("Song added to playlist.");
        } else {
            System.out.println("Playlist not found.");
        }
    }

    private void removeSongFromPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            System.out.print("Enter song title to remove: ");
            String title = scanner.nextLine();
            boolean removed = false;
            for (Song song : playlist.viewSongs()) {
                if (song.getName().equals(title)) {
                    playlist.removeSong(song);
                    EventLog.getInstance().logEvent(new Event("Removed song: "
                            + title + " from playlist: " + playlistName));
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
            EventLog.getInstance().logEvent(new Event("Renamed playlist from: "
                    + currentName + " to: " + newName));
            System.out.println("Playlist renamed.");
        } else {
            System.out.println("Playlist not found.");
        }
    }

    private void createCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        categories.put(name, new Category(name));
        EventLog.getInstance().logEvent(new Event("Created category: " + name));
        System.out.println("Category created.");
    }

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
            EventLog.getInstance().logEvent(new Event("Added song: " + title
                    + " to category: " + categoryName));
            System.out.println("Song added to category.");
        } else {
            System.out.println("Category not found.");
        }
    }

    private void viewCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        Category category = categories.get(name);
        if (category != null) {
            for (Song song : category.viewSongs()) {
                System.out.println(song.getName() + " by " + song.getArtist());
            }
        } else {
            System.out.println("Category not found.");
        }
    }

    private void shufflePlaylist() {
        System.out.print("Enter playlist name to shuffle: ");
        String name = scanner.nextLine();
        Playlist playlist = playlists.get(name);
        if (playlist != null) {
            playlist.shuffleSongs();
            EventLog.getInstance().logEvent(new Event("Shuffled playlist: " + name));
            System.out.println("Playlist shuffled.");
        } else {
            System.out.println("Playlist not found.");
        }
    }

    private void searchSongInPlaylist() {
        System.out.print("Enter playlist name to search in: ");
        String playlistName = scanner.nextLine();
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            System.out.print("Enter search query (title or artist): ");
            String query = scanner.nextLine();
            boolean found = false;
            for (Song song : playlist.viewSongs()) {
                if (song.getName().equalsIgnoreCase(query) || song.getArtist().equalsIgnoreCase(query)) {
                    System.out.println("Found: " + song.getName() + " by " + song.getArtist());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No matching songs found.");
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }

    private void savePlaylists() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlists);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Saved playlists to " + JSON_STORE));
            System.out.println("Playlists saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save playlists to " + JSON_STORE);
        }
    }

    private void loadPlaylists() {
        try {
            Map<String, Playlist> loadedPlaylists = jsonReader.read();
            System.out.println("Playlists loaded from " + JSON_STORE);
            System.out.println("Loaded playlists:");
            for (Map.Entry<String, Playlist> entry : loadedPlaylists.entrySet()) {
                System.out.println("Playlist: " + entry.getKey());
                Playlist playlist = entry.getValue();
                for (Song song : playlist.viewSongs()) {
                    System.out.println("\t" + song.getName() + " by " + song.getArtist()
                            + " (" + song.getCategory() + ")");
                }
            }
            playlists.putAll(loadedPlaylists);
            EventLog.getInstance().logEvent(new Event("Loaded playlists from " + JSON_STORE));
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void printEventLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDate() + " - " + event.getDescription());
        }
    }

    public static void main(String[] args) {
        new PlaylistApp();
    }
}
