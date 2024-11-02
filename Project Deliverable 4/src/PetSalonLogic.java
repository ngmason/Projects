import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class handles all the internal logic for handling and manipulating
 * data in the Pet Salon database. Users interact using the built-in methods.
 *
 * @version 1.0
 * @author Weronika Golden, Anne Himes, Nina Mason, Andrew Tellez
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"}) //REMOVE ME BEFORE SUBMITTING
public class PetSalonLogic {

    /**
     * Variable that links java program to sql database
     */
    private final Connection conn;

    /**
     * Constructor for PetSalonLogic object
     * @param link connection variable that links to sql database
     */
    public PetSalonLogic(Connection link) {
        this.conn = link;
    }

    /**
     * This method allows a user to add a new row to an existing table
     * in the Pet Salon database.
     */
    public void add() throws SQLException {

        //User selects a table to add to
        String table = selectTable();

        //Direct user to table specific add logic
        HashMap<Integer, String> columns = getColumnNames(table);
        addQuery(table, columns);
    }

    /**
     * This method allows a user to delete a row from an existing table
     * in the Pet Salon database.
     */
    public void delete() throws SQLException {
        Scanner sc = new Scanner(System.in);

        //User selects a table
        String table = selectTable();

        //If user selection invalid, return
        if (table == null) return;

        //Display user selected table and store row numbers and primary keys
        search(table);
        HashMap<Integer, String> primaryKeys = getPrimaryKeys(table);

        //User selects a row to delete
        System.out.print("Select the number of the row you would like to delete: ");
        int row = sc.nextInt();
        System.out.println();

        //Test for valid row number and execute query
        if (primaryKeys.containsKey(row)) {
            deleteQuery(table, primaryKeys.get(row));
        }
        else {
            System.out.println("Invalid row number");
        }
    }

    /**
     * This method allows for a user to update attributes from a row
     * in an existing table in the Pet Salon database.
     */
    public void update() throws SQLException {
        Scanner sc = new Scanner(System.in);

        //User selects a table
        String table = selectTable();

        //If user selection invalid, return
        if (table == null) return;

        //Display user selected table and store row numbers and primary keys
        search(table);
        HashMap<Integer, String> primaryKeys = getPrimaryKeys(table);
        HashMap<Integer, String> columns = getColumnNames(table);

        //User selects a row to update
        System.out.print("Select the number of the row you would like to update: ");
        int row = sc.nextInt();
        System.out.println();

        //Test for valid row number and execute query
        if (primaryKeys.containsKey(row)) {
            updateQuery(table, primaryKeys.get(row), columns);
        }
        else {
            System.out.println("Invalid row number");
        }
    }

    /**
     * This method allows for a user to search for specific information
     * in the Pet Salon database.
     */
    public void search() throws SQLException {
        //Display menu and capture user selection
        String table = selectTable();

        //If user selection invalid, return
        if (table == null) return;

        //Display user selected table
        switch (table) {
            case "CUSTOMER":
                displayTable("CUSTOMER");
                break;
                case "EMPLOYEE":
                    displayTable("EMPLOYEE");
                    break;
                case "EMPLOYS":
                    displayTable("EMPLOYS");
                    break;
                case "GROOMER":
                    displayTable("GROOMER");
                    break;
                case "GROOMS":
                    displayTable("GROOMS");
                    break;
                case "HELPS":
                    displayTable("HELPS");
                    break;
                case "PET":
                    displayTable("PET");
                    break;
                case "PET_MULTIVALUE":
                    displayTable("PET_MULTIVALUE");
                    break;
                case "SALON":
                    displayTable("SALON");
                    break;
                case "STAFF":
                    displayTable("STAFF");
                    break;
            default:
                System.out.println("Invalid selection");
        }
    }

    /**
     * Overloaded search method for displaying a table without a menu selection.
     * @param table User selected table
     */
    private void search(String table) throws SQLException {
        //if selection was invalid, return
        if (table == null) return;

        //Display user selected table
        displayTable(table);
    }

    /* Helper Methods */

    /**
     * This private helper method allows a user to select a table and returns the selected table option.
     * @return User selected table
     */
    private String selectTable() {
        Scanner sc = new Scanner(System.in);

        //Display table options
        System.out.println("Which table would you like to select?: ");
        System.out.println("1. Customer");
        System.out.println("2. Employee");
        System.out.println("3. Employs");
        System.out.println("4. Groomer");
        System.out.println("5. Grooms");
        System.out.println("6. Helps");
        System.out.println("7. Pet");
        System.out.println("8. Pet Multivalue");
        System.out.println("9. Salon");
        System.out.println("10. Staff");

        //User selects table
        System.out.print("Selection: ");
        String table = sc.nextLine();
        System.out.println();

        //Convert number selection to table name and return5
        return switch (table) {
            case "1" -> "CUSTOMER";
            case "2" -> "EMPLOYEE";
            case "3" -> "EMPLOYS";
            case "4" -> "GROOMER";
            case "5" -> "GROOMS";
            case "6" -> "HELPS";
            case "7" -> "PET";
            case "8" -> "PET_MULTIVALUE";
            case "9" -> "SALON";
            case "10" -> "STAFF";
            default -> {
                System.out.println("Invalid selection");
                yield null;
            }
        };
    }

    /**
     * This private helper method displays a selected table based on user input.
     * @param table user selected table name
     */
    private void displayTable(String table) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;

        //Selection query
        String query = "SELECT * FROM " + table;

        //Execute query and store results
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            //gather column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            /* Table Formatting code */

                //determine the maximum width for each column + row number
                int[] columnWidths = new int[columnCount + 1];
                columnWidths[0] = "Row".length(); //Width of row number header
                for (int i = 1; i <= columnCount; i++) {
                    columnWidths[i] = rsmd.getColumnName(i).length();
                }

                //check each row to see if any value is longer than the column name
                int rowNumber = 1;
                while (rs.next()) {

                    //Check row number length
                    if (String.valueOf(rowNumber).length() > columnWidths[0]) {
                        columnWidths[0] = String.valueOf(rowNumber).length();
                    }

                    for (int i = 1; i <= columnCount; i++) {
                        if (rs.getString(i) == null) continue;

                        int valueLength = rs.getString(i).length();
                        if (valueLength > columnWidths[i]) {
                            columnWidths[i] = valueLength;
                        }
                    }
                    rowNumber++;
                }
            /* End of formatting block */


            //re-execute the query to reset the ResultSet for printing
            rs = stmt.executeQuery(query);

            //print the column headers with proper width
            System.out.printf("%-" + (columnWidths[0] + 2) + "s", "Row"); //Row header
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-" + (columnWidths[i] + 4) + "s", rsmd.getColumnName(i));
            }
            System.out.println();

            //print each row of data with the same formatting
            rowNumber = 1;
            while (rs.next()) {
                System.out.printf("%-" + (columnWidths[0] + 2) + "s", rowNumber); // row number
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-" + (columnWidths[i] + 4) + "s", rs.getString(i));
                }
                System.out.println();
                rowNumber++;
            }
        }
        //if error occurs, throw exception
        catch (Exception e) {
            System.out.println("Error occurred while displaying table: " + e.getMessage());
        }
        //Close open resources
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * This private helper method parses a table and stores a hash map of primary keys and their
     * corresponding "row number" assigned in the order that returned by the select SQL query.
     * @param table User selected table
     * @return hash map of primary keys, null if table selection invalid
     * @throws SQLException if problem with server connection occurs
     */
    private HashMap<Integer, String> getPrimaryKeys(String table) throws SQLException {
        //Declare new hash map
        HashMap<Integer, String> primaryKeys = new HashMap<>();

        //Select all rows from user selected table
        String query = "SELECT * FROM " + table;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            //Get data from table

            //First row is number 1, store primary keys and associate them with row number
            int rowNumber = 1;
            while (rs.next()) {
                primaryKeys.put(rowNumber, rs.getString(1));
                rowNumber++;
            }
        } catch (Exception e) {
            System.out.println("Error occurred while storing primary keys: " + e.getMessage());
        }

        //Return hash map
        return primaryKeys;
    }

    /**
     * This private helper method executes a SQL query that deletes a row from a
     * user selected table. This method uses the assumption that the first column
     * of each table uniquely identifies a given row.
     * @param table User selected table
     * @param primaryKey Value of data in the first column
     * @throws SQLException if error occurs while deleting a row
     */
    private void deleteQuery(String table, String primaryKey) throws SQLException {

        Statement stmt = null;

        try {
            //Set auto commit to false so invalid deletes don't go through
            conn.setAutoCommit(false);

            //Execute delete query
            stmt = conn.createStatement();
            String query = "DELETE FROM " + table + " WHERE " + getFirstColumnName(table) + " = '" + primaryKey + "'";
            int rowsDeleted = stmt.executeUpdate(query);

            //Test if a row was deleted
            if (rowsDeleted != 1) {
                System.out.println("No rows were deleted from table " + table);
            }
            //If row deleted, commit changes
            else {
                System.out.println("Row successfully deleted from " + table);
                conn.commit();
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while deleting row: " + e.getMessage());
        }
        //Close open resources
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * This private helper method retrieves the column name of the first column
     * in a user selected table.
     * @param table User selected table
     * @return column name, empty string if invalid
     * @throws SQLException if error occurs with server connection
     */
    private String getFirstColumnName(String table) throws SQLException {
        //Declare return variable
        String columnName = "";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {
            //Get data from table
            ResultSetMetaData rsmd = rs.getMetaData();

            //Store name of first column
            columnName = rsmd.getColumnName(1);
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving column name: " + e.getMessage());
        }

        //return column name
        return columnName;
    }

    /**
     * This private helper method retrieves the name of each column and stores it in a hash map
     * that tracks the number of columns in that table.
     * @param table User selected table
     * @return hash map of column number and name, empty hash map if invalid
     * @throws SQLException if error occurs with server connection
     */
    private HashMap<Integer, String> getColumnNames(String table) throws SQLException {
        //Initialize return hash map
        HashMap<Integer, String> columnNames = new HashMap<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {
            //Get data from table
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            //Store column names into hash map
            for (int i = 1; i <= columnCount; i++) {
                columnNames.put(i, rsmd.getColumnName(i));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving column names: " + e.getMessage());
        }

        //Return hash map
        return columnNames;
    }

    /**
     * This private helper method executes an SQL query that inserts a new row into a user
     * selected table. The user is prompted to input new values for created table. Then the query is displayed
     * to the user and is executed.
     * @param table User selected table
     * @param columns Hash map containing the names of each column
     * @throws SQLException if error occurs with server connection
     */
    private void addQuery(String table, HashMap<Integer, String> columns) throws SQLException {
        //initialize scanner and start building strings
        Scanner sc = new Scanner(System.in);
        StringBuilder columnNames = new StringBuilder();
        StringBuilder values = new StringBuilder();

        String columnName;
        String value;

        //iterate through each column and obtain user value
        for (int i = 1; i <= columns.size(); i++) {
            columnName = columns.get(i);
            System.out.print("Enter value for " + columnName + ": ");
            value = sc.nextLine();

            //append new value to string
            columnNames.append(columnName);
            values.append("'").append(value).append("'");

            //if not last column, append commas
            if (i < columns.size()) {
                columnNames.append(", ");
                values.append(", ");
            }
        }

        //Build SQL query and display to user
        String query = "INSERT INTO " + table + " (" + columnNames + ") VALUES (" + values + ")";
        System.out.println("Here is the query: " + query);

        Statement stmt = null;

        try {
            //Set auto commit to false so invalid inserts don't go through
            conn.setAutoCommit(false);

            //Execute SQL query and store number of rows inserted
            stmt = conn.createStatement();
            int rowsInserted = stmt.executeUpdate(query);

            //If no rows inserted, no change
            if (rowsInserted != 1) {
                System.out.println("No rows were added to table " + table);
            }
            //If row inserted, commit change
            else {
                System.out.println("Row successfully added to table " + table);
                conn.commit();
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while adding row: " + e.getMessage());
        }
        //Close open resources
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }

    }

    /**
     * This private helper method executes an SQL query that updates the values from a row in
     * a user selected Table. The method cycles through each column and the user can either type
     * a value or leave it blank. Then, the query is executed and displayed to the user.
     * @param table User selected table
     * @param primaryKey The value in the first column of the selected row.
     * @param columns Hash map containing the column names
     * @throws SQLException if error occurs with server connection
     */
    private void updateQuery(String table, String primaryKey, HashMap<Integer, String> columns) throws SQLException {
        //Initialize scanner and updated values string builder
        Scanner sc = new Scanner(System.in);
        StringBuilder setValues = new StringBuilder();

        //Strings for value updates and a check for first update
        String columnName;
        String value;
        boolean firstUpdate = true;

        //User has the option to enter new value or continues
        System.out.println("Update value or press Enter\n");

        //Cycle through each attribute and allow for user to update
        for (int i = 1; i <= columns.size(); i++) {
            //Obtain user input
            columnName = columns.get(i);
            System.out.print("Update value for " + columnName + "?: ");
            value = sc.nextLine();

            //If user chooses to update, append to string builder
            if (!value.isEmpty()) {
                //Only append commas if it is not the first update
                if (!firstUpdate) {
                    setValues.append(", ");
                }
                setValues.append(columnName).append(" = '").append(value).append("'");
                firstUpdate = false;
            }
        }

        //If user doesn't input anything, do not execute a query
        if (setValues.isEmpty()) {
            System.out.println("\nNothing to update");
            return;
        }

        //Build SQL query and display to user
        String query = "UPDATE " + table + " SET " + setValues + " WHERE " + columns.get(1) + " = '" + primaryKey + "'";
        System.out.println("\nHere is the query: " + query);

        Statement stmt = null;

        try {
            //Set auto commit to false so invalid update doesn't go through
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            //Execute the query and store number of rows updated
            int rowsUpdated = stmt.executeUpdate(query);

            //If no rows updated, error occurred, else commit changes
            if (rowsUpdated != 1) {
                System.out.println("No rows were updated from table " + table);
            }
            else {
                System.out.println("\nRow successfully updated from table " + table);
                conn.commit();
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while updating row: " + e.getMessage());
        }
        //Close open resources
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
