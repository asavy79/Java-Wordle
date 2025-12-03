package Wordle;

/**
 * Strategy Pattern: Interface for different letter evaluation strategies
 * Allows for different game modes or rule variations
 */
public interface LetterEvaluator {
    LetterStatus evaluateLetter(char letter, int position, String guess, String target);
}

