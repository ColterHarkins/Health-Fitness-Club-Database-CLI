import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Class: DatabaseConnection
Purpose: Offload the job of connecting to a database from the other DAOs
 */
public class DatabaseConnection {
    private static String URL = "jdbc:postgresql://localhost:5432/HF_club";
    private static String USER = "postgres";
    private static String PASSWORD = "Mario797";

    /*
    Method: getConnection
    Purpose: Establish a connection with the postgreSQL server
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database");
        }
    }
}