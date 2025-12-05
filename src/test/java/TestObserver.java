import Wordle.GameObserver;
import Wordle.State.GameState;
import Wordle.WordleGame;

import java.util.ArrayList;
import java.util.List;

public class TestObserver implements GameObserver {

    private final List<GameState> stateChanges;
    private final List<String> guesses;
    private final List<WordleGame> gamesFromGuesses;
    private int resetCount;

    public TestObserver() {
        this.stateChanges = new ArrayList<>();
        this.guesses = new ArrayList<>();
        this.gamesFromGuesses = new ArrayList<>();
        this.resetCount = 0;
    }

    @Override
    public void onGameStateChanged(GameState state) {
        stateChanges.add(state);
    }

    @Override
    public void onGuessMade(WordleGame game, String guess) {
        guesses.add(guess);
        gamesFromGuesses.add(game);
    }

    @Override
    public void onGameReset() {
        resetCount++;
    }


    public int getGuessCount() {
        return guesses.size();
    }


    public String getLastGuess() {
        return guesses.isEmpty() ? null : guesses.get(guesses.size() - 1);
    }

    public void clear() {
        stateChanges.clear();
        guesses.clear();
        gamesFromGuesses.clear();
        resetCount = 0;
    }
}