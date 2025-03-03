// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: UpdateAlbum.java
// Description: This class is used for manually updating data for existing album objects within the db.

import java.util.Scanner;
import java.util.List;

public class UpdateAlbum {

    private static Scanner scanner = new Scanner(System.in);

    // Name: run
    // Description: This method is used to create a submenu where the user can manually edit the data for an album that is already in the db
    // Inputs:
        // Scanner scanner: The scanner object which is passed through to collect user input
        // List<Album> albums: The list of albums that is passed through for processing
    // Outputs:
        // List<Album>: The updated list of albums after the album(s) have been modified.
    public static void run() {
        while (true) {
            int idToModify;
            String input;
            int choiceInt;

            // Options
            Main.printAlbums();
            System.out.println();
            System.out.println("Enter the ID of the album you want to edit or [exit] to exit:");

            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                // Restart loop if the input is blank
                continue;
            } else if (input.equals("exit")) {
                return;
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

            // Loop for editing properties of selected album
            editLoop: while (true) {
                int userRating;
                int trackCount;
                int runtime;
                String newValue;

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

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }

                            System.out.println("Album ID: " + idToModify);
                            System.out.println("new val: " + newValue);

                            // Try to update the album ID
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }

                            break;
                        }
                        break;
                    case 2:
                        // Album name
                        while (true) {
                            System.out.println("Enter album name or 'exit' to exit:");

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }

                            // Try to Update the property
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                            break;
                        }
                        break;
                    case 3:
                        // Artist name
                        while (true) {
                            System.out.println("Enter artist name or 'exit' to exit:");

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }

                            // Try to Update the property
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                            break;
                        }
                        break;
                    case 4:
                        // Genre
                        while (true) {
                            System.out.println("Enter album genre or 'exit' to exit:");

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }


                            // Try to Update the property
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                            break;
                        }
                        break;
                    case 5:
                        // Rating
                        while (true) {
                            System.out.println("Enter album rating or 'exit' to exit:");

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }

                            // Try to Update the property
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                            break;
                        }
                        break;
                    case 6:
                        // Track count
                        while (true) {
                            System.out.println("Enter album track count or 'exit' to exit:");

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                // Restart loop if input is null/whitespace
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }

                            // Try to Update the property
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                            break;
                        }
                        break;
                    case 7:
                        // Runtime
                        while (true) {
                            System.out.println("Enter album runtime in seconds or 'exit' to exit:");

                            newValue = scanner.nextLine().trim();
                            if (newValue.isEmpty()) {
                                continue;
                            } else if (newValue.equals("exit")) {
                                break;
                            }

                            // Try to Update the property
                            try {
                                Main.updateAlbum(idToModify, Main.AlbumProperty.id, newValue);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
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
