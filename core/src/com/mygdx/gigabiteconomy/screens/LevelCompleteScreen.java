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

public class LevelCompleteScreen implements Screen {
    private GigabitEconomy director;

    private Stage stage;
    private Table levelCompletedTable;

    private ScoreSystem levelScores;

    public LevelCompleteScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    @Override
    public void show() {
        this.levelScores = new ScoreSystem(director.getLastPlayedLevel());

        Gdx.input.setInputProcessor(stage);
        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));

        levelCompletedTable = new Table();
        levelCompletedTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label congratulationsLabel = new Label("CONGRATULATIONS!", style);
        levelCompletedTable.add(congratulationsLabel).size(40);
        levelCompletedTable.row();

        Label passedThisLevelLabel = new Label("YOU HAVE PASSED THIS LEVEL!", style);
        levelCompletedTable.add(passedThisLevelLabel).size(40);
        levelCompletedTable.row();

        int scoreTestNumber = 45656465;
        Label yourScoreWasLabel = new Label("YOUR SCORE WAS: " + scoreTestNumber, style);
        levelCompletedTable.add(yourScoreWasLabel).size(40);
        levelCompletedTable.row();

        Label yourPastScoresLabel = new Label("YOUR PAST SCORES:", style);
        levelCompletedTable.add(yourPastScoresLabel).padLeft(-600);

        String highestScoreText = String.format("YOUR HIGHEST SCORE: %d", levelScores.getHighestScore());
        Label yourHighestScoreLabel = new Label(highestScoreText, style);
        levelCompletedTable.add(yourHighestScoreLabel).padLeft(-600);

        TextButton levelCompletedMainMenuButton = new TextButton("MAIN MENU", style);
        levelCompletedMainMenuButton.setName("menu");
        levelCompletedTable.add(levelCompletedMainMenuButton);

        TextButton levelCompletedNextLevelButton = new TextButton("NEXT LEVEL", style);
        levelCompletedNextLevelButton.setName("nextlevelscreen");
        levelCompletedTable.add(levelCompletedNextLevelButton).padRight(-300);
        levelCompletedTable.row();

        String[] prevScores = levelScores.getAllScores();
        List<String> levelCompletedScoreList = new List<String>(style);
        levelCompletedScoreList.setItems(prevScores);
        levelCompletedTable.add(levelCompletedScoreList).padLeft(-600);

        // Add click listener for buttons
        ClickListener buttonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String buttonName = event.getListenerActor().getName();
                System.out.println(buttonName);

                // Switch to selected level screen via. director
                if (buttonName == "menu" || buttonName == "") {
                    try {
                        director.switchScreen(buttonName);
                    } catch (Exception ex) {
                        Gdx.app.error("Exception", String.format("Error switching screen to %s", buttonName), ex);
                        System.exit(-1);
                    }
                }
            }
        };
        levelCompletedNextLevelButton.addListener(buttonsListener);
        levelCompletedMainMenuButton.addListener(buttonsListener);
        stage.addActor(levelCompletedTable);

    }

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
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
