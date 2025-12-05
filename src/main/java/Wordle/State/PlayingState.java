package Wordle.State;

import Wordle.WordleGame;

/**
 * State Pattern: Game is in progress, player can make guesses
 */
public class PlayingState implements GameState {
    
    @Override
    public boolean canMakeGuess() {
        return true;
    }
    
    @Override
    public boolean isGameOver() {
        return false;
    }
    
    @Override
    public String getStateMessage() {
        return "Make your guess!";
    }
    
    @Override
    public void handleGuess(WordleGame game, String guess) {
        boolean won = game.processGuess(guess);
        if (won) {
            game.setState(new WonState());
        } else if (game.getGuessCount() >= game.getMaxGuesses()) {
            game.setState(new LostState(game.getTargetWord()));
        }
    }
}

