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
            DataOperations dataOperations = new DataOperations();
            dataOperations.initializeDatabase();
            System.out.println("Database initialized successfully.");

            // Load the FXML file inside a try-catch block
            Parent root = FXMLLoader.load(getClass().getResource("StudentTerm.fxml"));
            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Student Term Management");
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + e.getMessage());
            e.printStackTrace();
            // Handle or recover from the exception
        } catch (Exception e) {
            System.err.println("Error during application startup: " + e.getMessage());
            e.printStackTrace();
            // Handle or recover from the exception
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}