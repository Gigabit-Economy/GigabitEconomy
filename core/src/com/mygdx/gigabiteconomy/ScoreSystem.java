package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.gigabiteconomy.screens.LevelScreen;

import java.util.Map;

public class ScoreSystem {
    private static final String HIGH_SCORE = "HIGH_SCORE";

    private int score = 0;
    private String levelName;
    private Preferences levelScores;
    private String scoreId;

    public ScoreSystem(LevelScreen level) {
        this.levelName = level.getClass().getName();
        this.levelScores = Gdx.app.getPreferences(String.format("%sScores", levelName));

        this.scoreId = Integer.toString(getAllScores().size() + 1);
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
     * Save the current level's score.
     * Also saves the level's score as high score if level score is higher than current level high score.
     */
    public void saveScore() {
        // save score to scoreboard
        levelScores.putInteger(scoreId, score);

        // if higher than current high score (defaults to 0), also set as high score
        int highScore = levelScores.getInteger(HIGH_SCORE, 0); // returns 0 if a score for level hasn't yet been set
        if (score > highScore) {
            levelScores.putInteger(HIGH_SCORE, score);
        }

        // persist changes
        levelScores.flush();
    }

    /**
     * Get all scores for the level
     *
     * @return a map containing all the level's scores as values
     */
    public Map<String, ?> getAllScores() {
        return levelScores.get();
    }
}
