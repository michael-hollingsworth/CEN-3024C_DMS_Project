// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: Main.java
// Description: This DMS application is used to keep track of the albums that an individual has listened to along with their ratings

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Name: main
    // Description: Main method for running the program
    // Inputs: none
    // Outputs: none
    public static void main(String[] args) {

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        String choice;
        int choiceInt;

        List<Album> albums = new ArrayList<Album>();

        try {
            while (true) {
                // Options
                System.out.println("Enter the number of one of the options below and press enter:");
                System.out.println("0: Exit");
                System.out.println("1: Import album(s) from text file.");
                System.out.println("2: Manually enter new album.");
                System.out.println("3: Edit existing album.");
                System.out.println("4: Remove album.");
                System.out.println("5: View list of album");

                choice = scanner.nextLine();
                try {
                    choiceInt = Integer.parseInt(choice);
                } catch (NumberFormatException e)  {
                    System.out.println("Invalid input! Please enter an integer.\n");
                    continue;
                }
                switch (choiceInt) {
                    case 0:
                        System.exit(0);
                        return;
                    case 1:
                        albums = TxtImporter.run(scanner, albums);
                        break;
                    case 2:
                        albums = ManualImporter.run(scanner, albums);
                        break;
                    case 3:
                        albums = UpdateAlbum.run(scanner, albums);
                        break;
                    case 4:
                        albums = RemoveAlbum.run(scanner, albums);
                        break;
                    case 5:
                        System.out.println();
                        System.out.println("Albums:");

                        if (albums.isEmpty()) {
                            System.out.println("No albums in the DMS.");
                        } else {
                            for (Album album : albums) {
                                System.out.println(album.toString());
                            }
                            System.out.println();
                        }

                        System.out.println("Press [enter] to continue.");
                        choice = scanner.nextLine();
                        break;
                    default:
                        System.out.println("Invalid input\n");
                        break;
                }
            }
        } finally {
            scanner.close();
        }
    }

    // Name: isAlbumIdUsed
    // Description: Checks if an album id is currently in use. While not particularly useful now,
                 // it will be when the SQL database is added and the id is used as a PK.
    // Inputs:
        // List<Album>: The list of albums to check against
        // int id: The album id that will be checked.
    // Outputs:
        // boolean: true if the id is in use and false if the id is not in use.
    public static boolean isAlbumIdUsed(List<Album> albums, int id) {
        for (Album album : albums) {
            if (album.getId() == id) {
                return true;
            }
        }

        return false;
    }
}