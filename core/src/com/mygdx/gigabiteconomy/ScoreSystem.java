package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.gigabiteconomy.screens.LevelScreen;

import java.util.Arrays;
import java.util.List;
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

        this.scoreId = Integer.toString(getAllScores().length + 1);
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
     * @return a List containing all the level's recorded scores as strings
     */
    public String[] getAllScores() {
        Object[] scores = levelScores.get().values().toArray();

        String scoresString = Arrays.toString(scores);
        return scoresString.substring(1, scoresString.length() - 1).split(", ");
    }

    /**
     * Get the highest score for the level
     *
     * @return the level's recorded highest score
     */
    public int getHighestScore() {
        return levelScores.getInteger(HIGH_SCORE, 0); // returns 0 if a score for level hasn't yet been set
    }

    public int getLatestScore() {
        return levelScores.getInteger(Integer.toString(getAllScores().length));
    }
}
