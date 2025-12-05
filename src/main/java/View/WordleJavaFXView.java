package View;

import Controller.GameController;
import Wordle.*;
import Wordle.State.GameState;
import Wordle.State.LostState;
import Wordle.State.WonState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * View: Complete JavaFX UI for Wordle game
 * Implements Observer pattern to receive game state updates
 */
public class WordleJavaFXView extends Application implements BoardDisplayer, GameObserver {
    
    private static GameController controller;
    private static WordleJavaFXView instance;
    
    private GridPane boardGrid;
    private Stage primaryStage;
    private Label statusLabel;
    private Label currentGuessLabel;
    private Button resetButton;
    private String currentGuess = "";
    private static final int CELL_SIZE = 60;
    private static final int MAX_ROWS = 6;
    private static final int MAX_COLS = 5;
    
    public WordleJavaFXView() {
        instance = this;
    }
    
    public static void setController(GameController ctrl) {
        controller = ctrl;
    }
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        org.wordle_project.Main.initializeGame(this);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("WORDLE");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #333;");

        statusLabel = new Label("Enter your guess!");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        currentGuessLabel = new Label("");
        currentGuessLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        currentGuessLabel.setPrefHeight(30);

        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);

        initializeEmptyBoard();

        resetButton = new Button("New Game");
        resetButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 16; -fx-background-color: #6aaa64; -fx-text-fill: white;");
        resetButton.setOnAction(e -> {
            if (controller != null) {
                controller.resetGame();
                currentGuess = "";
                updateCurrentGuessDisplay();
            }
        });
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(resetButton);
        
        root.getChildren().addAll(title, statusLabel, currentGuessLabel, boardGrid, buttonBox);

        root.setFocusTraversable(true);
        root.requestFocus();
        root.setOnKeyPressed(this::handleKeyPress);
        
        Scene scene = new Scene(root, 450, 600);
        stage.setTitle("Wordle Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        root.requestFocus();
    }
    
    private void handleKeyPress(KeyEvent event) {
        if (controller == null || controller.getGame().getState().isGameOver()) {
            return;
        }
        
        KeyCode code = event.getCode();
        
        if (code.isLetterKey()) {
            if (currentGuess.length() < MAX_COLS) {
                currentGuess += code.toString().toUpperCase();
                updateCurrentGuessDisplay();
            }
        } else if (code == KeyCode.BACK_SPACE || code == KeyCode.DELETE) {
            if (currentGuess.length() > 0) {
                currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
                updateCurrentGuessDisplay();
            }
        } else if (code == KeyCode.ENTER) {
            if (currentGuess.length() == MAX_COLS) {
                String validationError = controller.validateGuess(currentGuess);
                if (validationError != null) {
                    statusLabel.setText(validationError);
                    statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #d32f2f;");
                    currentGuess = "";
                    updateCurrentGuessDisplay();
                } else {
                    boolean won = controller.makeGuess(currentGuess);
                    currentGuess = "";
                    updateCurrentGuessDisplay();

                    Platform.runLater(() -> {
                        updateBoardDisplay();
                    });
                    
                    if (won) {
                        statusLabel.setText("Congratulations! You won!");
                        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6aaa64; -fx-font-weight: bold;");
                    }
                }
            } else {
                statusLabel.setText("Word must be 5 letters!");
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #d32f2f;");
            }
        }
    }
    
    private void updateCurrentGuessDisplay() {
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < MAX_COLS; i++) {
            if (i < currentGuess.length()) {
                display.append(currentGuess.charAt(i));
            } else {
                display.append("_");
            }
            if (i < MAX_COLS - 1) {
                display.append(" ");
            }
        }
        currentGuessLabel.setText(display.toString());
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
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: black;");
        return cell;
    }
    
    private Label createLetterCell(LetterGuess letterGuess) {
        Label cell = new Label(String.valueOf(letterGuess.getLetter()).toUpperCase());
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        cell.setAlignment(Pos.CENTER);

        LetterStatus status = letterGuess.getStatus();
        String backgroundColor = status.getColorCode();
        String textColor = status.getTextColor();
        
        cell.setStyle(
                "-fx-border-color: #d3d6da; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-color: " + backgroundColor + "; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + textColor + ";");
        
        return cell;
    }
    
    private void updateBoardDisplay() {
        if (boardGrid == null || controller == null) {
            return;
        }

        if (Platform.isFxApplicationThread()) {
            updateBoardDisplayInternal();
        } else {
            Platform.runLater(this::updateBoardDisplayInternal);
        }
    }
    
    private void updateBoardDisplayInternal() {
        if (boardGrid == null || controller == null) {
            return;
        }
        
        boardGrid.getChildren().clear();
        
        Board board = controller.getGame().getBoard();
        List<List<LetterGuess>> boardData = board.getBoard();

        for (int row = 0; row < boardData.size() && row < MAX_ROWS; row++) {
            List<LetterGuess> currentRow = boardData.get(row);
            for (int col = 0; col < currentRow.size() && col < MAX_COLS; col++) {
                Label cell = createLetterCell(currentRow.get(col));
                boardGrid.add(cell, col, row);
            }

            for (int col = currentRow.size(); col < MAX_COLS; col++) {
                Label cell = createEmptyCell();
                boardGrid.add(cell, col, row);
            }
        }

        for (int row = boardData.size(); row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLS; col++) {
                Label cell = createEmptyCell();
                boardGrid.add(cell, col, row);
            }
        }
    }
    
    @Override
    public void displayBoard(Board board) {
        updateBoardDisplay();
    }

    @Override
    public void onGameStateChanged(GameState state) {
        Platform.runLater(() -> {
            statusLabel.setText(state.getStateMessage());
            
            if (state instanceof WonState) {
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6aaa64; -fx-font-weight: bold;");
            } else if (state instanceof LostState) {
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #d32f2f; -fx-font-weight: bold;");
            } else {
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            }
            
            updateBoardDisplay();
        });
    }
    
    @Override
    public void onGuessMade(WordleGame game, String guess) {
        Platform.runLater(() -> {
            // Guess was already validated before being processed
            // Show remaining guesses
            int remaining = game.getMaxGuesses() - game.getGuessCount();
            if (remaining > 0) {
                statusLabel.setText("Guesses remaining: " + remaining);
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            }
            updateBoardDisplay();
        });
    }
    
    @Override
    public void onGameReset() {
        Platform.runLater(() -> {
            currentGuess = "";
            updateCurrentGuessDisplay();
            statusLabel.setText("Enter your guess!");
            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            initializeEmptyBoard();
        });
    }
    
    public static WordleJavaFXView getInstance() {
        return instance;
    }
}

