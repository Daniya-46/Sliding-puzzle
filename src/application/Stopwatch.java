package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Stopwatch extends Application {

    private int timeSeconds = 0;
    private Label timerLabel;
    private Timeline timeline;
    private boolean running = false;

    @Override
    public void start(Stage primaryStage) {
        timerLabel = new Label();
        updateLabel();

        Button startButton = new Button("Start");
        startButton.setOnAction(event -> startTimer());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(event -> stopTimer());

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> resetTimer());

        HBox buttonBox = new HBox(10, startButton, stopButton, resetButton);
        VBox root = new VBox(10, timerLabel, buttonBox);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 150);

        primaryStage.setTitle("Stopwatch App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds++;
            updateLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        running = true;
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        running = false;
    }

    private void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 0;
        updateLabel();
        running = false;
    }

    private void updateLabel() {
        timerLabel.setText("Time: " + timeSeconds + " seconds");
    }


}
