package Wordle;

public class LetterGuess {

    private final Character letter;
    private final LetterStatus status;

    public LetterGuess(Character letter, LetterStatus status) {
        this.letter = letter;
        this.status = status;
    }

    public Character getLetter() {
        return letter;
    }
    public LetterStatus getStatus() {
        return status;
    }


}
