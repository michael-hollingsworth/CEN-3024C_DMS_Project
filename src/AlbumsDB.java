// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 3/30/2025
// Class: AlbumsDB.java
// Description: This class provides functions for interacting with the SQLite database

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.sql.*;


/**
 * The AlbumsDB class extends the DBHelper class and implements additional methods used to specifically interact with the Albums DMS database.
 */
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

    /**
     * prepareSQL() is a simple method that creates a SELECT SQL query using the variables passed to the method. This method is primarily focused towards users who aren't very SQL savvy.
     * Example: prepareSQL("*", "Name", "test123", "Id", "DESC")
     * This example will generate the following: SELECT * FROM Albums WHERE Name = "test123" ORDER BY Id DESC;
     * This example will select all properties of the rows in the Albums table where the name of row is "test123". It will then sort the output by album ID in descending order.
     * @param fields A comma-separated String containing the fields you want returned from your SQL query.
     * @param whatField The name of the field you are attempting to query against.
     * @param whatValue The expected value of the field you have selected to query against.
     * @param sortField What field the output data should be sorted by.
     * @param sort The way the data should be sorted.
     * @return A String representing the fully prepared SQL query.
     */
    private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
        String query = "SELECT ";
        query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
        query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
        query += sort != null && sortField != null ? " ORDER BY " + sortField + " " + sort : "";
        return query;
    }

    // Name: insert
    // Description: Adds an album object to the SQLite DB
    // Inputs:
        // Album album: The album to add to the DB
    // Outputs: none

    /**
     * The insert() method takes an album object and inserts it into the DB.
     * @param album The album object to add to the DB.
     */
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

    /**
     * The delete() method deletes albums from the DB where the search criteria provided is met.
     * Example: delete("Id", "1")
     * This example will delete all albums from the DB where the Id is equal to 1.
     * @param whatField The name of the field to filter by
     * @param whatValue The value that the field selected must be equal to. Any albums/rows that match this criteria will be deleted
     */
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

    /**
     * Updates a property on one or more rows in the DB
     * Example: update("Name", "New Name", "Id", "123")
     * This example will update albums where the ID is 123 so that the name field is now "New Name".
     * @param whatField The field that will be updated.
     * @param whatValue The value that will replace the existing value.
     * @param whereField The field to filter by.
     * @param whereValue The value to filter by.
     */
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

    /**
     * Selects a record or records from the database based on the criteria provided. This method creates a SQL query from the fields provided.
     * @param fields The fields that will be returned by the SELECT command. This is a comma-separated String value.
     * @param whatField The field to search for
     * @param whatValue The value to search for within "whatField"
     * @param sortField The field to sort the output by
     * @param sort The type of sort
     * @return A 2D ArrayList of the output from the SELECT query.
     */
    public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
        return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
    }

    // Name: getExecuteResult
    // Description: Selects a record or records from the database based on the criteria provided
    // Inputs:
        // String query: The query to perform
    // Outputs:
        // ArrayList<ArrayList<Object>>: The output from the SQL query

    /**
     * Selects a record or records from the database based on the criteria provided.
     * @param query The SQL query to execute.
     * @return A 2D ArrayList of the output from the SELECT query.
     */
    public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
        return super.executeQuery(query);
    }

    // Name: execute
    // Description: Executes a SQL command on the database
    // Inputs:
        // String query: The SQL command to execute
    // Outputs: none

    /**
     * This method is used to execute a SQL statement without returning an output.
     * @param query The String form of the SQL statement that you are attempting to execute.
     */
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

    /**
     * Performs a SQL query and outputs a DefaultTableModel representing the data returned from the query.
     * @param fields The fields that will be returned by the SELECT command
     * @param whatField The field to search for
     * @param whatValue The value to search for within "whatField"
     * @param sortField The field to sort the output by
     * @param sort The type of sort
     * @return A DefaultTableModel representing the data returned by the SQL query.
     */
    public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
        return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
    }
}
