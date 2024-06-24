package application;

import java.io.IOException;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Puzzle {

    public interface PuzzleEventListener {
        void onFirstButtonClick();
    }

    private PuzzleEventListener listener;
    private int size;
    private Button[][] buttons;
    private int[][] puzzle;

    private int emptyRow, emptyCol;
    private boolean firstClick;

    private GridPane gridPane;

    public Puzzle() {
        gridPane = new GridPane();
        firstClick = false;
    }

    public void setPuzzleEventListener(PuzzleEventListener listener) {
        this.listener = listener;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void initialize(int size) {
        this.size = size;
        buttons = new Button[size][size];
        puzzle = new int[size][size];

        Image image = new Image(getClass().getResourceAsStream("/application/shreksy.jpg"));

        double width = image.getWidth() / size;
        double height = image.getHeight() / size;

        initializePuzzle();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
               
                button.setStyle("-fx-background-color: #095859;");

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(button.getPrefWidth());
                imageView.setFitHeight(button.getPrefHeight());
                Rectangle2D viewport = new Rectangle2D(j * width, i * height, width, height);
                imageView.setViewport(viewport);

                StackPane stack = new StackPane();
                stack.getChildren().addAll(imageView, createNumberLabel(puzzle[i][j]));
                
                button.setGraphic(stack);
                
                if(puzzle[i][j] == 0) {
                	button.setGraphic(null);
                }

                final int row = i;
                final int col = j;
                button.setOnAction(event -> handleButtonClick(row, col));

                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        shufflePuzzle();
    }

    private void handleButtonClick(int row, int col) {
        if (!firstClick) {
            if (listener != null) {
                listener.onFirstButtonClick();
            }
            firstClick = true;
        }

        if ((row == emptyRow && Math.abs(col - emptyCol) == 1) || (col == emptyCol && Math.abs(row - emptyRow) == 1)) {
            swapTiles(emptyRow, emptyCol, row, col);
            updateButtonGraphics(emptyRow, emptyCol, row, col);
            emptyRow = row;
            emptyCol = col;
        }
        if (isSolved()) {
            showAlert();
        }
    }

    public void showAlert() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.75));  
        pause.setOnFinished(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("solved.fxml"));
                Stage stage = (Stage) gridPane.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    private void initializePuzzle() {
        int count = 1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                puzzle[row][col] = count;
                count++;
            }
        }
        emptyRow = size - 1;
        emptyCol = size - 1;
        puzzle[emptyRow][emptyCol] = 0;
    }

    private void shufflePuzzle() {
        Random rand = new Random();
        for (int i = 0; i < size * size * size; i++) {
            int direction = rand.nextInt(4);
            int newRow = emptyRow;
            int newCol = emptyCol;
            if (direction == 0 && emptyRow > 0) {
                newRow = emptyRow - 1;
            } else if (direction == 1 && emptyRow < size - 1) {
                newRow = emptyRow + 1;
            } else if (direction == 2 && emptyCol > 0) {
                newCol = emptyCol - 1;
            } else if (direction == 3 && emptyCol < size - 1) {
                newCol = emptyCol + 1;
            }
            swapTiles(emptyRow, emptyCol, newRow, newCol);
            updateButtonGraphics(emptyRow, emptyCol, newRow, newCol);
            emptyRow = newRow;
            emptyCol = newCol;
        }
    }

    private void updateButtonGraphics(int oldRow, int oldCol, int newRow, int newCol) {

        String tempText = buttons[oldRow][oldCol].getText();
        buttons[oldRow][oldCol].setText(buttons[newRow][newCol].getText());
        buttons[newRow][newCol].setText(tempText);

       
        Node tempGraphic = buttons[oldRow][oldCol].getGraphic();
        buttons[oldRow][oldCol].setGraphic(buttons[newRow][newCol].getGraphic());
        buttons[newRow][newCol].setGraphic(tempGraphic);
    }




    private void swapTiles(int r1, int c1, int r2, int c2) {
        int temp = puzzle[r1][c1];
        puzzle[r1][c1] = puzzle[r2][c2];
        puzzle[r2][c2] = temp;
    }

    private boolean isSolved() {
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzle[i][j] != count % (size * size)) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    private Label createNumberLabel(int number) {
        Label label = new Label(number == 0 ? "" : Integer.toString(number));
        label.setFont(Font.font("Arial", 20));
        label.setTextFill(Color.WHITE);
        return label;
    }
}
