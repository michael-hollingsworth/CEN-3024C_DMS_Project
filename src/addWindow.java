// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 3/12/2025
// Class: addWindow.java
// Description: Houses the UI elements used to import albums to the DMS

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class addWindow {
    // Name: run
    // Description: static method to run the createAndShowGUI method
    // Inputs: none
    // Outputs: none
    public static void run() {
        SwingUtilities.invokeLater(() -> new addWindow().createAndShowGUI());
    }

    // Name: createAndShowGUI
    // Description: Creates the GUI used to add and albums to DMS
    // Inputs: none
    // Outputs: none
    public void createAndShowGUI() {
        // Create the frame
        JFrame frame = new JFrame("Add Album");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the first panel with form inputs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Album ID field
        JLabel albumIdLabel = new JLabel("Album ID:");
        JTextField albumIdField = new JTextField(10);
        addComponent(formPanel, albumIdLabel, 0, 0, gbc);
        addComponent(formPanel, albumIdField, 1, 0, gbc);

        // Album Name field
        JLabel albumNameLabel = new JLabel("Album Name:");
        JTextField albumNameField = new JTextField(10);
        addComponent(formPanel, albumNameLabel, 0, 1, gbc);
        addComponent(formPanel, albumNameField, 1, 1, gbc);

        // Artist Name field
        JLabel artistNameLabel = new JLabel("Artist Name:");
        JTextField artistNameField = new JTextField(10);
        addComponent(formPanel, artistNameLabel, 0, 2, gbc);
        addComponent(formPanel, artistNameField, 1, 2, gbc);

        // Genre field
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField(10);
        addComponent(formPanel, genreLabel, 0, 3, gbc);
        addComponent(formPanel, genreField, 1, 3, gbc);

        // Rating field
        JLabel ratingLabel = new JLabel("Rating (0-10):");
        JTextField ratingField = new JTextField(10);
        addComponent(formPanel, ratingLabel, 0, 4, gbc);
        addComponent(formPanel, ratingField, 1, 4, gbc);

        // Track Count field
        JLabel trackCountLabel = new JLabel("Track Count:");
        JTextField trackCountField = new JTextField(10);
        addComponent(formPanel, trackCountLabel, 0, 5, gbc);
        addComponent(formPanel, trackCountField, 1, 5, gbc);

        // Runtime field
        JLabel runtimeLabel = new JLabel("Runtime (seconds):");
        JTextField runtimeField = new JTextField(10);
        addComponent(formPanel, runtimeLabel, 0, 6, gbc);
        addComponent(formPanel, runtimeField, 1, 6, gbc);

        // Save and Cancel buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            // Call the public method for validation and saving
            if (validateAndSaveAlbum(frame, albumIdField, albumNameField, artistNameField, genreField, ratingField, trackCountField, runtimeField)) {
                frame.dispose();
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add the button panel to the form panel
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        formPanel.add(buttonPanel, gbc);

        // Add formPanel to the first tab
        tabbedPane.addTab("Manual", formPanel);

        // Create the second panel with JFileChooser
        JPanel filePanel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(".txt", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        JButton openButton = new JButton("Open Text File");
        openButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    TxtImporter.Import(selectedFile);
                } catch (IOException e1) {
                }

                frame.dispose();
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        filePanel.add(openButton);

        // Add filePanel to the second tab
        tabbedPane.addTab("Import", filePanel);

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // Name: addComponent
    // Description: Helper method to add components with GridBagLayout
    // Inputs:
        // JPanel panel: the panel to add a component to
        // Component comp: the component to add to the panel
        // int gridx: X coordinate of the position where the object should go
        // int gridy: Y coordinate of the position where the object should go
        // GridBagConstraints gbc: constraint to use when adding the component to the panel
    // Outputs: none
    private void addComponent(JPanel panel, Component comp, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(comp, gbc);
    }

    // Name: validateAndSaveAlbum
    // Description: Public method to validate and save the album
    // Inputs:
        // Frame frame: the frame so that it can modify it if need be
        // JTextField albumIdField: album id field to get contents and modify if need be
        // JTextField albumNameField: album name field to get contents and modify if need be
        // JTextField artistNameField: artist name field to get contents and modify if need be
        // JTextField genreField: genre field to get contents and modify if need be
        // JTextField ratingField: rating field to get contents and modify if need be
        // JTextField trackCountField: track count field to get contents and modify if need be
        // JTextField runtimeField: runtime field to get contents and modify if need be
    // Outputs:
        // Boolean: true or false if the object was successfully created
    public boolean validateAndSaveAlbum(JFrame frame, JTextField albumIdField, JTextField albumNameField,
                                        JTextField artistNameField, JTextField genreField, JTextField ratingField,
                                        JTextField trackCountField, JTextField runtimeField) {
        // Reset field backgrounds to default before validating
        resetFieldBackgrounds(albumIdField, albumNameField, artistNameField, genreField, ratingField, trackCountField, runtimeField);

        // Validate inputs and show error messages for invalid input
        String albumId = albumIdField.getText();
        String albumName = albumNameField.getText();
        String artistName = artistNameField.getText();
        String genre = genreField.getText();
        String rating = ratingField.getText();
        String trackCount = trackCountField.getText();
        String runtime = runtimeField.getText();

        if (!isPositiveInteger(albumId)) {
            highlightField(albumIdField);
            JOptionPane.showMessageDialog(frame, "Album ID must be a positive integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Main.isAlbumIdUsed(Integer.parseInt(albumId))) {
            highlightField(albumIdField);
            JOptionPane.showMessageDialog(frame, "Album ID is already in use.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isNonEmptyString(albumName)) {
            highlightField(albumNameField);
            JOptionPane.showMessageDialog(frame, "Album Name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isNonEmptyString(artistName)) {
            highlightField(artistNameField);
            JOptionPane.showMessageDialog(frame, "Artist Name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isNonEmptyString(genre)) {
            highlightField(genreField);
            JOptionPane.showMessageDialog(frame, "Genre cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidRating(rating)) {
            highlightField(ratingField);
            JOptionPane.showMessageDialog(frame, "Rating must be an integer between 0 and 10.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isPositiveInteger(trackCount)) {
            highlightField(trackCountField);
            JOptionPane.showMessageDialog(frame, "Track Count must be a positive integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isPositiveInteger(runtime)) {
            highlightField(runtimeField);
            JOptionPane.showMessageDialog(frame, "Runtime must be a positive integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // If all validations pass, create and save the album
        Album album = new Album(Integer.parseInt(albumId), albumName, artistName, genre, Integer.parseInt(rating), Integer.parseInt(trackCount), Integer.parseInt(runtime));
        Main.addAlbumToDB(album);


        return true;
    }

    // Name: highlightField
    // Description: Helper method to highlight invalid fields
    // Inputs:
        // JTextField field: the field to highlight
    // Outputs: none
    private void highlightField(JTextField field) {
        field.setBackground(Color.PINK);  // Change background to pink
    }

    // Name: resetFieldBackgrounds
    // Description: Helper method to reset field backgrounds to default color
        // Inputs: JTextField fields
    // JTextField field: the field to reset to white
    // Outputs: none
    private void resetFieldBackgrounds(JTextField... fields) {
        for (JTextField field : fields) {
            field.setBackground(Color.WHITE);  // Reset background to white
        }
    }

    // Name: isPositiveInteger
    // Description: Helper method to check if a string is a positive integer
    // Inputs:
        // String str: the string to validate if it is a positive integer
    // Outputs:
        // boolean: true if it is a positive integer false if not
    private boolean isPositiveInteger(String str) {
        try {
            int value = Integer.parseInt(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Name: isPositiveInteger
    // Description: Helper method to check if a string is a non-empty string
    // Inputs:
        // String str: the string to validate if it is empty
    // Outputs:
        // boolean: true if it is a non-empty string false if not
    private boolean isNonEmptyString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Name: isValidRating
    // Description: Helper method to validate the rating (0-10)
    // Inputs:
        // String str: the string to validate if it is a valid rating
    // Outputs:
        // boolean: true if it is a valid rating false if not
    private boolean isValidRating(String rating) {
        try {
            int value = Integer.parseInt(rating);
            return value >= 0 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
