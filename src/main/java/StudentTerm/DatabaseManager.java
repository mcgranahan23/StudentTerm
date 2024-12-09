package StudentTerm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:StudentTermData"; // Ensure this path is correct

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}