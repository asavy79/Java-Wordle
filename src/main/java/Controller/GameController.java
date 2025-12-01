package Controller;


import View.BoardDisplayer;
import Wordle.Board;
import Wordle.WordleGame;

public class GameController {

    public WordleGame game;
    public BoardDisplayer view;

    public GameController(WordleGame game, BoardDisplayer view) {
        this.game = game;
        this.view = view;
    }

    public void resetGame() {
        game.resetGame();
    }

    public Boolean playTurn(String input) {
        return game.playTurn(input);
    }

    public Board getBoard() {
        return game.getBoard();
    }
}
