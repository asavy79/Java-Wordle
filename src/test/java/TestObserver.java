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

    public List<GameState> getStateChanges() {
        return new ArrayList<>(stateChanges);
    }

    public List<String> getGuesses() {
        return new ArrayList<>(guesses);
    }

    public List<WordleGame> getGamesFromGuesses() {
        return new ArrayList<>(gamesFromGuesses);
    }

    public int getResetCount() {
        return resetCount;
    }

    public int getStateChangeCount() {
        return stateChanges.size();
    }

    public int getGuessCount() {
        return guesses.size();
    }

    public GameState getLastStateChange() {
        return stateChanges.isEmpty() ? null : stateChanges.get(stateChanges.size() - 1);
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