// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: RemoveAlbum.java
// Description: This class is used to remove albums from the internal db

import java.util.Scanner;
import java.util.List;

public class RemoveAlbum {
    // Name: run
    // Description: This method is used to create a submenu for removing albums from the internal db
    // Inputs:
        // Scanner scanner: The scanner object which is passed through to collect user input
        // List<Album> albums: The list of albums that is passed through for processing
    // Outputs:
        // List<Album>: The updated list of albums after the album(s) have been removed.

    private static Scanner scanner = new Scanner(System.in);

    public static void run() {
        while (true) {
            int idToRemove;
            int index = -1;

            // Options
            Main.printAlbums();
            System.out.println();
            System.out.println("Enter the ID of the album you want to remove or [exit] to exit:");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                // Restart loop if the input is blank
                continue;
            } else if (input.equals("exit")) {
                // Return album list ("internal db") to previous context
                return;
            }

            // Parse integer
            try {
                idToRemove = Integer.parseInt(input);

                if (idToRemove < 0) {
                    System.out.println("Invalid option! Select one of the following options:");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option! Select one of the following options:");
                continue;
            }

            // Get the album object that should be removed from its ID
            Album albumToRemove = Main.getAlbumById(idToRemove);

            // Verify that the album exists within the internal DB
            if (albumToRemove == null) {
                System.out.println("Invalid option! Select one of the following options:");
                continue;
            }

            // Remove the album
            Main.removeAlbumFromDB(albumToRemove);
        }
    }
}
