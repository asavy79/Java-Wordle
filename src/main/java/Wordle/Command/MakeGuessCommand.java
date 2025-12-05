package Wordle.Command;

import Wordle.WordleGame;

/**
 * Command Pattern: Command to make a guess in the game
 */
public class MakeGuessCommand implements GameCommand {
    private final WordleGame game;
    private final String guess;
    private boolean executed = false;
    
    public MakeGuessCommand(WordleGame game, String guess) {
        this.game = game;
        this.guess = guess;
    }
    
    @Override
    public void execute() {
        if (!executed && game.getState().canMakeGuess()) {
            game.getState().handleGuess(game, guess);
            executed = true;
        }
    }
    
    @Override
    public void undo() {
        // Players cannot undo in wordle. This is just included for consistency with the command pattern
    }
}

