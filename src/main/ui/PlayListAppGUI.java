package ui;

import model.Playlist;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayListAppGUI extends JFrame {
    private JTextField playlistNameField;
    private JTextField songNameField;
    private JTextField artistNameField;
    private JTextField songCategoryField;
    private JTextField searchField;
    private DefaultListModel<String> playlistModel;
    private JList<String> playlistJList;
    private JLabel songDetailsLabel; // New JLabel for song details
    private Playlist currentPlaylist;
    private Map<String, Playlist> playlists;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/playlist.json";


    //MODIFIES: This instance (PlayListAppGUI) by setting up and initializing the GUI components.
    // EFFECTS: Creates and displays the GUI with all necessary components.
    //REQUIRES: None
    public PlayListAppGUI() {
        super("Playlist Application");
        setSize(1000, 600); // Increased width to accommodate song details panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playlists = new HashMap<>();
        layoutComponents();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    // EFFECTS: Sets up and arranges GUI components on the frame.
    //REQUIRES: None

    private void layoutComponents() {
        playlistNameField = new JTextField(10);
        songNameField = new JTextField(10);
        artistNameField = new JTextField(10);
        songCategoryField = new JTextField(10);
        searchField = new JTextField(10);
        playlistModel = new DefaultListModel<>();
        playlistJList = new JList<>(playlistModel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel);
    }


    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges GUI components on the frame.
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(createPlaylistPanel());
        leftPanel.add(addSongPanel());
        leftPanel.add(searchPanel());
        leftPanel.add(filePanel());
        leftPanel.add(createPlaylistDisplayPanel());
        leftPanel.add(createBlueCirclePanel());

        return leftPanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges playlist panel
    private JPanel createPlaylistPanel() {
        JPanel createPlaylistPanel = new JPanel();
        createPlaylistPanel.add(new JLabel("Playlist Name:"));
        createPlaylistPanel.add(playlistNameField);
        JButton createPlaylistButton = new JButton("Create Playlist");
        createPlaylistButton.addActionListener(this::createPlaylistAction);
        createPlaylistPanel.add(createPlaylistButton);
        return createPlaylistPanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges add song details panel
    private JPanel addSongPanel() {
        JPanel addSongPanel = new JPanel();
        addSongPanel.add(new JLabel("Song Name:"));
        addSongPanel.add(songNameField);
        addSongPanel.add(new JLabel("Artist Name:"));
        addSongPanel.add(artistNameField);
        addSongPanel.add(new JLabel("Category:"));
        addSongPanel.add(songCategoryField);
        JButton addSongButton = new JButton("Add Song");
        addSongButton.addActionListener(this::addSongAction);
        addSongPanel.add(addSongButton);
        return addSongPanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges the search button
    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::searchSongAction);
        searchPanel.add(searchButton);

        JButton viewPlaylistButton = new JButton("View Playlist");
        viewPlaylistButton.addActionListener(this::viewPlaylistAction);
        searchPanel.add(viewPlaylistButton);

        JButton removeSongButton = new JButton("Remove Song");
        removeSongButton.addActionListener(this::removeSongAction);
        searchPanel.add(removeSongButton);

        return searchPanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges GUI save , load and quit buttons
    private JPanel filePanel() {
        JPanel filePanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::savePlaylistsAction);
        filePanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(this::loadPlaylistsAction);
        filePanel.add(loadButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::quitApplicationAction);
        filePanel.add(quitButton);

        return filePanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges GUI components on the frame.
    private JPanel createPlaylistDisplayPanel() {
        JScrollPane playlistScrollPane = new JScrollPane(playlistJList);
        JPanel playlistDisplayPanel = new JPanel();
        playlistDisplayPanel.add(playlistScrollPane);
        return playlistDisplayPanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges GUI components by creating a blue circle
    private JPanel createBlueCirclePanel() {
        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLUE);
                g2d.fillOval(10, 10, 100, 100); // Draw a blue circle
            }
        };
        circlePanel.setPreferredSize(new Dimension(120, 120));
        return circlePanel;
    }

    //MODIFIES: This instance (PlayListAppGUI) by adding and arranging GUI components.
    //EFFECTS: Sets up and arranges GUI components to have a panel that includes
    //details
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        songDetailsLabel = new JLabel("Song details will appear here", SwingConstants.CENTER);
        songDetailsLabel.setPreferredSize(new Dimension(300, 600)); // Set preferred size
        rightPanel.add(songDetailsLabel, BorderLayout.CENTER);
        return rightPanel;
    }

      //MODIFIES: This instance (PlayListAppGUI) by updating the current playlist and playlistModel.
      //EFFECTS: Creates a new playlist with the given name and displays a confirmation message.
      // REQUIRES: The playlist name must not be empty.
    private void createPlaylistAction(ActionEvent e) {
        String playlistName = playlistNameField.getText().trim();
        if (!playlistName.isEmpty()) {
            currentPlaylist = new Playlist(playlistName);
            playlists.put(playlistName, currentPlaylist);
            JOptionPane.showMessageDialog(this, "Playlist created: " + playlistName);
            playlistModel.clear(); // Clear the playlist display
            songDetailsLabel.setText("Song details will appear here"); // Reset song details
        }
    }


    //MODIFIES: This instance (PlayListAppGUI) by adding the song to the current playlist and updating playlistModel.
    //EFFECTS: Adds a song to the current playlist and displays a confirmation message.
    //REQUIRES: A playlist must be created first. Song details must not be empty.


    private void addSongAction(ActionEvent e) {
        if (currentPlaylist == null) {
            JOptionPane.showMessageDialog(this, "Create a playlist first.");
            return;
        }
        String songName = songNameField.getText().trim();
        String artistName = artistNameField.getText().trim();
        String songCategory = songCategoryField.getText().trim();
        if (!songName.isEmpty() && !artistName.isEmpty() && !songCategory.isEmpty()) {
            Song song = new Song(songName, artistName, songCategory);
            currentPlaylist.addSong(song);
            JOptionPane.showMessageDialog(this, "Song added: " + songName);
            songNameField.setText("");
            artistNameField.setText("");
            songCategoryField.setText("");
            playlistModel.addElement(song.toString());
            songDetailsLabel.setText("Added song: " + song); // Update song details
        }
    }


    //MODIFIES: This instance (PlayListAppGUI) by updating the playlistModel with search results.
    //EFFECTS: Displays songs that match the search query.
    //REQUIRES: A playlist must be created first. The search query must not be empty.


    private void searchSongAction(ActionEvent e) {
        if (currentPlaylist == null) {
            JOptionPane.showMessageDialog(this, "Create a playlist first.");
            return;
        }
        String searchQuery = searchField.getText().trim();
        if (!searchQuery.isEmpty()) {
            List<Song> searchResults = currentPlaylist.searchSongs(searchQuery);
            playlistModel.clear();
            for (Song song : searchResults) {
                playlistModel.addElement(song.toString());
            }
        }
    }


    //MODIFIES: This instance (PlayListAppGUI) by updating the playlistModel with the current playlist.
    //EFFECTS: Displays the current playlist and its songs.
    //REQUIRES: A playlist must be created.
    private void viewPlaylistAction(ActionEvent e) {
        if (currentPlaylist == null) {
            JOptionPane.showMessageDialog(this, "Create a playlist first.");
            return;
        }
        playlistModel.clear();
        playlistModel.addElement("Playlist: " + currentPlaylist.getName()); // Display playlist name
        for (Song song : currentPlaylist.viewSongs()) {
            playlistModel.addElement(song.toString());
        }
    }


    //MODIFIES: This instance (PlayListAppGUI) by removing the selected song from the current playlist
    // and updating playlistModel.
    //EFFECTS: Removes the selected song from the playlist and updates the display.
    //REQUIRES: A playlist must be created and a song must be selected.

    private void removeSongAction(ActionEvent e) {
        int selectedIndex = playlistJList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedSongStr = playlistModel.getElementAt(selectedIndex);
            Song selectedSong = null;
            for (Song song : currentPlaylist.viewSongs()) {
                if (song.toString().equals(selectedSongStr)) {
                    selectedSong = song;
                    break;
                }
            }
            if (selectedSong != null) {
                playlistModel.removeElementAt(selectedIndex);
                currentPlaylist.removeSong(selectedSong);
                JOptionPane.showMessageDialog(this, "Song removed: " + selectedSongStr);
                songDetailsLabel.setText("Removed song: " + selectedSongStr); // Update song details
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a song to remove.");
        }
    }

    //MODIFIES: This instance (PlayListAppGUI) by loading playlists from a JSON file and updating the playlists map.
    //EFFECTS: Loads playlists from a JSON file and updates the playlistModel.
    //REQUIRES: None

    private void savePlaylistsAction(ActionEvent e) {
        try {
            jsonWriter.open();
            jsonWriter.write(playlists);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Playlists saved to " + JSON_STORE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save playlists: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }

    //MODIFIES: This instance (PlayListAppGUI) by saving the playlists to a JSON file.
    //EFFECTS: Saves the playlists to a JSON file and displays a confirmation message.
    //REQUIRES: None
    private void loadPlaylistsAction(ActionEvent e) {
        try {
            playlists = jsonReader.read();
            // Load the first playlist or handle accordingly
            if (!playlists.isEmpty()) {
                currentPlaylist = playlists.values().iterator().next();
                playlistModel.clear();
                playlistModel.addElement("Playlist: " + currentPlaylist.getName());
                for (Song song : currentPlaylist.viewSongs()) {
                    playlistModel.addElement(song.toString());
                }
                JOptionPane.showMessageDialog(this, "Playlists loaded from " + JSON_STORE);
            } else {
                JOptionPane.showMessageDialog(this, "No playlists found.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load playlists: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }

    //MODIFIES: This instance (PlayListAppGUI) by loading playlists from a JSON file and updating the playlists map.
    //EFFECTS: Loads playlists from a JSON file and updates the playlistModel.
    // REQUIRES: None

    private void quitApplicationAction(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //MODIFIES: This instance (PlayListAppGUI) by closing the application.
     //EFFECTS: Exits the application.

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlayListAppGUI app = new PlayListAppGUI();
            app.setVisible(true);
        });
    }
}
