package Wordle;

import Wordle.LetterEvaluator.LetterEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Model: Represents the game board with all guesses
 * Uses Strategy pattern for letter evaluation
 */
public class Board {

    private final List<List<LetterGuess>> board;
    private final LetterEvaluator evaluator;

    public Board(LetterEvaluator evaluator) {
        this.board = new ArrayList<>();
        this.evaluator = evaluator;
    }

    public void addWord(String word, String targetWord) {
        this.board.add(createRow(word, targetWord));
    }

    private List<LetterGuess> createRow(String guessedWord, String targetWord) {
        List<LetterGuess> row = new ArrayList<>();

        for (int i = 0; i < guessedWord.length(); i++) {
            char guessedChar = guessedWord.charAt(i);
            LetterStatus status = evaluator.evaluateLetter(guessedChar, i, guessedWord, targetWord);
            row.add(new LetterGuess(guessedChar, status));
        }
        return row;
    }

    public void reset() {
        board.clear();
    }

    public List<List<LetterGuess>> getBoard() {
        return new ArrayList<>(board);
    }
}
