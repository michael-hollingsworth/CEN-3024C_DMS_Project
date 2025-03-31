// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 3/30/2025
// Class: AlbumsDB.java
// Description: This class provides functions for interacting with the SQLite database

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.sql.*;

public class AlbumsDB extends DBHelper {
    public static final String TABLE_NAME = "Albums";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_ARTIST = "ArtistName";
    public static final String COLUMN_GENRE = "Genre";
    public static final String COLUMN_USER_RATING = "UserRating";
    public static final String COLUMN_TRACK_COUNT = "TrackCount";
    public static final String COLUMN_RUNTIME = "Runtime";

    // Name: prepareSQL
    // Description: Prepares the text of a SQL "SELECT" command.
    // Inputs:
        // String fields: The fields that will be returned by the SELECT command
        // String whatField: Field to search for
        // String whatValue: The value to search for within "whatField"
        // String sortField: The field to sort the output by
        // String sort: The type of sort
    // Outputs:
        // String: SQL "SELECT" query
    private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
        String query = "SELECT ";
        query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
        query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
        query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
        return query;
    }

    // Name: insert
    // Description: Adds an album object to the SQLite DB
    // Inputs:
        // Album album: The album to add to the DB
    // Outputs: none
    public void insert(Album album) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_ARTIST + ", " +
                COLUMN_GENRE + ", " + COLUMN_USER_RATING + ", " +
                COLUMN_TRACK_COUNT + ", " + COLUMN_RUNTIME +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + Main.dbFile.getAbsolutePath());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, album.id);
            statement.setString(2, album.name);
            statement.setString(3, album.artistName);
            statement.setString(4, album.genre);
            statement.setInt(5, album.userRating);
            statement.setInt(6, album.trackCount);
            statement.setInt(7, album.runtime);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // or use a logging framework
        }
    }

    // Name: delete
    // Description: Deletes an object from the DB based on the criteria provided
    // Inputs:
        // String whatField: The field key used to determine if a row should be deleted
        // String whatValue: The value that is searched for when deleting a row
    // Outputs: none
    public void delete(String whatField, String whatValue) {
        super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
    }

    // Name: update
    // Description: Updates a record in the database
    // Inputs:
        // String whatField: The field to update
        // String whatValue: The value that will be placed in the field
        // String whereField: The search criteria when updating a record
        // String whereValue: The search value when updating a record
    // Outputs: none
    public void update(String whatField, String whatValue, String whereField, String whereValue) {
        super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
    }

    // Name: select
    // Description: Selects a record or records from the database based on the criteria provided
    // Inputs:
        // String fields: The fields that will be returned by the SELECT command
        // String whatField: Field to search for
        // String whatValue: The value to search for within "whatField"
        // String sortField: The field to sort the output by
        // String sort: The type of sort
    // Outputs:
        // ArrayList<ArrayList<Object>>: The output from the select query
    public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
        return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
    }

    // Name: getExecuteResult
    // Description: Selects a record or records from the database based on the criteria provided
    // Inputs:
        // String query: The query to perform
    // Outputs:
        // ArrayList<ArrayList<Object>>: The output from the SQL query
    public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
        return super.executeQuery(query);
    }

    // Name: execute
    // Description: Executes a SQL command on the database
    // Inputs:
        // String query: The SQL command to execute
    // Outputs: none
    public void execute(String query) {
        super.execute(query);
    }

    // Name: selectToTable
    // Description: Performs a SQL query and outputs a DefaultTableModel representing the data returned from the query
    // Inputs:
        // String fields: The fields that will be returned by the SELECT command
        // String whatField: Field to search for
        // String whatValue: The value to search for within "whatField"
        // String sortField: The field to sort the output by
        // String sort: The type of sort
    // Outputs:
        // DefaultTableModel: a DefaultTableModel representation of the data returned by the SQL query
    public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
        return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
    }
}
