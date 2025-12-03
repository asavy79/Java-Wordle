package Wordle;

/**
 * Observer Pattern: Interface for observers that need to be notified of game state changes
 */
public interface GameObserver {
    void onGameStateChanged(WordleGame game);
    void onGuessMade(WordleGame game, String guess);
    void onGameReset(WordleGame game);
}

