# Java-Wordle

This document identifies and explains the 5 design patterns implemented in the Wordle game.

## 1. Strategy Pattern

**Location**: `Wordle/LetterEvaluator.java`, `Wordle/StandardWordleEvaluator.java`, `Wordle/WordValidator.java`, `Wordle/StandardWordValidator.java`

**Purpose**: Allows different algorithms to be used interchangeably for letter evaluation and word validation.

**Implementation**:
- `LetterEvaluator` interface defines the strategy for evaluating letters
- `StandardWordleEvaluator` implements the standard Wordle evaluation logic (handles duplicate letters correctly)
- `WordValidator` interface defines the strategy for validating words
- `StandardWordValidator` implements standard validation (checks word length and word bank)

**Benefits**: Easy to add new game modes or rule variations without modifying existing code.

## 2. Observer Pattern

**Location**: `Wordle/GameObserver.java`, `Wordle/WordleGame.java`, `View/WordleJavaFXView.java`

**Purpose**: Notifies multiple observers when game state changes occur.

**Implementation**:
- `GameObserver` interface defines the contract for observers
- `WordleGame` maintains a list of observers and notifies them on state changes
- `WordleJavaFXView` implements `GameObserver` to receive updates and refresh the UI

**Benefits**: Decouples the model from the view, allowing multiple views to observe the same game.

## 3. Factory Pattern

**Location**: `Wordle/GameComponentFactory.java`

**Purpose**: Centralizes object creation logic and provides a simple interface for creating game components.

**Implementation**:
- `GameComponentFactory` provides static factory methods for creating:
  - `WordleGame` instances
  - `Board` instances
  - `LetterEvaluator` instances
  - `WordValidator` instances

**Benefits**: Encapsulates complex object creation, makes it easy to change creation logic, and supports dependency injection.

## 4. Command Pattern

**Location**: `Wordle/GameCommand.java`, `Wordle/MakeGuessCommand.java`, `Wordle/ResetGameCommand.java`, `Controller/GameController.java`

**Purpose**: Encapsulates game actions as objects, allowing for undo/redo functionality and queuing of commands.

**Implementation**:
- `GameCommand` interface defines the command contract
- `MakeGuessCommand` encapsulates the action of making a guess
- `ResetGameCommand` encapsulates the action of resetting the game
- `GameController` uses commands to execute game actions

**Benefits**: Decouples the invoker (controller) from the receiver (game), enables command queuing, and supports undo/redo (though not fully implemented in this game).

## 5. State Pattern

**Location**: `Wordle/GameState.java`, `Wordle/PlayingState.java`, `Wordle/WonState.java`, `Wordle/LostState.java`, `Wordle/WordleGame.java`

**Purpose**: Manages game state transitions and behavior based on current state.

**Implementation**:
- `GameState` interface defines state behavior
- `PlayingState` - game is in progress, player can make guesses
- `WonState` - player has won, no more guesses allowed
- `LostState` - player has lost, no more guesses allowed
- `WordleGame` maintains the current state and delegates behavior to it

**Benefits**: Eliminates large if-else chains for state management, makes state transitions explicit, and follows Open/Closed Principle.

## Additional Design Principles

### Dependency Injection
**Location**: `Controller/DependencyContainer.java`

The `DependencyContainer` class manages the creation and wiring of dependencies, implementing dependency injection manually. This allows for:
- Loose coupling between components
- Easy testing (can inject mocks)
- Centralized dependency management

### Polymorphism Over Switch Statements
**Location**: `Wordle/LetterStatus.java`, `View/WordleJavaFXView.java`

Instead of using switch statements, the code uses polymorphism:
- `LetterStatus` enum has methods (`getColorCode()`, `getTextColor()`) that return values based on the enum value
- The UI code calls these polymorphic methods instead of switching on the enum

### MVC Architecture
- **Model**: `Wordle/WordleGame.java`, `Wordle/Board.java`, `Wordle/LetterGuess.java`
- **View**: `View/WordleJavaFXView.java`, `View/BoardDisplayer.java`
- **Controller**: `Controller/GameController.java`

The application follows a clear separation of concerns with the Model-View-Controller pattern.