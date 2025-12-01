package Wordle;


import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WordleGame {

    private final Board board;
    private final Random random;
    private final Integer maxGuesses;
    private final List<String> wordBank;
    private String targetWord;
    private Integer guessCount;


    public static class GameBuilder {

        private static final Integer DEFAULT_MAX_GUESSES = 6;
        private static final List<String> DEFAULT_WORDBANK = List.of("APPLE", "BLIND", "SHEET", "CRUSH", "RELAX");

        private Board board;
        private Random random;
        private Integer maxGuesses;
        private List<String> wordBank;

        public GameBuilder() {
            this.board = null;
            this.random = null;
            this.maxGuesses = null;
            this.wordBank = null;
        };

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

        public WordleGame build() {

            if(board == null) {
                board = new Board();
            }
            if(random == null) {
                random = new Random();
            }
            if (maxGuesses == null) {
                maxGuesses = DEFAULT_MAX_GUESSES;
            }

            if (wordBank == null) {
                wordBank = DEFAULT_WORDBANK;
            }
            else {
                for(String word : wordBank) {
                    if(word.length() != 5) {
                        throw new IllegalArgumentException("Word length should be 5");
                    }
                }
            }
            return new WordleGame(board, maxGuesses, wordBank, random);
        }
    }


    private WordleGame(Board board, Integer maxGuesses, List<String> wordBank, Random random) {
        this.maxGuesses = maxGuesses;
        this.random = random;
        this.board = board;
        this.wordBank = wordBank;
        resetGame();
    }

    private String getRandomWord() {
        return wordBank.get(random.nextInt(wordBank.size()));
    }

    public void resetGame() {
        board.reset();
        this.targetWord = getRandomWord();
        this.guessCount = 0;
    }

    public Boolean playTurn(String guess) {
        if(Objects.equals(guessCount, maxGuesses)) {
            return false;
        }
        else {
            guessCount++;
            board.addWord(guess, targetWord);
            return checkWin(guess);
        }
    }

    public Board getBoard() {
        return board;
    }

    private Boolean checkWin(String word) {
        return Objects.equals(word, targetWord);
    }
}
