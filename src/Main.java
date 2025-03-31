// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 3/12/2025
// Class: Main.java
// Description: This DMS application is used to keep track of the albums that an individual has listened to along with their ratings

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Main {
    // Name: main
    // Description: Main method for running the program
    // Inputs: none
    // Outputs: none


    // Internal DB
    public static List<Album> albums = new ArrayList<Album>();

    // External DB
    public static File dbFile;

    private static DefaultTableModel model;

    public static AlbumsDB db = new AlbumsDB();

    public static void main(String[] args) {
        // Open the JFileChooser first to select the SQLite DB file
        dbFile = selectFile();

        // If no file is selected or an error occurs, exit
        if (dbFile == null) {
            System.out.println("No database file selected, exiting.");
            System.exit(0);
        }



        JFrame frame = new JFrame("Music DMS");
        JTextField searchBar;
        JLabel searchLbl;
        JTable mainTable;
        TableRowSorter<TableModel> sorter;
        JScrollPane jsp;

        // Create UI elements
        searchBar = new JTextField(10);
        searchLbl = new JLabel("Search");
        JButton addButton = new JButton("+");
        String[] columnNames = {"Album ID", "Name", "Artist Name", "Genre", "Rating", "Track Count", "Runtime"};
        Object[][] rowData = {}; // Empty initially; this will be updated when albums are added
        model = new DefaultTableModel(rowData, columnNames);
        sorter = new TableRowSorter<>(model);
        mainTable = new JTable(model);
        mainTable.setRowSorter(sorter);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        jsp = new JScrollPane(mainTable);

        // Button function to open the album adding window
        addButton.addActionListener(e -> {
            // Show the album info dialog for adding a new album
            addWindow.run();
        });

        // Right-click menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem modifyItem = new JMenuItem("Edit");

        // Delete action
        deleteItem.addActionListener(e -> {
            int selectedRow = mainTable.getSelectedRow();
            if (selectedRow != -1) {
                int albumId = (int) model.getValueAt(selectedRow, 0);
                Album album = getAlbumById(albumId);
                removeAlbumFromDB(album);
                updateTable();
            }
        });

        // Modify action
        modifyItem.addActionListener(e -> {
            int selectedRow = mainTable.getSelectedRow();
            if (selectedRow != -1) {
                int albumId = (int) model.getValueAt(selectedRow, 0);
                Album album = getAlbumById(albumId);
                new EditWindow(album);  // Open the Edit Window
            }
        });

        // Mouse listener to show popup menu on right-click
        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = mainTable.rowAtPoint(e.getPoint());
                    mainTable.setRowSelectionInterval(row, row);  // Select the row right-clicked on
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // Add items to right-click menu
        popupMenu.add(modifyItem);
        popupMenu.add(deleteItem);

        // Add elements to the frame
        frame.add(addButton);
        frame.add(searchLbl);
        frame.add(searchBar);
        frame.add(jsp);

        // Searchbar functionality to filter the table
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchBar.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchBar.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchBar.getText());
            }

            private void search(String str) {
                if (str.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(str));
                }
            }
        });

        // Frame settings
        frame.setSize(475, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        updateTable();
    }

    // Method to update the table with the latest list of albums
    // Name: updateTable
    // Description: Updates the table in the main window after making changes to the internal DB.
    // Inputs: none
    // Outputs: none
    public static void updateTable() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath())) {
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Albums")) {
                // Clear any existing data in the table model
                model.setRowCount(0);

                // Add data to the table
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("ArtistName"),
                            rs.getString("Genre"),
                            rs.getInt("UserRating"),
                            rs.getInt("TrackCount"),
                            convertIntToRuntimeString(rs.getInt("Runtime"))
                    };
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Name: convertIntToRuntimeString
    // Description: Converts a runtime value in seconds to a human-readable timespan
    // Inputs:
        // int runtime: integer to convert
    // Outputs:
        // String: Human-readable representation of the integer runtime
    public static String convertIntToRuntimeString(int runtime) {
        int hours = runtime / 3600;
        int minutes = (runtime / 60) % 60;
        int seconds = runtime % 60;

        return (hours + ":" + minutes + ":" + seconds);
    }

    // Name: isAlbumIdUsed
    // Description: Checks if an album id is currently in use. While not particularly useful now,
                 // it will be when the SQL database is added and the id is used as a PK.
    // Inputs:
        // List<Album>: The list of albums to check against
        // int id: The album id that will be checked.
    // Outputs:
        // boolean: true if the id is in use and false if the id is not in use.
    public static boolean isAlbumIdUsed(int id) {
        if ((Main.db.getExecuteResult("SELECT * FROM " + AlbumsDB.TABLE_NAME + " WHERE Id = " + id)).isEmpty()) {
            return false;
        }

        return true;
    }

    // Name: getAlbumById
    // Description: Gets an album from the internal DB based on its ID
    // Inputs:
        // int id: The album id that will be checked.
    // Outputs:
        // Album: The album with a matching ID
        // null: If no album has the ID provided, null is returned
    public static Album getAlbumById(int id) {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

        data = Main.db.getExecuteResult("SELECT * FROM " + AlbumsDB.TABLE_NAME + " WHERE Id = " + id);

        if (data.isEmpty()) {
            return null;
        }

        Album album = new Album(
            (int) data.get(0).get(0),
            (String) data.get(0).get(1),
            (String) data.get(0).get(2),
            (String) data.get(0).get(3),
            (int) data.get(0).get(4),
            (int) data.get(0).get(5),
            (int) data.get(0).get(6)
        );
        return album;
    }

    // Name: addAlbumToDB
    // Description: Adds an album to the internal DB
    // Inputs:
        // Album album: The album that will be added to the DB.
    // Outputs: none
    public static void addAlbumToDB(Album album) {
        db.insert(album);
        updateTable();
    }

    // Name: removeAlbumFromDB
    // Description: Adds an album to the internal DB
    // Inputs:
        // Album album: The album that will be removed from the DB.
    // Outputs: none
    public static void removeAlbumFromDB(Album albumToRemove) {
        db.delete(AlbumsDB.COLUMN_ID, Integer.toString(albumToRemove.id));
    }

    // Name: updateAlbum
    // Description: Updates a property for an album within the DB
    // Inputs:
    // int albumId: The album that will be removed from the DB.
    // AlbumProperty: Enum representing the property that will be modified
    // String newValue: The updated value. This value will be converted to the correct type based on the AlbumProperty
    // Outputs: none
    public static void updateAlbum(int albumId, AlbumProperty property, String newValue) {
        Album albumToModify;
        int index = -1;

        if (albumId < 0) {
            throw new IllegalArgumentException("Album ID cannot be a negative number.");
        }

        // Verify that album exists
        albumToModify = getAlbumById(albumId);
        if (albumToModify == null) {
            throw new NullPointerException("Album with id [" + albumId + "] does not exist.");
        }

        switch (property) {
            case name:
                // Validate that the name isn't null/whitespace
                if (newValue.isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty!");
                }

                // Update the album name property
                db.update("Name", newValue, "Id", Integer.toString(albumId));
                return;
            case artistName:
                // Validate that the artist name isn't null/whitespace
                if (newValue.isEmpty()) {
                    throw new IllegalArgumentException("Artist name cannot be empty!");
                }

                // Update artist name property
                db.update("ArtistName", newValue, "Id", Integer.toString(albumId));
                return;
            case genre:
                // Validate that the genre isn't null/whitespace
                if (newValue.isEmpty()) {
                    throw new IllegalArgumentException("Genre cannot be empty!");
                }

                // Update genre property
                db.update("Genre", newValue, "Id", Integer.toString(albumId));
                return;
            case userRating:
                int newUserRating;

                // Parse integer
                try {
                    newUserRating = Integer.parseInt(newValue);

                    // User rating is between 0 and 10
                    if (newUserRating < 0 || newUserRating > 10) {
                        throw new IllegalArgumentException("Invalid album rating! Album rating must be an integer between 0 and 10.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid album rating! Album rating must be an integer between 0 and 10.");
                }

                // Update user rating property
                db.update("UserRating", Integer.toString(newUserRating), "Id", Integer.toString(albumId));
                return;
            case trackCount:
                int newTrackCount;

                // Parse integer
                try {
                    newTrackCount = Integer.parseInt(newValue);

                    // Track count can be any positive integer
                    if (newTrackCount < 1) {
                        throw new IllegalArgumentException("Invalid track count! Track count must be a positive integer.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid track count! Track count must be a positive integer.");
                }

                // Update track count property
                db.update("TrackCount", Integer.toString(newTrackCount), "Id", Integer.toString(albumId));
                return;
            case runtime:
                int newRuntime;

                // Parse integer
                try {
                    newRuntime = Integer.parseInt(newValue);

                    // Runtime can be any positive integer
                    if (newRuntime < 1) {
                        throw new IllegalArgumentException("Invalid runtime! Runtime must be a positive integer.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid runtime! Runtime must be a positive integer.");
                }

                // Update runtime property
                db.update("Runtime", Integer.toString(newRuntime), "Id", Integer.toString(albumId));
        }
    }

    // Enum of the properties in an album object
    // Used in updateAlbum() method
    public enum AlbumProperty {
        id,
        name,
        artistName,
        genre,
        userRating,
        trackCount,
        runtime
    }


    // Edit Window Class
    public static class EditWindow {
        private JFrame frame;
        private JTextField nameField, artistField, genreField, ratingField, trackCountField, runtimeField;
        private Album albumToEdit;

        // Name: EditWindow
        // Description: Launches a GUI to edit the selected album
        // Inputs:
            // Album album: The album that will be modified from the DB.
        // Outputs: none
        public EditWindow(Album album) {
            this.albumToEdit = album;

            // Create the Edit window
            frame = new JFrame("Edit Album");
            frame.setLayout(new GridLayout(7, 2));

            // Add fields and labels
            frame.add(new JLabel("Album Name:"));
            nameField = new JTextField(album.getName());
            frame.add(nameField);

            frame.add(new JLabel("Artist Name:"));
            artistField = new JTextField(album.getArtistName());
            frame.add(artistField);

            frame.add(new JLabel("Genre:"));
            genreField = new JTextField(album.getGenre());
            frame.add(genreField);

            frame.add(new JLabel("Rating (0-10):"));
            ratingField = new JTextField(String.valueOf(album.getUserRating()));
            frame.add(ratingField);

            frame.add(new JLabel("Track Count:"));
            trackCountField = new JTextField(String.valueOf(album.getTrackCount()));
            frame.add(trackCountField);

            frame.add(new JLabel("Runtime (minutes):"));
            runtimeField = new JTextField(String.valueOf(album.getRuntime()));
            frame.add(runtimeField);

            // Save button to save the changes
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            saveButton.addActionListener(e -> {
                try {
                    if (album.name != nameField.getText()) {
                        updateAlbum(album.id, AlbumProperty.name, nameField.getText());
                    }
                    if (album.artistName != artistField.getText()) {
                        updateAlbum(album.id, AlbumProperty.artistName, artistField.getText());
                    }
                    if (album.genre != genreField.getText()) {
                        updateAlbum(album.id, AlbumProperty.genre, genreField.getText());
                    }
                    if (album.userRating != Integer.parseInt(ratingField.getText())) {
                        updateAlbum(album.id, AlbumProperty.userRating, ratingField.getText());
                    }
                    if (album.trackCount != Integer.parseInt(trackCountField.getText())) {
                        updateAlbum(album.id, AlbumProperty.trackCount, trackCountField.getText());
                    }
                    if (album.trackCount != Integer.parseInt(runtimeField.getText())) {
                        updateAlbum(album.id, AlbumProperty.trackCount, runtimeField.getText());
                    }

                    // Update the table and close the window
                    updateTable();
                    frame.dispose();  // Close the edit window
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            });


            // Close the edit window without making changes
            cancelButton.addActionListener(e -> {
                frame.dispose();
            });

            // Add elements to frame
            frame.add(saveButton);
            frame.add(cancelButton);
            frame.setSize(300, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    // Name: selectFile
    // Description: Opens a JFileChooser for the user to select a file
    // Inputs: none
    // Outputs:
        // File: The file selected by the user
        // null: if the user closes the JFileChooser without selecting a file
    private static File selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select SQLite Database File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("SQLite DB Files", "db", "sqlite"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null; // Return null if no file was selected
    }
}