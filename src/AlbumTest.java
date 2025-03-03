import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class AlbumTest {

    // Create object to test
    Album album;

    @BeforeEach
    void setUp() {
        album = new Album(1, "goodname", "goodartist", "goodgenre",  7, 9, 1234);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Get Runtime String Test")
    void getRuntimeString_Test() {
        // Using test data of 1234, convert the time in seconds to HH:MM:SS
        int runtime = album.getRuntime();
        int hours = runtime / 3600;
        int minutes = (runtime / 60) % 60;
        int seconds = runtime % 60;

        String testData = (hours + ":" + minutes + ":" + seconds);

        // assertEquals compares two values and displays a message if they aren't equal
        assertEquals(testData, album.getRuntimeString(), "Error: the runtime string does not match the expected test data.");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Add Album to DMS Test")
    void addAlbum_Test() {
        int prev = Main.albums.size();
        Main.addAlbumToDB(album);

        // verify that the album added is the first one in the internal DB and that it is equal to our test album
        assertEquals(album, Main.albums.getFirst());

        //Verify that the internal DB only contains 1 album
        assertEquals((prev + 1), Main.albums.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Album from DMS Test")
    void removeAlbum_Test() {
        int prev = Main.albums.size();
        Main.removeAlbumFromDB(album);

        // Verify that the internal DB is now empty
        assertEquals((prev - 1), Main.albums.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Import Albums From File Test")
    void s_importAlbums_test() {
        int prev = Main.albums.size();

        File txtFile = new File("C:\\Users\\Michael Gaming\\Downloads\\albumImport.txt");
        try {
            TxtImporter.Import(txtFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Album> expected = new ArrayList<>();
        expected.add(new Album(1, "goodname", "goodartist", "goodgenre", 7, 9, 1234));
        expected.add(new Album(6, "good", "good", "good", 8, 12, 5121));
        expected.add(new Album(7, "good", "good", "good", 9, 14, 623));
        expected.add(new Album(8, "good", "good", "good", 8, 12, 7457));
        expected.add(new Album(9, "good", "good", "good", 8, 12, 3553));
        expected.add(new Album(10, "good", "good", "good", 8, 12, 824));
        expected.add(new Album(11, "good", "good", "good", 8, 12, 3272));
        expected.add(new Album(12, "good", "good", "good", 8, 12, 4261));
        expected.add(new Album(13, "good", "good", "good", 8, 12, 1232));
        expected.add(new Album(14, "good", "good", "good", 8, 12, 721));
        expected.add(new Album(15, "good", "good", "good", 8, 12, 3217));
        expected.add(new Album(16, "good", "good", "good", 8, 12, 2854));
        expected.add(new Album(17, "good", "good", "good", 8, 12, 4185));
        expected.add(new Album(18, "good", "good", "good", 8, 12, 1727));
        expected.add(new Album(19, "good", "good", "good", 8, 12, 1262));
        expected.add(new Album(20, "good", "good", "good", 8, 12, 7212));

        // Verify that the two Lists are the same size
        assertEquals(expected.size(), Main.albums.size());

        // Verify that each item is the same and that they are in the same order
        for (int i = 0; i < expected.size(); i++) {
            assertEquals((expected.get(i)).toString(), (Main.albums.get(i)).toString());
        }
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album ID in DMS Test")
    void updateAlbumID_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.id, "2");

        // Verify the first album's ID is now 2
        assertEquals(2, Main.albums.getFirst().id);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album Name in DMS Test")
    void updateAlbumName_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.name, "goodername");

        // Verify the first album's name is now "goodername"
        assertEquals("goodername", Main.albums.getFirst().name);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album Artist in DMS Test")
    void updateAlbumArtist_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.artistName, "newartist");

        // Verify the first album's artist is now "newartist"
        assertEquals("newartist", Main.albums.getFirst().artistName);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album Genre in DMS Test")
    void updateAlbumGenre_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.genre, "testgenre");

        // Verify the first album's genre is now "testgenre"
        assertEquals("testgenre", Main.albums.getFirst().genre);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album Rating in DMS Test")
    void updateAlbumRating_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.userRating, "8");

        // Verify the first album's rating is now 8
        assertEquals(8, Main.albums.getFirst().userRating);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album Track Count in DMS Test")
    void updateAlbumTrackcount_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.trackCount, "10");

        // Verify the first album's track count is now 10
        assertEquals(10, Main.albums.getFirst().trackCount);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Album Runtime in DMS Test")
    void updateAlbumRuntime_Test() {
        Main.albums.set(0, album);
        Main.updateAlbum(1, Main.AlbumProperty.runtime, "1523");

        // Verify that the runtime is now 1523
        assertEquals(1523, Main.albums.getFirst().runtime);
    }
}