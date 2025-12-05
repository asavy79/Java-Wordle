package Wordle;

import Wordle.LetterEvaluator.LetterEvaluator;
import Wordle.LetterEvaluator.StandardLetterEvaluator;
import Wordle.State.GameState;
import Wordle.State.LostState;
import Wordle.State.PlayingState;
import Wordle.State.WonState;
import Wordle.WordEvaluator.StandardWordValidator;
import Wordle.WordEvaluator.WordValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Model: Core game logic
 * Uses State pattern for game states
 * Uses Observer pattern for notifications
 * Uses Strategy pattern for validation and evaluation
 * The builder pattern is implemented as an additional method of constructing a game
 */
public class WordleGame {

    private final Board board;
    private final Random random;
    private final Integer maxGuesses;
    private final List<String> wordBank;
    private final WordValidator validator;
    private final List<GameObserver> observers;
    
    private String targetWord;
    private Integer guessCount;
    private GameState state;

    public static class GameBuilder {

        private static final Integer DEFAULT_MAX_GUESSES = 6;

        private Board board;
        private Random random;
        private Integer maxGuesses;
        private List<String> wordBank;
        private LetterEvaluator evaluator;
        private WordValidator validator;

        public GameBuilder() {
            this.board = null;
            this.random = null;
            this.maxGuesses = null;
            this.wordBank = null;
            this.evaluator = null;
            this.validator = null;
        }

        public GameBuilder setBoard(Board board) {
            this.board = board;
            return this;
        }

        public GameBuilder setRandom(Random random) {
            this.random = random;
            return this;
        }

        public GameBuilder setMaxGuesses(Integer maxGuesses) {
            this.maxGuesses = maxGuesses;
            return this;
        }

        public GameBuilder setWordBank(List<String> wordBank) {
            this.wordBank = wordBank;
            return this;
        }

        public GameBuilder setEvaluator(LetterEvaluator evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        public GameBuilder setValidator(WordValidator validator) {
            this.validator = validator;
            return this;
        }

        public WordleGame build() {
            if (board == null) {
                if (evaluator == null) {
                    evaluator = new StandardLetterEvaluator();
                }
                board = new Board(evaluator);
            }
            if (random == null) {
                random = new Random();
            }
            if (maxGuesses == null) {
                maxGuesses = DEFAULT_MAX_GUESSES;
            }
            if (wordBank == null) {
                throw new IllegalArgumentException("Word bank must be provided");
            }
            for (String word : wordBank) {
                if (word.length() != 5) {
                    throw new IllegalArgumentException("All words in word bank must be exactly 5 letters. Found: " + word);
                }
            }
            if (validator == null) {
                validator = new StandardWordValidator(5);
            }
            return new WordleGame(board, maxGuesses, wordBank, random, evaluator, validator);
        }
    }

    WordleGame(Board board, Integer maxGuesses, List<String> wordBank, Random random, LetterEvaluator evaluator, WordValidator validator) {
        this.maxGuesses = maxGuesses;
        this.random = random;
        this.board = board;
        this.wordBank = wordBank;
        this.validator = validator;
        this.observers = new ArrayList<>();
        resetGame();
    }

    private String getRandomWord() {
        return wordBank.get(random.nextInt(wordBank.size()));
    }

    public void resetGame() {
        board.reset();
        this.targetWord = getRandomWord();
        this.guessCount = 0;
        this.state = new PlayingState();
        notifyObserversReset();
    }

    public boolean processGuess(String guess) {
        if (guess == null) {
            return false;
        }

        if (!state.canMakeGuess()) {
            return false;
        }

        String upperGuess = guess.toUpperCase();

        if (!validator.isValid(upperGuess)) {
            return false;
        }
        
        guessCount++;
        board.addWord(upperGuess, targetWord);
        boolean won = checkWin(upperGuess);
        
        notifyObserversGuess(upperGuess);
        
        if (won) {
            setState(new WonState());
        } else if (guessCount >= maxGuesses) {
            setState(new LostState(targetWord));
        }
        
        notifyObserversStateChanged();
        return won;
    }

    public void setState(GameState newState) {
        this.state = newState;
        notifyObserversStateChanged();
    }

    public GameState getState() {
        return state;
    }

    public Board getBoard() {
        return board;
    }

    public Integer getGuessCount() {
        return guessCount;
    }

    public Integer getMaxGuesses() {
        return maxGuesses;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public WordValidator getValidator() {
        return validator;
    }

    private boolean checkWin(String word) {
        return Objects.equals(word, targetWord);
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversStateChanged() {
        for (GameObserver observer : observers) {
            observer.onGameStateChanged(state);
        }
    }

    private void notifyObserversGuess(String guess) {
        for (GameObserver observer : observers) {
            observer.onGuessMade(this, guess);
        }
    }

    private void notifyObserversReset() {
        for (GameObserver observer : observers) {
            observer.onGameReset();
        }
    }
}