package ui;

import model.Playlist;
import model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayListAppGUI extends JFrame {
    private final JTextField playlistNameField = new JTextField(10);
    private final JTextField songNameField = new JTextField(10);
    private final JTextField artistNameField = new JTextField(10);
    private final JTextField categoryField = new JTextField(10);
    private final JTextField searchField = new JTextField(10);
    private final DefaultListModel<String> playlistModel = new DefaultListModel<>();
    private final JList<String> playlistList = new JList<>(playlistModel);
    private Playlist playlist = null;

    public PlayListAppGUI() {
        super("Playlist Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveOnClose();
                System.exit(0);
            }
        });
        layoutComponents();
        setupListeners();
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createPlaylistPanel());
        mainPanel.add(createSongInputPanel());
        mainPanel.add(createSearchPanel());
        mainPanel.add(createControlPanel());
        mainPanel.add(createPlaylistListPanel());

        add(mainPanel);
    }

    private JPanel createPlaylistPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Playlist Name:"));
        panel.add(playlistNameField);
        JButton createPlaylistButton = new JButton("Create Playlist");
        createPlaylistButton.addActionListener(e -> createPlaylistAction());
        panel.add(createPlaylistButton);
        return panel;
    }

    private JPanel createSongInputPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Song Name:"));
        panel.add(songNameField);
        panel.add(new JLabel("Artist Name:"));
        panel.add(artistNameField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        JButton addSongButton = new JButton("Add Song");
        addSongButton.addActionListener(e -> addSongAction());
        panel.add(addSongButton);
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Search:"));
        panel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchAction());
        panel.add(searchButton);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadAction());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveAction());
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitAction());
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(quitButton);
        return panel;
    }

    private JPanel createPlaylistListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Playlist"));
        panel.add(new JScrollPane(playlistList), BorderLayout.CENTER);
        return panel;
    }

    private void setupListeners() {
        // Other listeners if needed
    }

    // MODIFIES: this
    // EFFECTS: Creates a new playlist with the name entered in the text field
    private void createPlaylistAction() {
        String playlistName = playlistNameField.getText().trim();
        if (!playlistName.isEmpty()) {
            playlist = new Playlist(playlistName);
            playlistModel.clear();
            playlistNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a playlist name.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, playlist
    // EFFECTS: Adds a new song to the playlist
    private void addSongAction() {
        String songName = songNameField.getText().trim();
        String artistName = artistNameField.getText().trim();
        String category = categoryField.getText().trim();
        if (!songName.isEmpty() && !artistName.isEmpty() && !category.isEmpty()) {
            Song song = new Song(songName, artistName, category);
            playlist.addSong(song);
            playlistModel.addElement(song.toString());
            songNameField.setText("");
            artistNameField.setText("");
            categoryField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill all song details.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Searches for a song or artist in the playlist
    private void searchAction() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (!searchText.isEmpty()) {
            DefaultListModel<String> searchResults = new DefaultListModel<>();
            for (int i = 0; i < playlistModel.size(); i++) {
                String songInfo = playlistModel.getElementAt(i).toLowerCase();
                if (songInfo.contains(searchText)) {
                    searchResults.addElement(playlistModel.getElementAt(i));
                }
            }
            playlistList.setModel(searchResults);
        } else {
            playlistList.setModel(playlistModel);
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the playlist from a file
    private void loadAction() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                playlist = (Playlist) ois.readObject();
                updatePlaylistModel();
                JOptionPane.showMessageDialog(this, "Playlist loaded successfully.",
                        "Load Success", JOptionPane.INFORMATION_MESSAGE);
                // Display pop-up with visual component
                displayLoadPopup();
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading playlist: " + ex.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EFFECTS: Displays a pop-up with a visual component after loading
    private void displayLoadPopup() {
        JOptionPane.showMessageDialog(this, "Playlist loaded successfully!",
                "Load Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("path/to/music/icon.png"));
    }

    // MODIFIES: this
    // EFFECTS: Saves the playlist to a file and clears the current playlist
    private void saveAction() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(playlist);
                playlistModel.clear();
                playlist = null;
                JOptionPane.showMessageDialog(this, "Playlist saved successfully.",
                        "Save Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving playlist: " + ex.getMessage(),
                        "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EFFECTS: Saves on close and exits the application
    private void quitAction() {
        saveOnClose();
        System.exit(0);
    }

    // EFFECTS: Saves the playlist on close if any changes have been made
    private void saveOnClose() {
        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save the playlist before exiting?",
                "Save on Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            saveAction();
        }
    }

    // MODIFIES: this
    // EFFECTS: Updates the playlist model with the current playlist
    private void updatePlaylistModel() {
        playlistModel.clear();
        for (Song song : playlist.viewSongs()) {
            playlistModel.addElement(song.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayListAppGUI().setVisible(true));
    }
}
