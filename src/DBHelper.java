// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 3/30/2025
// Class: DBHelper.java
// Description: This class provides helper functions for interacting with the SQLite database

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * The DBHelper class contains a few basic methods used to connect to and interact with a SQLite database
 */
public class DBHelper {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    // Name: DBHelper
    // Description: Instantiator for class
    // Inputs: none
    // Outputs: none

    /**
     * An instantiator for the DBHelper class
     */
    public DBHelper() {
        connection = null;
        statement = null;
        resultSet = null;
    }

    // Name: connect
    // Description: Connect to the SQLite DB selected at the start of the application
    // Inputs: none
    // Outputs: none

    /**
     * The connect() method is used to connect to the database. This method connects to the DB file selected by the user at the start of the application.
     */
    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Connecting to database [" + Main.dbFile.getAbsolutePath() + "]");
            connection = DriverManager.getConnection("jdbc:sqlite:" + Main.dbFile.getAbsolutePath());
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Name: close
    // Description: Close the connection to the SQLite DB
    // Inputs: none
    // Outputs: none

    /**
     * The close() method is used to close any active DB connections and cleanup memory.
     */
    private void close() {
        try {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Name: arrayListTo2DArray
    // Description: Convert 2D ArrayList to 2D Array
    // Inputs:
        // ArrayList<ArrayList<Object>> list: 2D ArrayList that will be converted to a 2D Array
    // Outputs:
        // Object[][]: 2D Array representation of the 2D Arraylist input

    /**
     * arrayListTo2DArray converts a 2D ArrayList to a 2D Array.
     * @param list The 2D ArrayList that you would like to convert to a 2D array.
     * @return A 2D Object Array representing the data input.
     */
    private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
        Object[][] array = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Object> row = list.get(i);
            array[i] = row.toArray(new Object[row.size()]);
        }
        return array;
    }

    // Name: execute
    // Description: Executes a SQL query
    // Inputs:
        // String sql: SQL query to execute
    // Outputs: none

    /**
     * The execute() method is used to execute a SQL statement. The statement can be any type of statement but it will not return any data.
     * @param sql The String form of the SQL statement that you are attempting to execute.
     */
    protected void execute(String sql) {
        try {
            connect();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    // Name: executeQueryToTable
    // Description: Executes a SQL query and returns a DefaultTableModel representing the data
    // Inputs:
        // String sql: SQL query to execute
    // Outputs:
        // DefaultTableModel: Table with the information returned from the query

    /**
     * The executeQueryToTable() method executes a SQL query and outputs a DefaultTableModel object with the data returned.
     * @param sql The String form of the SQL query that you are attempting to search.
     * @return A DefaultTableModel representing the data returned form the SQL query.
     */
    protected DefaultTableModel executeQueryToTable(String sql) {
        ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> columns = new ArrayList<Object>();
        connect();
        try {
            resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++)
                columns.add(resultSet.getMetaData().getColumnName(i));
            while (resultSet.next()) {
                ArrayList<Object> subresult = new ArrayList<Object>();
                for (int i = 1; i <= columnCount; i++)
                    subresult.add(resultSet.getObject(i));
                result.add(subresult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
    }

    // Name: executeQuery
    // Description: Executes a SQL query and returns a 2D ArrayList representing the data
    // Inputs:
        // String sql: SQL query to execute
    // Outputs:
        // ArrayList<ArrayList<Object>>: 2D ArrayList with the information returned from the query

    /**
     * executeQuery is used to execute a SQL SELECT query and return a 2D ArrayList of the data returned by the SQL query.
     * @param sql The String form of the SQL query that you are attempting to search.
     * @return A 2D ArrayList representing the data returned form the SQL query.
     */
    protected ArrayList<ArrayList<Object>> executeQuery(String sql) {
        ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
        connect();
        try {
            resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                ArrayList<Object> subresult = new ArrayList<Object>();
                for (int i = 1; i <= columnCount; i++) {
                    subresult.add(resultSet.getObject(i));
                }
                result.add(subresult);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        close();
        return result;
    }
}
