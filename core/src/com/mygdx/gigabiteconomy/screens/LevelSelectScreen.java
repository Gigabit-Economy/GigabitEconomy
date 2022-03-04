package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.ScoreSystem;

public class LevelSelectScreen implements Screen {
    private GigabitEconomy director;

    private Stage stage;
    private Table levelSelectTable;

    private ScoreSystem score1 = new ScoreSystem("LevelOneScreen");
    private ScoreSystem score2 = new ScoreSystem("LevelTwoScreen");
    private ScoreSystem score3 = new ScoreSystem("LevelThreeScreen");
    private int pauseCount = 0;

    public LevelSelectScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        levelSelectTable = new Table();
        levelSelectTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label selectFirstLevelLabel = new Label("Record score: "+ score1.getHighestScore(), style);
        levelSelectTable.add(selectFirstLevelLabel).padLeft(-200);

        TextButton selectFirstLevelTextButton = new TextButton("Level 1", style);
        selectFirstLevelTextButton.setName("LevelOnePlotScreen");
        levelSelectTable.add(selectFirstLevelTextButton).padRight(-200);

        levelSelectTable.row();

        Label selectSecondLevelLabel = new Label("Record score: "+ score2.getHighestScore(), style);
        levelSelectTable.add(selectSecondLevelLabel).padLeft(-200);

        TextButton selectSecondLevelTextButton = new TextButton("Level 2", style);
        selectSecondLevelTextButton.setName("LevelTwoPlotScreen");
        if (score1.getHighestScore() == 0) {
            selectSecondLevelTextButton.setTouchable(Touchable.disabled);
        }
        levelSelectTable.add(selectSecondLevelTextButton).padRight(-200);
        
        levelSelectTable.row();

        Label selectThirdLevelLabel = new Label("Record score: "+score3.getHighestScore(), style);
        levelSelectTable.add(selectThirdLevelLabel).padLeft(-200);

        TextButton selectThirdLevelTextButton = new TextButton("Level 3", style);
        selectThirdLevelTextButton.setName("LevelThreePlotScreen");
        if (score2.getHighestScore() == 0) {
            selectThirdLevelTextButton.setTouchable(Touchable.disabled);
        }
        levelSelectTable.add(selectThirdLevelTextButton).padRight(-200);

        // Add click listener for buttons
        ClickListener buttonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            String buttonName = event.getListenerActor().getName();

            // Switch to selected level screen via. director
            try {
                director.switchScreen(buttonName);
            } catch (Exception ex) {
                Gdx.app.error("Exception", String.format("Error switching screen to %s", buttonName), ex);
                System.exit(-1);
            }
            }
        };
        selectFirstLevelTextButton.addListener(buttonsListener);
        selectSecondLevelTextButton.addListener(buttonsListener);
        selectThirdLevelTextButton.addListener(buttonsListener);
        selectThirdLevelLabel.addListener(buttonsListener);

        stage.addActor(levelSelectTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        levelSelectTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
