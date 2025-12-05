package Wordle.WordEvaluator;

/**
 * Strategy Pattern: Standard word validator that checks word length
 */
public class StandardWordValidator implements WordValidator {
    private final int wordLength;
    
    public StandardWordValidator(int wordLength) {
        this.wordLength = wordLength;
    }
    
    @Override
    public boolean isValid(String word) {
        if (word == null) {
            return false;
        }
        String upperWord = word.toUpperCase().trim();

        if (upperWord.length() != wordLength) {
            return false;
        }

        return upperWord.matches("[A-Z]+");
    }
    
    @Override
    public String getValidationMessage(String word) {
        if (word == null || word.trim().isEmpty()) {
            return "Please enter a word";
        }
        String upperWord = word.toUpperCase().trim();
        if (upperWord.length() != wordLength) {
            return "Word must be exactly " + wordLength + " letters";
        }
        if (!upperWord.matches("[A-Z]+")) {
            return "Word must contain only letters";
        }
        return "Valid word";
    }
}

