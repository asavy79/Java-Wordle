package Wordle;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy Pattern: Standard Wordle evaluation strategy
 */
public class StandardWordleEvaluator implements LetterEvaluator {
    
    @Override
    public LetterStatus evaluateLetter(char letter, int position, String guess, String target) {
        // First pass: mark exact matches
        if (guess.charAt(position) == target.charAt(position)) {
            return LetterStatus.CORRECT_POSITION;
        }
        
        // Count occurrences in target word
        Map<Character, Integer> targetCounts = countLetters(target);
        Map<Character, Integer> guessCounts = countLetters(guess);
        
        // Count how many times this letter appears in correct positions in guess
        int correctPositionCount = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == letter && guess.charAt(i) == target.charAt(i)) {
                correctPositionCount++;
            }
        }
        
        // Count how many times this letter appears in wrong positions in guess (up to this position)
        int wrongPositionCount = 0;
        for (int i = 0; i < position; i++) {
            if (guess.charAt(i) == letter && guess.charAt(i) != target.charAt(i)) {
                wrongPositionCount++;
            }
        }
        
        // Check if letter exists in target
        int targetOccurrences = targetCounts.getOrDefault(letter, 0);
        
        if (targetOccurrences == 0) {
            return LetterStatus.INCORRECT;
        }
        
        // Available yellow slots = total occurrences - correct positions - already used yellows
        int availableYellows = targetOccurrences - correctPositionCount;
        
        if (wrongPositionCount < availableYellows) {
            return LetterStatus.INCLUDED;
        } else {
            return LetterStatus.INCORRECT;
        }
    }
    
    private Map<Character, Integer> countLetters(String word) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : word.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        return counts;
    }
}

