package Wordle;

/**
 * State Pattern: Interface for game states
 * Different states handle game behavior differently
 */
public interface GameState {
    boolean canMakeGuess();
    boolean isGameOver();
    String getStateMessage();
    void handleGuess(WordleGame game, String guess);
}

