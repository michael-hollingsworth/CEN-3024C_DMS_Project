// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 2/23/2025
// Class: Album.java
// Description: This class houses the album object. This object is used to store properties and methods for the object type

public class Album {
    public int id;
    public String name;
    public String artistName;
    public String genre;
    int userRating;
    int trackCount;
    int runtime;

    // Constructor
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

    public Album() {

    }

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getArtistName() {
        return artistName;
    }
    public String getGenre() { return genre; }
    public int getUserRating() { return userRating; }
    public int getTrackCount() { return trackCount; }
    public int getRuntime() { return runtime; }

    // Custom method to convert runtime (in seconds) to a human-readable/friendly format
    public String getRuntimeString() {
        int hours = runtime / 3600;
        int minutes = (runtime / 60) % 60;
        int seconds = runtime % 60;

        return (hours + ":" + minutes + ":" + seconds);
    }


    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void setGenre(String genre) { this.genre = genre; }
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

    // toString() override
    @Override
    public String toString() {
        return (String.format("%07d", id) + "-" + name + "-" + artistName + "-" + genre + "-" + userRating + "-" + trackCount + "-" + (this.getRuntimeString()));
    }

}
