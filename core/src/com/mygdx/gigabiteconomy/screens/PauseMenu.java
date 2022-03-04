package com.mygdx.gigabiteconomy.screens;

import java.util.function.IntPredicate;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;
import javax.swing.text.StyledEditorKit.StyledTextAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.gigabiteconomy.GigabitEconomy;

/**
 * The pause menu screen (shown when accessed via. settings button on menu screen or pausing level screen)
 */
public class PauseMenu implements Screen, InputProcessor {
    private GigabitEconomy director;

    private Stage stage;
    private Table pauseMenuTable;

    private InputMultiplexer inputMulti;
    private int pauseCount = 0;

    /**
     * Create a new pause menu screen
     *
     * @param director the game's director class instance
     */
    public PauseMenu(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
        this.inputMulti = new InputMultiplexer();
        inputMulti.addProcessor(stage);
		inputMulti.addProcessor(this);
    }

    /**
     * Show the game complete screen.
     * Called by LibGDX when setScreen() is called to this screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMulti);

        // Import UI skin (commodore)
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        if (pauseCount == 0) {
            pauseMenuTable = new Table();
            pauseMenuTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            TextButton closePauseMenuButton = new TextButton("CLOSE", style);
            closePauseMenuButton.align(Align.topRight);
            closePauseMenuButton.setName(director.getLastPlayedLevel() != null ? director.getLastPlayedLevel() : "MenuScreen");
            pauseMenuTable.add(closePauseMenuButton);

            pauseMenuTable.row();

            Label volumeControlLabel = new Label("AUDIO", style);
            volumeControlLabel.setSize(10, 50);
            pauseMenuTable.add(volumeControlLabel);

            final TextButton audioButton = new TextButton("CAT", style); // final so it can be called from within click listener
            if (director.isMusicPlaying() == true) {
                audioButton.setText("ON");
            }
            if (director.isMusicPlaying() == false) {
                audioButton.setText("OFF");
            }
            pauseMenuTable.add(audioButton);

            pauseMenuTable.row();

            TextButton backToMainMenuButton = null;
            if (director.getLastPlayedLevel() != null) {
                backToMainMenuButton = new TextButton("BACK TO MAIN MENU", style);
                backToMainMenuButton.setName("MenuScreen");
                pauseMenuTable.add(backToMainMenuButton);
            }

            TextButton tutorialButton = new TextButton("HELP", style);
            tutorialButton.setName("TutorialScreen");
            pauseMenuTable.add(tutorialButton);

            pauseMenuTable.row();

            TextButton levelResetButton = null;
            if (director.getLastPlayedLevel() != null) {
                levelResetButton = new TextButton("RESET LEVEL", style);
                pauseMenuTable.add(levelResetButton);
            }

            // Add click listeners for buttons
            ClickListener screenButtonsListener = new ClickListener() {
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
            if (backToMainMenuButton != null) backToMainMenuButton.addListener(screenButtonsListener);
            tutorialButton.addListener(screenButtonsListener);
            closePauseMenuButton.addListener(screenButtonsListener);

            if (levelResetButton != null) {
                ClickListener resetButtonListener = new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            String tempLastPlayedLevel = director.getLastPlayedLevel();
                            director.resetPausedLevel(); // resets the LevelScreen instance currently paused
                            director.switchScreen(tempLastPlayedLevel);
                        } catch (Exception ex) {
                            Gdx.app.error("Exception", String.format("Error resetting level"), ex);
                        }

                        stage.draw();
                    }

                };
                levelResetButton.addListener(resetButtonListener);
            }

            ClickListener audioButtonListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                try {
                    if (director.isMusicPlaying() == true) {
                        director.enableMusic(false);
                        audioButton.setText("OFF");
                    }
                    else if (director.isMusicPlaying() == false) {
                        director.enableMusic(true);
                        audioButton.setText("ON");
                    }
                } catch (Exception ex) {
                    Gdx.app.error("Exception", String.format("Error changing audio"), ex);
                }

                stage.draw();
                }

            };
            audioButton.addListener(audioButtonListener);

            stage.addActor(pauseMenuTable);
        }

        pauseCount++;
    }

    /**
     * Register a key press input
     *
     * @param keycode the key pressed
     * @return true if key press processed, false if not
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            try {
                director.switchScreen(director.getLastPlayedLevel());
            } catch (Exception ex) {
                Gdx.app.error("Exception", String.format("Error switching screen back to level from pause"), ex);
                System.exit(-1);
            }
        }
        else {
            return false;
        }

        return true;
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
        pauseMenuTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Resize the window
     *
     * @param width the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
        // True since camera position with UI is rarely changed
        stage.getViewport().update(width, height, true);
    }

    /**
     * Hide the screen.
     * Called by LibGDX when setScreen()'ed away from the screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
