package Wordle;

import Wordle.State.GameState;

/**
 * Observer Pattern: Interface for observers that need to be notified of game state changes
 */
public interface GameObserver {
    void onGameStateChanged(GameState game);
    void onGuessMade(WordleGame game, String guess);
    void onGameReset();
}

