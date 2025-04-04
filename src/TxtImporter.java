// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: TxtImporter.java
// Description: This class is used to import one or more albums from a text file.

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is used to import one or more text albums from a text file
 */
public class TxtImporter {
    // Name: Import
    // Description: Method contains the logic necessary to import albums from a text file
    // Arguments:
        // File txtFile: The File object representing the text file that will be imported.
                      // This object is passed to a scanner to parse the file's contents
    // Outputs:
        // List<Album>: The list of albums imported from the text file. These albums will be added to the internal db by the run() method

    /**
     * This is the primary method used to parse a text file for Album strings. This method will then convert the strings into Album objects and import them to the DB
     * @param txtFile A File object of the text file you are attempting to import albums from
     * @throws FileNotFoundException When the file passed to this method does not exist or cannot be found, a FileNotFoundException is thrown.
     */
    public static void Import(File txtFile) throws FileNotFoundException {
        Album album;

        try (Scanner txtScanner = new Scanner(txtFile)) {
            while (txtScanner.hasNextLine()) {
                int id;
                int userRating;
                int trackCount;
                int runtime;

                String line = txtScanner.nextLine();
                // Split line by dash separator
                String[] values = line.split("-");

                // Validate that there are only 7 properties
                if (values.length != 7) {
                    System.out.println("Line [" + line + "] is incorrectly formatted.");
                    System.out.println("Try replacing any dashes that aren't used to separate album properties with other characters.");
                    continue;
                }

                // Assign properties
                String name = values[1].trim();
                String artistName = values[2].trim();
                String genre = values[3].trim();
                try {
                    id = Integer.parseInt(values[0]);
                    userRating = Integer.parseInt(values[4]);
                    trackCount = Integer.parseInt(values[5]);
                    runtime = Integer.parseInt(values[6]);

                    // Further logic for creating the album
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid number format in line [" + line + "]");
                    continue;
                }

                // Validate that ID isn't already in use
                if (Main.isAlbumIdUsed(id)) {
                    System.out.println("An album with the ID [" + id + "] already exists!");
                    continue;
                }

                // Attempt to create new album using assigned properties
                try {
                    album = new Album(id, name, artistName, genre, userRating, trackCount, runtime);
                } catch (Exception e) {
                    System.out.println("Album [" + id + "] could not be created.\n" + e.getMessage());
                    continue;
                }

                // Import album to DB
                System.out.println("Importing album [" + album.toString() + "].");
                Main.addAlbumToDB(album);
            }
        }
    }
}
