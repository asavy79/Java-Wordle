package Wordle;

import java.util.List;
import java.util.Random;

/**
 * Factory Pattern: Factory for creating game components
 * Centralizes object creation logic
 */
public class GameComponentFactory {
    
    public static WordleGame createGame(List<String> wordBank, int maxGuesses) {
        Board board = createBoard();
        Random random = new Random();
        LetterEvaluator evaluator = createEvaluator();
        WordValidator validator = createValidator(5);
        
        return new WordleGame(board, maxGuesses, wordBank, random, evaluator, validator);
    }
    
    public static Board createBoard() {
        LetterEvaluator evaluator = createEvaluator();
        return new Board(evaluator);
    }
    
    public static LetterEvaluator createEvaluator() {
        return new StandardWordleEvaluator();
    }
    
    public static WordValidator createValidator(int wordLength) {
        return new StandardWordValidator(wordLength);
    }
}

