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
 * Class representing the pause menu
 * @param pauseMenuTable a table where all visual elements of the screen are stored
 */
public class PauseMenu implements Screen, InputProcessor {
    private GigabitEconomy director;

    private Stage stage;
    private Table pauseMenuTable;

    private InputMultiplexer inputMulti;

    public PauseMenu(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
        this.inputMulti = new InputMultiplexer();
        inputMulti.addProcessor(stage);
        inputMulti.addProcessor(this);
    }

    /**
     * @param closePauseMenuButton a button which closes pause screen
     * @param audioButton a button which closes pause screen
     * @param backToMainMenuButton a button which opens MenuScreen
     * @param tutorialButton a button which opens TutorialScreen
     * @param res1920Button a button which changes resolution to 1920x1080
     * @param res1366Button a button which changes resolution to 1366x728
     * @param res1280Button a button which changes resolution to 1280x720
     * @param levelResetButton a button which resets the current level
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMulti);

        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        pauseMenuTable = new Table();
        pauseMenuTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton closePauseMenuButton = new TextButton("CLOSE", style);
        closePauseMenuButton.align(Align.topRight);
        closePauseMenuButton.setName(director.getLastPlayedLevel());
        pauseMenuTable.add(closePauseMenuButton);

        pauseMenuTable.row();

        Label volumeControlLabel = new Label("AUDIO", style);
        volumeControlLabel.setSize(10, 50);
        pauseMenuTable.add(volumeControlLabel);

        TextButton audioButton = new TextButton("CAT", style);
        if (director.isMusicPlaying() == true) {
            audioButton.setText("ON");
        }
        if (director.isMusicPlaying() == false) {
            audioButton.setText("OFF");
        }
        pauseMenuTable.add(audioButton);

        pauseMenuTable.row();

        TextButton backToMainMenuButton = new TextButton("BACK TO MAIN MENU", style);
        backToMainMenuButton.setName("MenuScreen");
        pauseMenuTable.add(backToMainMenuButton);

        TextButton tutorialButton = new TextButton("HELP", style);
        tutorialButton.setName("TutorialScreen");
        pauseMenuTable.add(tutorialButton);

        pauseMenuTable.row();

        TextButton res1920Button = new TextButton("1920 x 1080", style);
        res1920Button.setName("res1920");
        pauseMenuTable.add(res1920Button);

        pauseMenuTable.row();

        TextButton res1366Button = new TextButton("1366 x 768", style);
        res1366Button.setName("res1366");
        pauseMenuTable.add(res1366Button);

        pauseMenuTable.row();

        TextButton res1280Button = new TextButton("1280 x 720", style);
        res1280Button.setName("res1280");
        pauseMenuTable.add(res1280Button);

        pauseMenuTable.row();

        TextButton levelResetButton = new TextButton("RESET LEVEL", style);
        levelResetButton.setName("resetthelevel");
        pauseMenuTable.add(levelResetButton);

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
        backToMainMenuButton.addListener(screenButtonsListener);
        tutorialButton.addListener(screenButtonsListener);
        closePauseMenuButton.addListener(screenButtonsListener);

        ClickListener resButtonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String buttonName = event.getListenerActor().getName();

                try {
                    if (buttonName == "res1920") {
                        resize(1920, 1080);
                        // Gdx.graphics.setWindowedMode(1920, 1080);
                        pauseMenuTable.setBounds(0, 0, 1920, 1080);
                    } else if (buttonName == "res1366") {
                        resize(1366, 768);

                        // LwjglApplicationConfiguration.lw = 1080;
                        pauseMenuTable.setBounds(0, 0, 1366, 768);
                    } else if (buttonName == "res1280") {
                        resize(1280, 720);
                        pauseMenuTable.setBounds(0, 0, 1280, 720);
                    }
                } catch (Exception ex) {
                    Gdx.app.error("Exception", String.format("Error changing resolution to %s", buttonName), ex);
                    System.exit(-1);
                }

                if (buttonName == "resetthelevel") {
                    try {
                        String tempLastPlayedLevel = director.getLastPlayedLevel();
                        director.switchScreen("MenuScreen");
                        director.switchScreen(tempLastPlayedLevel);

                    } catch (Exception ex) {
                        Gdx.app.error("Exception",
                                String.format("Error switching screen to" + director.getLastPlayedLevel()), ex);
                    }
                }
            }
        };

        res1280Button.addListener(resButtonsListener);
        res1366Button.addListener(resButtonsListener);
        res1920Button.addListener(resButtonsListener);
        levelResetButton.addListener(resButtonsListener);

        ClickListener audioButtonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                try {
                    if (director.isMusicPlaying() == true) {
                        director.enableMusic(false);
                    } else if (director.isMusicPlaying() == false) {
                        director.enableMusic(true);
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        pauseMenuTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        // True since camera position with UI is rarely changed
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            try {
                director.switchScreen(director.getLastPlayedLevel());
            } catch (Exception ex) {
                Gdx.app.error("Exception", String.format("Error switching screen back"), ex);
                System.exit(-1);
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

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
}
