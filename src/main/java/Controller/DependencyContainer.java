package Controller;

import View.BoardDisplayer;
import Wordle.*;

import java.util.List;

/**
 * Dependency Injection: Simple dependency injection container
 * Manages creation and injection of dependencies
 */
public class DependencyContainer {
    
    private final WordleGame game;
    private final BoardDisplayer view;
    private final GameController controller;
    
    public DependencyContainer(List<String> wordBank, int maxGuesses, BoardDisplayer view) {
        this.game = GameComponentFactory.createGame(wordBank, maxGuesses);
        this.view = view;

        this.controller = new GameController(game, view);

        if (view instanceof GameObserver) {
            game.addObserver((GameObserver) view);
        }
    }
    
    public WordleGame getGame() {
        return game;
    }
    
    public BoardDisplayer getView() {
        return view;
    }
    
    public GameController getController() {
        return controller;
    }
}

