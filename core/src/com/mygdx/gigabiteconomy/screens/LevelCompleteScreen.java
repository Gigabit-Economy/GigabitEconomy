package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.ScoreSystem;

import java.util.Arrays;

/**
 * Screen shown once a level is complete (level 1/2)
 */
public class LevelCompleteScreen implements Screen {
    private GigabitEconomy director;

    private Stage stage;
    private Table levelCompletedTable;

    private ScoreSystem levelScores;

    /**
     * Create a new level complete screen
     *
     * @param director the game's director class instance
     */
    public LevelCompleteScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    /**
     * Show the game complete screen.
     * Called by LibGDX when setScreen() is called to this screen.
     */
    @Override
    public void show() {
        this.levelScores = new ScoreSystem(director.getLastPlayedLevel());

        Gdx.input.setInputProcessor(stage);
        // Import UI skin (commodore)
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        levelCompletedTable = new Table();
        levelCompletedTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label congratulationsLabel = new Label("CONGRATULATIONS!", style);
        levelCompletedTable.add(congratulationsLabel).colspan(2);
        levelCompletedTable.row();

        Label passedThisLevelLabel = new Label("YOU HAVE COMPLETED THIS LEVEL!", style);
        levelCompletedTable.add(passedThisLevelLabel).colspan(2).padBottom(50);
        levelCompletedTable.row();

        Label yourScoreWasLabel = new Label("YOUR SCORE WAS: " + levelScores.getLatestScore(), style);
        levelCompletedTable.add(yourScoreWasLabel).colspan(2).pad(30);
        levelCompletedTable.row();

        String highestScoreText = String.format("HIGHEST SCORE: %d", levelScores.getHighestScore());
        Label yourHighestScoreLabel = new Label(highestScoreText, style);
        levelCompletedTable.add(yourHighestScoreLabel).colspan(2).padBottom(10);
        levelCompletedTable.row();

        Label yourPastScoresLabel = new Label("PREVIOUS SCORES:", style);
        levelCompletedTable.add(yourPastScoresLabel).colspan(2);
        levelCompletedTable.row();

        String[] prevScores = levelScores.getAllScores();
        List<String> levelCompletedScoreList = new List(style);
        levelCompletedScoreList.setItems(prevScores);
        levelCompletedTable.add(levelCompletedScoreList).colspan(2);
        levelCompletedTable.row();

        TextButton levelCompletedMainMenuButton = new TextButton("MAIN MENU", style);
        levelCompletedMainMenuButton.setName("MenuScreen");
        levelCompletedTable.add(levelCompletedMainMenuButton).bottom().padTop(50);

        TextButton levelCompletedNextLevelButton = new TextButton("NEXT LEVEL", style);
        levelCompletedNextLevelButton.setName(director.getNextLevel());
        levelCompletedTable.add(levelCompletedNextLevelButton).bottom().padTop(50);
        levelCompletedTable.row();

        // Add click listener for buttons
        ClickListener buttonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String buttonName = event.getListenerActor().getName();

                try {
                    director.switchScreen(buttonName);
                } catch (Exception ex) {
                    Gdx.app.error("Exception", String.format("Error switching screen to %s", buttonName), ex);
                    System.exit(-1);
                }
            }
        };
        levelCompletedNextLevelButton.addListener(buttonsListener);
        levelCompletedMainMenuButton.addListener(buttonsListener);
        stage.addActor(levelCompletedTable);
    }

    /**
     * Render the game complete screen
     *
     * @param delta the time elapsed since the previous render (in seconds)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        levelCompletedTable.setBounds(0, 0, Gdx.graphics.getWidth() + 180, Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    /**
     * Hide the screen.
     * Called by LibGDX when setScreen()'ed away from the screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
