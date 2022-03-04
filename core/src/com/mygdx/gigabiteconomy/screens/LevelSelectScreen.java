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

/**
 * Screen shown when selecting a level
 */
public class LevelSelectScreen implements Screen {
    private GigabitEconomy director;

    private Stage stage;
    private Table levelSelectTable;

    private ScoreSystem score1 = new ScoreSystem("LevelOneScreen");
    private ScoreSystem score2 = new ScoreSystem("LevelTwoScreen");
    private ScoreSystem score3 = new ScoreSystem("LevelThreeScreen");
    private int pauseCount = 0;

    /**
     * Create a new level failed screen
     *
     * @param director the game's director class instance
     */
    public LevelSelectScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    /**
     * Show the game complete screen.
     * Called by LibGDX when setScreen() is called to this screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Import UI skin (commodore)
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
        levelSelectTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Resize the window
     *
     * @param width the new screen width
     * @param height the new screen height
     */
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
