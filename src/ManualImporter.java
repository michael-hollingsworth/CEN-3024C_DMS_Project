// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: ManualImporter.java
// Description: This class is used for manually entering data that will be turned into album objects

import java.util.Scanner;
import java.util.List;

public class ManualImporter {

    private static Scanner scanner = new Scanner(System.in);

    // Name: run
    // Description: This method is used to create a submenu where the user can manually enter the data for an album that they'd like to import into the db
    // Inputs:
        // Scanner scanner: The scanner object which is passed through to collect user input
        // List<Album> albums: The list of albums that is passed through for processing
    // Outputs:
        // List<Album>: The updated list of albums after the album(s) have been imported.
    public static void run() {
        while (true) {
            int id;
            String name;
            String artistName;
            String genre;
            int userRating;
            int trackCount;
            int runtime;
            Album album;


            // ID
            while (true) {
                System.out.println("Enter album ID or [exit] to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                } else if (input.equals("exit")) {
                    // Return album list ("internal db") to previous context
                    return;
                }

                // Parse integer
                try {
                    id = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid album ID! Album ID must be a positive integer.");
                    continue;
                }

                // Verify that ineger is positive
                if (id < 0) {
                    System.out.println("Invalid album ID! Album ID must be a positive integer.");
                    continue;
                }

                // Verify that the ID is not currently used
                if (Main.isAlbumIdUsed(id)) {
                    System.out.println("A album with the ID [" + id + "] already exists!");
                    continue;
                }

                break;
            }

            // Album name
            while (true) {
                System.out.println("Enter album name or 'exit' to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {

                } else if (input.equals("exit")) {
                    return;
                } else {
                    name = input;
                    break;
                }
            }

            // Artist name
            while (true) {
                System.out.println("Enter album artist name or 'exit' to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {

                } else if (input.equals("exit")) {
                    return;
                } else {
                    artistName = input;
                    break;
                }
            }

            // Genre
            while (true) {
                System.out.println("Enter album genre or 'exit' to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    // Restart loop if input is null/whitespace
                    continue;
                } else if (input.equals("exit")) {
                    return;
                }

                genre = input;
                break;
            }

            // Rating
            while (true) {
                System.out.println("Enter album rating or 'exit' to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    // Restart loop if input is null/whitespace
                    continue;
                } else if (input.equals("exit")) {
                    return;
                }

                // Parse integer
                try {
                    userRating = Integer.parseInt(input);

                    if (userRating < 0 || userRating > 10) {
                        System.out.println("Invalid album rating! Album rating must be an integer between 0 and 10.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid album rating! Album rating must be an integer between 0 and 10.");
                    continue;
                }

                break;
            }

            // Track count
            while (true) {
                System.out.println("Enter track count or 'exit' to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    // Restart loop if input is null/whitespace
                    continue;
                } else if (input.equals("exit")) {
                    return;
                }

                // Parse integer
                try {
                    trackCount = Integer.parseInt(input);

                    if (trackCount < 0) {
                        System.out.println("Invalid track count! Track count must be a positive integer.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid track count! Track count must be a positive integer.");
                    continue;
                }

                break;
            }

            // Runtime
            while (true) {
                System.out.println("Enter album runtime or 'exit' to exit:");

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    // Restart loop if input is null/whitespace
                    continue;
                } else if (input.equals("exit")) {
                    return;
                }

                // Parse integer
                try {
                    runtime = Integer.parseInt(input);

                    if (runtime < 0) {
                        System.out.println("Invalid runtime! Runtime must be a positive integer.");
                        continue;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid runtime! Runtime must be a positive integer.");
                }
            }

            // Create album using the parameters gathered in the previous while loops
            try {
                album = new Album(id, name, artistName, genre, userRating, trackCount, runtime);
            } catch (Exception e) {
                System.out.println("Album [" + id + "] could not be created.\n" + e.getMessage());
                continue;
            }

            // Add album object to internal db
            Main.addAlbumToDB(album);
        }
    }
}
