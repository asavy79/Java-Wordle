package Wordle;

/**
 * State Pattern: Player has won the game
 */
public class WonState implements GameState {
    
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
        return "Congratulations! You won!";
    }
    
    @Override
    public void handleGuess(WordleGame game, String guess) {
        // Game is over, cannot make more guesses
    }
}

