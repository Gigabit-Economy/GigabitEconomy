package com.mygdx.gigabiteconomy.screens;

import java.util.ArrayList;
import java.util.Arrays;

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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class LevelCompleteScreen implements Screen {
    private GigabitEconomy director;

    private Stage stage;
    private Table levelComepletedTable;

    public LevelCompleteScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));

        levelComepletedTable = new Table();
        levelComepletedTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label congratulationsLabel = new Label("CONGRATULATIONS!", style);
        levelComepletedTable.add(congratulationsLabel).size(40);
        levelComepletedTable.row();

        Label passedThisLevelLabel = new Label("YOU HAVE PASSED THIS LEVEL!", style);
        levelComepletedTable.add(passedThisLevelLabel).size(40);
        levelComepletedTable.row();

        int scoreTestNumber = 45656465;
        Label yourScoreWasLabel = new Label("YOUR SCORE WAS: " + scoreTestNumber, style);
        levelComepletedTable.add(yourScoreWasLabel).size(40);
        levelComepletedTable.row();

        Label yourPastScoresLabel = new Label("YOUR PAST SCORES:", style);
        levelComepletedTable.add(yourPastScoresLabel).padLeft(-600);

        TextButton levelCompletedMainMenuButton = new TextButton("MAIN MENU", style);
        levelCompletedMainMenuButton.setName("menu");
        levelComepletedTable.add(levelCompletedMainMenuButton);

        TextButton levelCompletedNextLevelButton = new TextButton("NEXT LEVEL", style);
        levelCompletedNextLevelButton.setName("nextlevelscreen");
        levelComepletedTable.add(levelCompletedNextLevelButton).padRight(-300);
        levelComepletedTable.row();

        String[] examples15 = { "Firstexample", "Second example", "54864886", "Example Number4" };
        List<String> levelCompletedScoreList = new List<String>(style);
        levelCompletedScoreList.setItems(examples15);
        levelComepletedTable.add(levelCompletedScoreList).padLeft(-600);

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
        stage.addActor(levelComepletedTable);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        levelComepletedTable.setBounds(0, 0, Gdx.graphics.getWidth() + 180, Gdx.graphics.getHeight());

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
