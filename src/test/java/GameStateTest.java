import Wordle.WordleGame;
import Wordle.State.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    private WordleGame game;
    private TestObserver testObserver;
    private List<String> testWordBank;

    @BeforeEach
    void setUp() {
        testWordBank = Arrays.asList("HELLO", "WORLD", "TESTS", "GAMES", "WORDS");

        game = new WordleGame.GameBuilder()
                .setWordBank(testWordBank)
                .setRandom(new Random(42))
                .setMaxGuesses(6)
                .build();

        testObserver = new TestObserver();
        game.addObserver(testObserver);
    }

    @Test
    void testInitialPlayingState() {
        GameState initialState = game.getState();

        assertInstanceOf(PlayingState.class, initialState);
        assertTrue(initialState.canMakeGuess());
        assertFalse(initialState.isGameOver());
        assertEquals("Make your guess!", initialState.getStateMessage());
    }

    @Test
    void testPlayingStateTransitionToWon() {
        String targetWord = game.getTargetWord();
        GameState initialState = game.getState();

        assertInstanceOf(PlayingState.class, initialState);

        game.processGuess(targetWord);

        GameState finalState = game.getState();
        assertInstanceOf(WonState.class, finalState);
        assertFalse(finalState.canMakeGuess());
        assertTrue(finalState.isGameOver());
        assertEquals("Congratulations! You won!", finalState.getStateMessage());

    }

    @Test
    void testPlayingStateTransitionToLost() {
        String targetWord = game.getTargetWord();
        GameState initialState = game.getState();

        assertInstanceOf(PlayingState.class, initialState);

        for (int i = 0; i < game.getMaxGuesses(); i++) {
            String wrongGuess = generateWrongGuess(targetWord, i);
            game.processGuess(wrongGuess);
        }

        GameState finalState = game.getState();
        assertInstanceOf(LostState.class, finalState);
        assertFalse(finalState.canMakeGuess());
        assertTrue(finalState.isGameOver());
        assertTrue(finalState.getStateMessage().contains("Game Over"));

    }

    private String generateWrongGuess(String targetWord, int index) {
        String[] wrongGuesses = { "AAAAA", "BBBBB", "CCCCC", "DDDDD", "EEEEE", "FFFFF", "GGGGG", "HHHHH" };

        for (String guess : wrongGuesses) {
            if (!guess.equals(targetWord)) {
                return guess;
            }
        }

        // This case is unreachable in practice. The return statement is made for type consistency
        return null;

    }

}