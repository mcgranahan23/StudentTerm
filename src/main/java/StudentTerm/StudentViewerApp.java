package StudentTerm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class StudentViewerApp extends Application {

    private TableView<Student> tableView;
    private DataOperations dataOperations;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setupStage(primaryStage);
        initializeData();
        setupTable();
        loadDataFromDatabase();
    }

    private void setupStage(Stage primaryStage) {
        tableView = new TableView<>();
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox);

        primaryStage.setTitle("Student Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeData() {
        dataOperations = new DataOperations();

        // Populate students
        dataOperations.populateInitialData();

        // Populate generic example terms (Spring 2024, Summer 2024, Fall 2024)
        List<Term> terms = dataOperations.populateGenericTerms();

        // Get all students from the database
        List<Student> students = dataOperations.getAllStudents();

        // Link students to terms (example rule: alternate assignments, or other logic)
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            Term term = terms.get(i % terms.size()); // Alternate between the available terms
            dataOperations.linkStudentToTerm(student.getId(), term.getId());
        }
    }

    private void setupTable() {
        addTableColumn("ID", "id");
        addTableColumn("Name", "name");
        addTableColumn("Address", "address");
        addTableColumn("City", "city");
        addTableColumn("State", "state");
        addTableColumn("Zip", "zip");

        tableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    // Fetch and show term details for the selected student
                    showTermDetails(row.getItem().getId());
                }
            });
            return row;
        });
    }

    private <T> void addTableColumn(String title, String property) {
        TableColumn<Student, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        tableView.getColumns().add(column);
    }

    private void showTermDetails(int studentId) {
        // Fetch the term linked to the student using DataOperations
        Term term = dataOperations.getTermByStudentId(studentId);

        if (term != null) {
            // Show term details
            showAlert(Alert.AlertType.INFORMATION, "Term Details",
                    "Linked Term for the Selected Student",
                    "Term Name: " + term.getName() +
                            "\nStart Date: " + term.getStart() +
                            "\nDates: " + term.getDates() +
                            "\nDescription: " + term.getDescription() +
                            "\nCharge Amount: $" + term.getChargeAmount() +
                            "\nPayment: $" + term.getPayment());
        } else {
            // Show warning if no term is linked
            showAlert(Alert.AlertType.WARNING, "No Term Found", null,
                    "The selected student does not have a linked term.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadDataFromDatabase() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM Students")) {

            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getInt("zip")
                );
                tableView.getItems().add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // Close the shared database connection on application shutdown
        DatabaseManager.closeConnection();
        System.out.println("Application stopped.");
    }
}