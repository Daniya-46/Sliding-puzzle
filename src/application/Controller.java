package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private RadioButton opt2;
    @FXML
    private RadioButton opt3;
    @FXML
    private RadioButton opt4;
    private int selectedSize;

    @FXML
    private Label timerLabel;

    @FXML
    public void switchToPuzzle(ActionEvent e) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("puzzle.fxml"));
        root = loader.load();

        PuzzleController puzzleController = loader.getController();
        Puzzle puzzle = new Puzzle();
        puzzleController.initializePuzzleGrid(puzzle);
        puzzle.initialize(selectedSize);

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showOptions(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("options.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void optionSelected(ActionEvent e) throws IOException {
        if (opt2.isSelected()) {
            selectedSize = 2;
        } else if (opt3.isSelected()) {
            selectedSize = 3;
        } else if (opt4.isSelected()) {
            selectedSize = 4;
        }
        switchToPuzzle(e);
    }
    
    @FXML
    public void exitApp() {
        Platform.exit();
    }
}
