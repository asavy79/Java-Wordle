package Wordle;

/**
 * State Pattern: Player has lost the game (ran out of guesses)
 */
public class LostState implements GameState {
    private final String targetWord;
    
    public LostState(String targetWord) {
        this.targetWord = targetWord;
    }
    
    @Override
    public boolean canMakeGuess() {
        return false;
    }
    
    @Override
    public boolean isGameOver() {
        return true;
    }
    
    @Override
    public String getStateMessage() {
        return "Game Over! The word was: " + targetWord;
    }
    
    @Override
    public void handleGuess(WordleGame game, String guess) {
        // Game is over, cannot make more guesses
    }
}

