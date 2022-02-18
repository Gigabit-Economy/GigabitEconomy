package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.gigabiteconomy.screens.LevelScreen;

import java.util.Map;

public class ScoreSystem {
    private String levelName;
    private int score = 0;
    private Preferences levelScores = Gdx.app.getPreferences("scores");

    public ScoreSystem(LevelScreen level) {
        this.levelName = level.getClass().toString();
    }

    /**
     * Get the current level's score
     *
     * @return the current level's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Add to the current level's score
     *
     * @param amount the amount to add to the score
     */
    public void addToScore(int amount) {
        score += amount;
    }

    /**
     * Save the current level's score (persists as level score if higher than current level high score)
     */
    public void saveScore() {
        int currentLevelScore = levelScores.getInteger(levelName, 0); // returns 0 if score for level not yet set

        if (score > currentLevelScore) {
            levelScores.putInteger(levelName, score);
            levelScores.flush();
        }
    }

    /**
     * Get all scores (for every level)
     *
     * @return a map containing the level's name and its highest score
     */
    public Map<String, ?> getAllScores() {
        return levelScores.get();
    }
}
