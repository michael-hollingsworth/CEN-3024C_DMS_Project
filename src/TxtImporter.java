// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: TxtImporter.java
// Description: This class is used to import one or more albums from a text file.

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class TxtImporter {

    private static Scanner scanner = new Scanner(System.in);

    // Name: run
    // Description: This method creates the submenu for the text file importing feature.
                 // This method contains the logic necessary for creating the File object that is passed to the Import() method.
    // Inputs:
        // Scanner scanner: The scanner object which is passed through to collect user input
        // List<Album> albums: The list of albums that is passed through for processing
    // Outputs:
        // List<Album>: The updated list of albums after the album(s) have been imported.
    public static void run() {
        while (true) {
            System.out.println("Enter full path to text file or [exit] to exit:");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                // Restart loop if the input is blank
                continue;
            } else if (input.equals("exit")) {
                return;
            }

            // Remove leading and trailing quotes
            input = input.replaceAll("^\"|\"$", "");

            File txtFile = new File(input);
            if (!txtFile.exists()) {
                System.out.println("File path [" + input + "] could not be found.\n");
                continue;
            }

            if (!txtFile.isFile()) {
                System.out.println("File path [" + input + "] isn't a file.\n");
                continue;
            }

            try {
                Import(txtFile);
            } catch (FileNotFoundException e) {
                System.out.println("File path [" + input + "] could not be found.\n");
            }
        }
    }

    // Name: Import
    // Description: Method contains the logic necessary to import albums from a text file
    // Arguments:
        // File txtFile: The File object representing the text file that will be imported.
                      // This object is passed to a scanner to parse the file's contents
    // Outputs:
        // List<Album>: The list of albums imported from the text file. These albums will be added to the internal db by the run() method
    public static void Import(File txtFile) throws FileNotFoundException {
        Scanner txtScanner = new Scanner(txtFile);
        Album album;

        while (txtScanner.hasNextLine()) {
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
            int id = Integer.parseInt(values[0]);
            String name = values[1];
            String artistName = values[2];
            String genre = values[3];
            int userRating = Integer.parseInt(values[4]);
            int trackCount = Integer.parseInt(values[5]);
            int runtime = Integer.parseInt(values[6]);

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
            Main.addAlbumToDB(new Album(id, name, artistName, genre, userRating, trackCount, runtime));
        }

        txtScanner.close();
    }
}
