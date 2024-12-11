package StudentTerm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:/Users/milesmcgranahan/StudentTerm/StudentTerm/src/main/Database/StudentTermDB.db";
    private static Connection connection = null;

    // Private constructor to prevent instantiation of the class
    private DatabaseManager() {}

    /**
     * Get the shared database connection. Creates a new one if it doesn't exist or is closed.
     *
     * @return Connection object
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Log connection establishment (optional)
            System.out.println("Establishing new database connection...");
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    /**
     * Close the shared database connection. Call this at application shutdown.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}