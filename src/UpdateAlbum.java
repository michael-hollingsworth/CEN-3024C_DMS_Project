// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: UpdateAlbum.java
// Description: This class is used for manually updating data for existing album objects within the db.

import java.util.Scanner;
import java.util.List;

public class UpdateAlbum {
    // Name: run
    // Description: This method is used to create a submenu where the user can manually edit the data for an album that is already in the db
    // Inputs:
        // Scanner scanner: The scanner object which is passed through to collect user input
        // List<Album> albums: The list of albums that is passed through for processing
    // Outputs:
        // List<Album>: The updated list of albums after the album(s) have been modified.
    public static List<Album> run(Scanner scanner, List<Album> albums) {
        while (true) {
            int idToModify;
            int index = -1;
            String input;
            int choiceInt;

            // Options
            for (Album album : albums) {
                System.out.println(album.toString());
            }
            System.out.println();
            System.out.println("Enter the ID of the album you want to edit or [exit] to exit:");

            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                // Restart loop if the input is blank
                continue;
            } else if (input.equals("exit")) {
                return albums;
            }

            // Parse integer
            try {
                idToModify = Integer.parseInt(input);

                if (idToModify < 0) {
                    System.out.println("Invalid option! Select one of the following options:");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option! Select one of the following options:");
                continue;
            }

            // Identify which album has been selected
            for (Album album : albums) {
                if (album.id == idToModify) {
                    System.out.println("Modifying album [" + album.toString() + "].");
                    // albums.remove(album);
                    index = albums.indexOf(album);

                }
            }

            // We are modifying this album
            Album albumToModify = albums.get(index);

            // Loop for editing properties of selected album
            editLoop: while (true) {
                int id;
                String name;
                String artistName;
                String genre;
                int userRating;
                int trackCount;
                int runtime;
                Album album;

                System.out.println("Enter the number for the option you would like to edit:");
                System.out.println("0: Exit");
                System.out.println("1: Album ID");
                System.out.println("2: Album name");
                System.out.println("3: Artist name");
                System.out.println("4: Genre");
                System.out.println("5: Rating");
                System.out.println("6: Track count");
                System.out.println("7: Runtime");

                // Identify which property to modify
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    // Restart loop if input is null/whitespace
                    continue;
                } else if (input.equals("exit")) {
                    break;
                }
                try {
                    choiceInt = Integer.parseInt(input);
                } catch (NumberFormatException e)  {
                    System.out.println("Invalid input! Please enter an integer.\n");
                    continue;
                }
                switch (choiceInt) {
                    case 0:
                        // Exit loop
                        break editLoop;
                    case 1:
                        // ID
                        while (true) {
                            System.out.println("Enter album ID or [exit] to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (input.equals("exit")) {
                                break;
                            }

                            try {
                                id = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid album ID! Album ID must be a positive integer.");
                                continue;
                            }

                            // Verify that the album ID is valid
                            if (id < 0) {
                                System.out.println("Invalid album ID! Album ID must be a positive integer.");
                                continue;
                            }

                            // Verify that album id isn't already being used
                            if (Main.isAlbumIdUsed(albums, id)) {
                                System.out.println("A album with the ID [" + id + "] already exists!");
                                continue;
                            }

                            albumToModify.setId(id);
                            break;
                        }
                        break;
                    case 2:
                        // Album name
                        while (true) {
                            System.out.println("Enter album name or 'exit' to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (input.equals("exit")) {
                                break;
                            } else {
                                name = input;
                            }

                            albumToModify.setName(name);
                            break;
                        }
                        break;
                    case 3:
                        // Artist name
                        while (true) {
                            System.out.println("Enter artist name or 'exit' to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (input.equals("exit")) {
                                break;
                            } else {
                                artistName = input;
                            }

                            albumToModify.setArtistName(artistName);
                            break;
                        }
                        break;
                    case 4:
                        // Genre
                        while (true) {
                            System.out.println("Enter album genre or 'exit' to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (input.equals("exit")) {
                                break;
                            } else {
                                artistName = input;
                            }

                            albumToModify.setArtistName(artistName);
                            break;
                        }
                        break;
                    case 5:
                        // Rating
                        while (true) {
                            System.out.println("Enter album rating or 'exit' to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (input.equals("exit")) {
                                break;
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

                            albumToModify.setUserRating(userRating);
                            break;
                        }
                        break;
                    case 6:
                        // Track count
                        while (true) {
                            System.out.println("Enter album track count or 'exit' to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (input.equals("exit")) {
                                break;
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

                            albumToModify.setTrackCount(trackCount);
                            break;
                        }
                        break;
                    case 7:
                        // Runtime
                        while (true) {
                            System.out.println("Enter album runtime in seconds or 'exit' to exit:");

                            input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                continue;
                            } else if (input.equals("exit")) {
                                break;
                            }

                            // Parse integer
                            try {
                                runtime = Integer.parseInt(input);

                                if (runtime < 0) {
                                    System.out.println("Invalid runtime! Runtime count must be a positive integer.");
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid runtime! Runtime count must be a positive integer.");
                                continue;
                            }

                            albumToModify.setRuntime(runtime);
                            break;
                        }
                        break;
                    default:
                        System.out.println("Invalid input\n");
                        break;
                }
            }
        }
    }
}
