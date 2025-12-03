package Wordle;

/**
 * Command Pattern: Interface for game commands
 * Encapsulates game actions as objects
 */
public interface GameCommand {
    void execute();
    void undo();
}

