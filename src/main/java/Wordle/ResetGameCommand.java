package Wordle;

/**
 * Command Pattern: Command to reset the game
 */
public class ResetGameCommand implements GameCommand {
    private final WordleGame game;
    
    public ResetGameCommand(WordleGame game) {
        this.game = game;
    }
    
    @Override
    public void execute() {
        game.resetGame();
    }
    
    @Override
    public void undo() {
        // Reset cannot be undone
    }
}

