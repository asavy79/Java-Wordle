package org.wordle_project;

import Controller.DependencyContainer;
import View.WordleJavaFXView;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.List;

/**
 * Main entry point for Wordle game
 * Uses Dependency Injection to wire components
 */
public class Main {
    
    private static final List<String> WORD_BANK = List.of(
        "APPLE", "BLIND", "SHEET", "CRUSH", "RELAX", "WORLD", "HELLO",
        "MUSIC", "DANCE", "SMILE", "OCEAN", "RIVER", "GREEN", "TIGER",
        "EAGLE", "SHARK", "WHALE", "PANDA", "ZEBRA", "HOUSE", "PLANT",
        "STONE", "CLOUD", "STORM", "LIGHT", "AUDIO", "QUICK", "PEACE",
        "CHAOS", "ORDER", "MAGIC", "CRANE", "PLANE", "GRACE", "TRACE",
        "SPACE", "TRACE", "TRADE", "GRADE", "FRAME", "FLAME", "BLAME"
    );
    
    public static void main(String[] args) {
        Application.launch(WordleJavaFXView.class, args);
    }
    
    /**
     * Initialize the game with dependency injection
     * Called from JavaFX application thread after view is created
     */
    public static void initializeGame(WordleJavaFXView view) {
        DependencyContainer container = new DependencyContainer(WORD_BANK, 6, view);

        WordleJavaFXView.setController(container.getController());
    }
}