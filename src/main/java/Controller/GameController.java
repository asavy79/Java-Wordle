package Controller;

import View.BoardDisplayer;
import Wordle.*;
import Wordle.Command.GameCommand;
import Wordle.Command.MakeGuessCommand;
import Wordle.Command.ResetGameCommand;
import Wordle.State.WonState;

/**
 * Controller: Mediates between Model and View
 * Uses Command pattern for game actions
 */
public class GameController {

    private final WordleGame game;
    private final BoardDisplayer view;

    public GameController(WordleGame game, BoardDisplayer view) {
        this.game = game;
        this.view = view;
    }

    public void resetGame() {
        GameCommand resetCommand = new ResetGameCommand(game);
        resetCommand.execute();
        updateView();
    }

    public boolean makeGuess(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        
        String upperInput = input.toUpperCase().trim();
        
        if (!game.getValidator().isValid(upperInput)) {
            return false;
        }
        
        GameCommand guessCommand = new MakeGuessCommand(game, upperInput);
        guessCommand.execute();
        updateView();
        
        return game.getState() instanceof WonState;
    }
    
    public String validateGuess(String input) {
        // Will return null if the guess is valid
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a word";
        }
        String upperInput = input.toUpperCase().trim();
        if (!game.getValidator().isValid(upperInput)) {
            return game.getValidator().getValidationMessage(upperInput);
        }
        return null;
    }

    public void updateView() {
        view.displayBoard(game.getBoard());
    }

    public WordleGame getGame() {
        return game;
    }

    public BoardDisplayer getView() {
        return view;
    }
}
