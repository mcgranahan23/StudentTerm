package StudentTerm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            initializeDatabase();

            Parent root = FXMLLoader.load(getClass().getResource("StudentTerm.fxml"));
            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Student Term Management");
            primaryStage.show();

        } catch (IOException e) {
            handleException("Failed to load FXML", e);
        } catch (Exception e) {
            handleException("Error during application startup", e);
        }
    }

    private void initializeDatabase() {
        DataOperations dataOperations = new DataOperations();

        // Example SQL query to insert a new record
        String sql = "INSERT INTO Students (name, address, city, state, zip) VALUES (?, ?, ?, ?, ?)";

        dataOperations.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, "John Doe");            // Setting parameter index 1
            pstmt.setString(2, "1234 Elm Street");     // Setting parameter index 2
            pstmt.setString(3, "Springfield");         // Setting parameter index 3
            pstmt.setString(4, "IL");                  // Setting parameter index 4
            pstmt.setInt(5, 62704);                    // Setting parameter index 5
        });

        System.out.println("Database initialized successfully.");
    }

    private void handleException(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }

    public static void main(String[] args) {
        resetDatabase();
        launch(args);
    }

    private static void resetDatabase() {
        DataOperations dataOperations = new DataOperations();
        dataOperations.resetDatabase();
        System.out.println("Database reset successfully.");
    }

    @Override
    public void stop() {
        // Close the shared database connection on application shutdown
        DatabaseManager.closeConnection();
        System.out.println("Application stopped.");
    }
}