package Wordle.WordEvaluator;

/**
 * Strategy Pattern: Interface for word validation strategies
 */
public interface WordValidator {
    boolean isValid(String word);
    String getValidationMessage(String word);
}

