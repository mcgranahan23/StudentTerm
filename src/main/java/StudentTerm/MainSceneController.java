package StudentTerm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainSceneController {

    @FXML
    private Button addStudent; // Corresponds to fx:id="addStudent"

    @FXML
    private Button addTerm; // Corresponds to fx:id="addTerm"

    @FXML
    private Button printAll; // Corresponds to fx:id="printAll"

    @FXML
    private void handleAddStudent() {
        addStudent.setOnAction(actionEvent -> Student.newStudent());
    }

    @FXML
    private void handleAddTerm() {
        addTerm.setOnAction(actionEvent -> Term.newTerm());
        Term.newTerm();
    }

    @FXML
    private void handlePrintAll() {
        printAll.setOnAction(actionEvent -> StudentWithTerm.printAll());
        StudentWithTerm.printAll();
    }
}