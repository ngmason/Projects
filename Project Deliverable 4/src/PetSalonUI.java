import java.sql.*;
import java.util.Scanner;

/**
 * This class stores the text based menu for the Pet Salon database. This application
 * connects to a SQL Server and allows the user to modify entries.
 *
 * @version 1.0
 * @author Weronika Golden, Anne Himes, Nina Mason, Andrew Tellez
 */
public class PetSalonUI {


    public static void main(String[] args) throws Exception {

        //database server credentials
        String url = args[0];
        String user = args[1];
        String pass = args[2];
        Class.forName(args[3]);

        //make initial connection to server
        boolean connected = connectToServer(url, user, pass);

        //test connection, exit application if connection failed
        if (!connected) {
            throw new Exception("Failed to connect to server");
        }
        else {
            System.out.println("Connected to server");
        }

        //if server connection successful, create new Connection and pass
        //user to text based menu
        Connection conn = DriverManager.getConnection(url, user, pass);
        displayMenu(conn);

        //once user is done accessing database, close open resources
        if (conn != null) { conn.close(); }

    }

    /**
     * This method tests to see if the user is able to successfully connect to a SQL Server. Returns true or false.
     * @param url address of server
     * @param user username
     * @param pass password
     * @return true if connected, false if failed to connect
     * @throws SQLException exception thrown if connection failed
     */
    public static boolean connectToServer(String url, String user, String pass) throws SQLException {

        //result variable
        boolean status = false;

        //initialize a connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            status = true; //if connected, set status to true
        }
        catch (SQLException e) { //if not connected, throw exception
            System.out.println("Problem occurred while connecting to server");
            System.out.println(e);
        }
        finally {
            if (conn != null) { conn.close();} //close open resources
        }
        return status; //return false if failed to connect
    }

    /**
     * This method stores the primary menu interface for accessing the Pet Salon database.
     * Users make a selection and are directed to an instance of PetSalonLogic for
     * database query.
     * @param conn variable that links to sql database
     */
    public static void displayMenu(Connection conn) throws SQLException {

        //declare variables for menu selection
        String selection;
        Scanner sc = new Scanner(System.in);
        PetSalonLogic db = new PetSalonLogic(conn);

        System.out.println("\nWelcome to the Pet Salon Database!");
        System.out.println("----------------------------------");

        while (true) {
            System.out.println("\nPlease select from the following menu:");
            System.out.println("1. Add new entry to the database");
            System.out.println("2. Delete entry from the database");
            System.out.println("3. Update entry from the database");
            System.out.println("4. Search");
            System.out.println("5. Exit\n");

            System.out.print("Selection: ");
            selection = sc.nextLine();
            System.out.println();

            switch (selection) {
                case "1":
                    db.add();
                    break;
                case "2":
                    db.delete();
                    break;
                case "3":
                    db.update();
                    break;
                case "4":
                    db.search();
                    break;
                case "5":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
    }
}
