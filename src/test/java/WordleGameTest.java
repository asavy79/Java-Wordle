import Wordle.*;
import Wordle.LetterEvaluator.StandardLetterEvaluator;
import Wordle.State.*;
import Wordle.WordEvaluator.StandardWordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class WordleGameTest {

    private WordleGame game;
    private TestObserver testObserver;
    private List<String> testWordBank;

    @BeforeEach
    void setUp() {
        testWordBank = Arrays.asList("HELLO", "WORLD", "TESTS", "GAMES", "WORDS");

        Random fixedRandom = new Random(42);

        game = new WordleGame.GameBuilder()
                .setWordBank(testWordBank)
                .setRandom(fixedRandom)
                .setMaxGuesses(6)
                .build();

        testObserver = new TestObserver();
        game.addObserver(testObserver);
    }

    @Test
    void testGameCreation() {
        assertNotNull(game);
        assertEquals(6, game.getMaxGuesses());
        assertEquals(0, game.getGuessCount());
        assertNotNull(game.getTargetWord());
        assertTrue(testWordBank.contains(game.getTargetWord()));
        assertInstanceOf(PlayingState.class, game.getState());
    }


    @Test
    void testValidGuessProcessing() {
        String targetWord = game.getTargetWord();
        int initialGuessCount = game.getGuessCount();

        boolean result = game.processGuess("HELLO");

        assertEquals(initialGuessCount + 1, game.getGuessCount());
        assertEquals("HELLO", testObserver.getLastGuess());
        assertEquals(1, testObserver.getGuessCount());

        assertEquals(targetWord.equals("HELLO"), result);
    }

    @Test
    void testInvalidGuessRejection() {
        int initialGuessCount = game.getGuessCount();

        assertFalse(game.processGuess(null));
        assertEquals(initialGuessCount, game.getGuessCount());


        assertFalse(game.processGuess("HI"));
        assertEquals(initialGuessCount, game.getGuessCount());

        assertFalse(game.processGuess("TOOLONG"));
        assertEquals(initialGuessCount, game.getGuessCount());

    }


    @Test
    void testGameReset() {
        game.processGuess("HELLO");
        game.processGuess("WORLD");

        game.resetGame();

        assertEquals(0, game.getGuessCount());

        assertNotNull(game.getTargetWord());
        assertTrue(testWordBank.contains(game.getTargetWord()));

        assertTrue(game.getBoard().getBoard().isEmpty());
    }

    @Test
    void testGameBuilder() {
        Random customRandom = new Random(123);
        List<String> customWordBank = Arrays.asList("APPLE", "GRAPE", "PEACH");

        WordleGame customGame = new WordleGame.GameBuilder()
                .setWordBank(customWordBank)
                .setRandom(customRandom)
                .setMaxGuesses(3)
                .build();

        assertEquals(3, customGame.getMaxGuesses());
        assertTrue(customWordBank.contains(customGame.getTargetWord()));
    }

}