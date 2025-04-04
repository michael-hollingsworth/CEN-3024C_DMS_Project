// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: Album.java
// Description: This class houses the album object. This object is used to store properties and methods for the object type

/**
 * The Album class is used to create album object which represent the properties of an album and includes logic to verify that the property values are valid.
 */
public class Album {
    public int id;
    public String name;
    public String artistName;
    public String genre;
    int userRating;
    int trackCount;
    int runtime;

    /**
     * This is the main constructor used to create an Album object.
     * @param id The integer ID of the album within the DB. This value must be positive.
     * @param name The String name of the album. This value cannot be null or whitespace.
     * @param artistName The String artist name of the album. This value cannot be null or whitespace.
     * @param genre The String genere of the album. This value cannot be null or whitespace.
     * @param userRating The Integer rating of the album between 0 and 10.
     * @param trackCount The Integer number of tracks on the album. This value must be positive.
     * @param runtime The runtime of the album in Integer seconds. This value must be positive.
     */
    public Album(int id, String name, String artistName, String genre, int userRating, int trackCount, int runtime) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        // Error if userRating is negative or greater than 10
        if (userRating < 0 || userRating > 10) {
            throw new IllegalArgumentException("User rating must be a number between 0 and 10");
        }

        // Error if trackCount is negative
        if (trackCount < 0) {
            throw new IllegalArgumentException("Track count must be a positive number");
        }

        // Error if runtime is negative
        if (runtime < 0) {
            throw new IllegalArgumentException("Runtime must be a positive number");
        }

        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.genre = genre;
        this.userRating = userRating;
        this.trackCount = trackCount;
        this.runtime = runtime;
    }

    /**
     * A basic constructor used to create an empty Album object.
     */
    public Album() {

    }

    /**
     * This is a getter method for the Integer ID of the album.
     * @return The Integer ID of the album.
     */
    public int getId() {
        return id;
    }

    /**
     * This is a getter method for the String name of the album.
     * @return The String name of the album.
     */
    public String getName() {
        return name;
    }

    /**
     * This is a getter method for the String artist name of the album.
     * @return The String artist name of the album.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * This is a getter method for the String genre of the album.
     * @return The String genre of the album.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * This is a getter method for the Integer user rating of the album.
     * @return The Integer user rating of the album.
     */
    public int getUserRating() {
        return userRating;
    }

    /**
     * This is a getter method for the Integer track count of the album.
     * @return The Integer track count of the album.
     */
    public int getTrackCount() {
        return trackCount;
    }

    /**
     * This is a getter method for the Integer runtime of the album in seconds.
     * @return The Integer runtime of the album in seconds.
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * This is a getter method to convert the Integer runtime in seconds of an album to a human-readable format.
     * @return The human-readable String format of the runtime of the album (HH:MM:SS).
     */
    public String getRuntimeString() {
        int hours = runtime / 3600;
        int minutes = (runtime / 60) % 60;
        int seconds = runtime % 60;

        return (hours + ":" + minutes + ":" + seconds);
    }


    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setUserRating(int userRating) {
        // Error if userRating is negative or greater than 10
        if (userRating < 0 || userRating > 10) {
            throw new IllegalArgumentException("User rating must be a number between 0 and 10");
        }

        this.userRating = userRating;
    }
    public void setTrackCount(int trackCount) {
        if (trackCount < 0) {
            throw new IllegalArgumentException("Track count must be a positive number");
        }

        this.trackCount = trackCount;
    }
    public void setRuntime(int runtime) {
        if (runtime < 0) {
            throw new IllegalArgumentException("Runtime must be a positive number");
        }

        this.runtime = runtime;
    }

    /**
     * This method converts the Album object to a dash-separated String formatted representation of the Album object.
     * @return A String representing the properties of the Album object
     */
    @Override
    public String toString() {
        return (String.format("%07d", id) + "-" + name + "-" + artistName + "-" + genre + "-" + userRating + "-" + trackCount + "-" + (this.getRuntimeString()));
    }

}
