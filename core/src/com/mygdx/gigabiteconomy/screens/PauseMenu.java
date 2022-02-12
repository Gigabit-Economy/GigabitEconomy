package com.mygdx.gigabiteconomy.screens;

import javax.swing.JFrame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.Player;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.*;

import java.security.Key;
import java.util.ArrayList;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.*;
import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;
import java.util.ArrayList;

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
public class PauseMenu implements Screen {
    private GigabitEconomy director;
    private Stage stage;
    private Table pauseMenuTable;

    private Slider volumeControlSlider;

    private int pauseCount = 0;

    public PauseMenu(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));

        if (pauseCount == 0) {
            pauseMenuTable = new Table();
            pauseMenuTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            // pauseMenuTable.setFillParent(true);
            // pauseMenuTable.center();

            TextButton closePauseMenuButton = new TextButton("CLOSE", style);
            closePauseMenuButton.align(Align.topRight);
            pauseMenuTable.add(closePauseMenuButton);

            pauseMenuTable.row();

            TextField volumeControField = new TextField("AUDIO", style);
            pauseMenuTable.add(volumeControField);

            Slider volumeControlSlider = new Slider(-40, 6, 2, false, style);
            volumeControlSlider.setName("volumeSlider");
            // volumeControlSlider.align(Align.topLeft);
            volumeControlSlider.setWidth(300);
            volumeControlSlider.setHeight(500);
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
                        // Switch to selected level screen via. director
                        try {
                            director.switchScreen(buttonName);
                        } catch (Exception ex) {
                            Gdx.app.error("Exception", String.format("Error switching screen to %s", "menu"), ex);
                            System.exit(-1);
                        }
                    }

                    if (buttonName == "res1920") {
                        System.out.println("Res1920 is clicked");
                        Gdx.graphics.setWindowedMode(1920, 1080);
                        pauseMenuTable.setBounds(0, 0, 1920, 1080);
                    }

                    if (buttonName == "res1366") {
                        System.out.println("Res1366 is clicked");
                        Gdx.graphics.setWindowedMode(1366, 768);
                        pauseMenuTable.setBounds(0, 0, 1366, 768);
                    }

                    if (buttonName == "res1280") {
                        System.out.println("Res1280 is clicked");
                        Gdx.graphics.setWindowedMode(1280, 720);
                        pauseMenuTable.setBounds(0, 0, 1280, 720);
                    }

                }
            };
            pauseMenuTable.debug();
            backToMainMenuButton.addListener(buttonsListener);
            res1280Button.addListener(buttonsListener);
            res1366Button.addListener(buttonsListener);
            res1920Button.addListener(buttonsListener);

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

}
