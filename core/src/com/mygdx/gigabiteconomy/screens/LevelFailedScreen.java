package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;

/**
 * Screen shown when a level is failed (player health reaches 0)
 */
public class LevelFailedScreen implements Screen{
    private GigabitEconomy director;

    private Stage stage;
    private Table deathScreenTable;

    /**
     * Create a new level failed screen
     *
     * @param director the game's director class instance
     */
    public LevelFailedScreen(GigabitEconomy director) {
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

        deathScreenTable = new Table();
        deathScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label diedLabel = new Label("YOU HAVE DIED!", style);
        deathScreenTable.add(diedLabel).colspan(2);

        deathScreenTable.row();

        TextButton tryAgainButton = new TextButton("TRY AGAIN", style);
        tryAgainButton.setName(director.getLastPlayedLevel());
        deathScreenTable.add(tryAgainButton).left().pad(20);

        TextButton deathScreenMainMenuButton = new TextButton("MAIN MENU", style);
        deathScreenMainMenuButton.setName("MenuScreen");
        deathScreenTable.add(deathScreenMainMenuButton).right().pad(20);
        
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
        deathScreenMainMenuButton.addListener(buttonsListener);
        tryAgainButton.addListener(buttonsListener);

        stage.addActor(deathScreenTable);
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
        deathScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
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

    /**
     * Hide the screen.
     * Called by LibGDX when setScreen()'ed away from the screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
