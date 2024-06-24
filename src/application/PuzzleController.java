package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class PuzzleController {

    @FXML
    private GridPane gridPane;
    @FXML
    private Label timerLabel;

    private Timeline timeline;
    private int timeSeconds;

    public void initializePuzzleGrid(Puzzle puzzle) {
        gridPane.getChildren().clear();
        gridPane.add(puzzle.getGridPane(), 0, 0);

        puzzle.setPuzzleEventListener(() -> {
            startStopwatch();
        });
    }

    private void startStopwatch() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 0;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds++;
            updateTimerLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerLabel() {
        if (timerLabel != null) {
            timerLabel.setText(timeSeconds + " seconds");
        }
    }

    @FXML
    public void exitApp() {
    	Controller c1 = new Controller();
    	c1.exitApp();
    }
    
    @FXML
    public void showOptions(ActionEvent e) throws IOException {
    	Controller c1 = new Controller();
    	c1.showOptions(e);
    }
    
}
