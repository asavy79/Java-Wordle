package Wordle;

import View.BoardDisplayer;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<List<LetterGuess>> board;

    public Board() {
        this.board = new ArrayList<>();
    }

    public void addWord(String word, String targetWord) {
        this.board.add(createRow(word, targetWord));
    }

    private List<LetterGuess> createRow(String guessedWord, String targetWord) {
        List<LetterGuess> row = new ArrayList<>();

        for (int i = 0; i < guessedWord.length(); i++) {
            Character guessedChar = guessedWord.charAt(i);
            Character targetChar = targetWord.charAt(i);

            if (guessedChar == targetChar) {
                row.add(new LetterGuess(guessedChar, LetterStatus.CORRECT_POSITION));
            } else if (targetWord.contains(guessedChar.toString())) {
                row.add(new LetterGuess(guessedChar, LetterStatus.INCLUDED));
            } else {
                row.add(new LetterGuess(guessedChar, LetterStatus.INCORRECT));
            }
        }
        return row;
    }


    public void reset() {}

    public List<List<LetterGuess>> getBoard() {
        return board;
    }
}
