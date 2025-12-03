package Wordle;

/**
 * Enum for letter status in a guess
 */
public enum LetterStatus {
    CORRECT_POSITION,
    INCORRECT,
    INCLUDED;
    
    /**
     * Polymorphic method to get color representation
     */
    public String getColorCode() {
        if (this == CORRECT_POSITION) {
            return "#6aaa64"; // Green
        } else if (this == INCLUDED) {
            return "#c9b458"; // Yellow
        } else {
            return "#787c7e"; // Gray
        }
    }
    
    public String getTextColor() {
        return this == INCORRECT ? "white" : "white";
    }
}
