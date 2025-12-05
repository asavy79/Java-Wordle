package View;

import Wordle.Board;
import Wordle.LetterGuess;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class JavaFXDisplayer extends Application implements BoardDisplayer {

    private static Board currentBoard;
    private GridPane boardGrid;
    private Stage primaryStage;
    private static final int CELL_SIZE = 60;
    private static final int MAX_ROWS = 6;
    private static final int MAX_COLS = 5;

    public JavaFXDisplayer() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;


        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("WORDLE");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #333;");

        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);

        initializeEmptyBoard();

        root.getChildren().addAll(title, boardGrid);

        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Wordle Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        if (currentBoard != null) {
            updateBoardDisplay();
        }
    }

    private void initializeEmptyBoard() {
        boardGrid.getChildren().clear();

        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLS; col++) {
                Label cell = createEmptyCell();
                boardGrid.add(cell, col, row);
            }
        }
    }

    private Label createEmptyCell() {
        Label cell = new Label("");
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        cell.setAlignment(Pos.CENTER);
        cell.setStyle(
                "-fx-border-color: #d3d6da; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-color: white; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: black;");
        return cell;
    }

    private Label createLetterCell(LetterGuess letterGuess) {
        Label cell = new Label(letterGuess.getLetter().toString().toUpperCase());
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        cell.setAlignment(Pos.CENTER);

        String backgroundColor;
        String textColor = "white";

        switch (letterGuess.getStatus()) {
            case CORRECT_POSITION:
                backgroundColor = "#6aaa64"; // Green
                break;
            case INCLUDED:
                backgroundColor = "#c9b458"; // Yellow
                break;
            case INCORRECT:
                backgroundColor = "#787c7e"; // Gray
                break;
            default:
                backgroundColor = "white";
                textColor = "black";
        }

        cell.setStyle(
                "-fx-border-color: #d3d6da; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-color: " + backgroundColor + "; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + textColor + ";");

        return cell;
    }

    private void updateBoardDisplay() {
        if (boardGrid == null)
            return;

        Platform.runLater(() -> {
            boardGrid.getChildren().clear();

            List<List<LetterGuess>> board = currentBoard.getBoard();

            for (int row = 0; row < board.size() && row < MAX_ROWS; row++) {
                List<LetterGuess> currentRow = board.get(row);
                for (int col = 0; col < currentRow.size() && col < MAX_COLS; col++) {
                    Label cell = createLetterCell(currentRow.get(col));
                    boardGrid.add(cell, col, row);
                }

                for (int col = currentRow.size(); col < MAX_COLS; col++) {
                    Label cell = createEmptyCell();
                    boardGrid.add(cell, col, row);
                }
            }

            for (int row = board.size(); row < MAX_ROWS; row++) {
                for (int col = 0; col < MAX_COLS; col++) {
                    Label cell = createEmptyCell();
                    boardGrid.add(cell, col, row);
                }
            }
        });
    }

    @Override
    public void displayBoard(Board board) {
        currentBoard = board;

        if (primaryStage == null) {
            Platform.runLater(() -> {
                try {
                    Stage stage = new Stage();
                    start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            updateBoardDisplay();
        }
    }
}
