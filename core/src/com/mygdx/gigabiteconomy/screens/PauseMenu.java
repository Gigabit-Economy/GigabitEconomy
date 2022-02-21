package com.mygdx.gigabiteconomy.screens;

import java.util.function.IntPredicate;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

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
 * Constructor to create a number of tiles for the game screen
 * 
 * @param director            SXXXXXte
 * @param stage               HXXXXXXight)
 * @param pauseMenuTable      WXXXXh)
 * @param volumeControlSlider XXXXXXt)
 * @param pauseCount          Makes sure pauseMenu is not drawn more than one
 *                            time
 */
public class PauseMenu implements Screen, InputProcessor {
    private GigabitEconomy director;

    private Stage stage;
    private Table pauseMenuTable;

    private Slider volumeControlSlider;

    private InputMultiplexer inputMulti;
    private int pauseCount = 0;

    public PauseMenu(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
        this.inputMulti = new InputMultiplexer();
        inputMulti.addProcessor(stage);
		inputMulti.addProcessor(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMulti);
        
        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));

        if (pauseCount == 0) {
            pauseMenuTable = new Table();
            pauseMenuTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            TextButton closePauseMenuButton = new TextButton("CLOSE", style);
            closePauseMenuButton.align(Align.topRight);
            closePauseMenuButton.setName("closepausemenu");
            pauseMenuTable.add(closePauseMenuButton);

            pauseMenuTable.row();

            Label volumeControlLabel = new Label("AUDIO", style);
            volumeControlLabel.setSize(10, 50);
            pauseMenuTable.add(volumeControlLabel);

            Slider volumeControlSlider = new Slider(-40, 6, 2, false, style);
            volumeControlSlider.setName("volumeSlider");
            volumeControlSlider.setOriginX(volumeControlLabel.getX());
            pauseMenuTable.add(volumeControlSlider);

            pauseMenuTable.row();

            TextButton backToMainMenuButton = new TextButton("BACK TO MAIN MENU", style);
            backToMainMenuButton.setName("menu");
            pauseMenuTable.add(backToMainMenuButton);

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

            // Add click listener for buttons
            ClickListener buttonsListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String buttonName = event.getListenerActor().getName();
                    System.out.println(buttonName);

                    if (buttonName == "menu") {
                        try {
                            director.switchScreen(buttonName);
                        } catch (Exception ex) {
                            Gdx.app.error("Exception", String.format("Error switching screen to %s", "menu"), ex);
                            System.exit(-1);
                        }
                    }

                    if (buttonName == "res1920") {
                        Gdx.graphics.setWindowedMode(1920, 1080);
                        pauseMenuTable.setBounds(0, 0, 1920, 1080);
                    }

                    if (buttonName == "res1366") {
                        Gdx.graphics.setWindowedMode(1366, 768);
                        pauseMenuTable.setBounds(0, 0, 1366, 768);
                    }

                    if (buttonName == "res1280") {
                        Gdx.graphics.setWindowedMode(1280, 720);
                        pauseMenuTable.setBounds(0, 0, 1280, 720);
                    }

                    if (buttonName == "closepausemenu") {
                        try {
                            director.switchScreen("LevelOneScreen");
                        } catch (Exception ex) {
                            Gdx.app.error("Exception", String.format("Error switching screen to LevelOneScreen"), ex);
                            System.exit(-1);
                        }
                    }  
                }
            };
            pauseMenuTable.debug();
            backToMainMenuButton.addListener(buttonsListener);
            res1280Button.addListener(buttonsListener);
            res1366Button.addListener(buttonsListener);
            res1920Button.addListener(buttonsListener);
            closePauseMenuButton.addListener(buttonsListener);

            stage.addActor(pauseMenuTable);
        }
        pauseCount++;

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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            try {
                director.switchScreen("LevelOneScreen");
            } catch (Exception ex) {
                Gdx.app.error("Exception", String.format("Error switching screen to LevelOneScreen"), ex);
                System.exit(-1);
            }
        }
        else{
            return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }
}
