// Name: Michael Hollingsworth
// Course: CEN-3024C - Software Development 1
// CRN: 24204
// Date: 3/30/2025
// Class: DBHelper.java
// Description: This class provides helper functions for interacting with the SQLite database

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    // Name: DBHelper
    // Description: Instantiator for class
    // Inputs: none
    // Outputs: none
    public DBHelper() {
        connection = null;
        statement = null;
        resultSet = null;
    }

    // Name: connect
    // Description: Connect to the SQLite DB selected at the start of the application
    // Inputs: none
    // Outputs: none
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
