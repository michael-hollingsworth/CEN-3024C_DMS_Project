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


    // Internal DB
    public static List<Album> albums = new ArrayList<Album>();

    public static void main(String[] args) {

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        String choice;
        int choiceInt;

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
                        TxtImporter.run();
                        break;
                    case 2:
                        ManualImporter.run();
                        break;
                    case 3:
                        UpdateAlbum.run();
                        break;
                    case 4:
                        RemoveAlbum.run();
                        break;
                    case 5:
                        System.out.println();
                        System.out.println("Albums:");

                        if (albums.isEmpty()) {
                            System.out.println("No albums in the DMS.");
                        } else {
                            printAlbums();
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

    // Name: printAlbums
    // Description: prints a list of all albums in the DB
    // Inputs: none
    // Outputs: none (technically it outputs to the console, not the previous context)
    public static void printAlbums() {
        for (Album album : albums) {
            System.out.println(album.toString());
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
    public static boolean isAlbumIdUsed(int id) {
        for (Album album : albums) {
            if (album.getId() == id) {
                return true;
            }
        }

        return false;
    }


    // Name: getAlbumById
    // Description: Gets an album from the internal DB based on its ID
    // Inputs:
        // int id: The album id that will be checked.
    // Outputs:
        // Album: The album with a matching ID
        // null: If no album has the ID provided, null is returned
    public static Album getAlbumById(int id) {
        for (Album album : albums) {
            if (album.getId() == id) {
                return album;
            }
        }

        return null;
    }

    // Name: addAlbumToDB
    // Description: Adds an album to the internal DB
    // Inputs:
        // Album album: The album that will be added to the DB.
    // Outputs: none
    public static void addAlbumToDB(Album album) {
        albums.add(album);
    }


    // Name: removeAlbumFromDB
    // Description: Adds an album to the internal DB
    // Inputs:
        // Album album: The album that will be removed from the DB.
    // Outputs: none
    public static void removeAlbumFromDB(Album albumToRemove) {
        int index = -1;

        // Check if an album contains the ID provided
        for (Album album : albums) {
            if (album.id == albumToRemove.id) {
                System.out.println("Removing album [" + album.toString() + "].");
                index = albums.indexOf(album);
                break;
            }
        }

        // Verify that the object exists; if index is -1, the object doesn't exist in the db
        if (index == -1) {
            return;
        }

        albums.remove(index);
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
            case id:
                int newID;

                // Parse integer
                try {
                    newID = Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid album ID! Album ID must be a positive integer.");
                }

                // Verify that the album ID is valid
                if (newID < 0) {
                    throw new IllegalArgumentException("Invalid album ID! Album ID must be a positive integer.");
                }

                // Verify that album ID isn't currently being used
                if (isAlbumIdUsed(newID)) {
                    if (albumToModify.id == newID) {
                        // If they are setting the ID to the current ID, it isn't an issue, we will just not do anything
                        return;
                    }
                    throw new IllegalArgumentException("A album with the ID [" + newID + "] already exists!");
                }

                // Update album ID property
                albumToModify.setId(newID);

                return;
            case name:
                // Validate that the name isn't null/whitespace
                if (newValue.isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty!");
                }

                // Update album name property
                albumToModify.setName(newValue);
                return;
            case artistName:
                // Validate that the artist name isn't null/whitespace
                if (newValue.isEmpty()) {
                    throw new IllegalArgumentException("Artist name cannot be empty!");
                }

                // Update artist name property
                albumToModify.setArtistName(newValue);

                return;
            case genre:
                // Validate that the genre isn't null/whitespace
                if (newValue.isEmpty()) {
                    throw new IllegalArgumentException("Genre cannot be empty!");
                }

                // Update genre property
                albumToModify.setGenre(newValue);

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
                albumToModify.setUserRating(newUserRating);
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
                albumToModify.setTrackCount(newTrackCount);
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
                albumToModify.setRuntime(newRuntime);
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
}