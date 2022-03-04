package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.GigabitEconomy;

/**
 * The menu screen which is shown on initial game load.
 */
public class MenuScreen implements Screen {
    private static final Texture BACKGROUND_TEXTURE = new Texture("ui_elements/homescreen.png");

    private GigabitEconomy director;
    private Stage stage;
    private Table buttons;

    TextureAtlas ta;
    private Array<TextureAtlas.AtlasRegion> regions;
    private TextureRegion current;

    public MenuScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Import UI skin (commodore)
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        // Buttons
        buttons = new Table();
        //add background
        buttons.setBackground(new TextureRegionDrawable(new TextureRegion(BACKGROUND_TEXTURE)));
        buttons.setFillParent(true);
        buttons.bottom();

        // add button images
        ta = new TextureAtlas("ui_elements/icons.txt");
        regions = ta.getRegions();
        current = regions.get(7);
        Drawable drawable = new TextureRegionDrawable(current);
        current = regions.get(1);
        Drawable drawable2 = new TextureRegionDrawable(current);
        current = regions.get(4);
        Drawable drawable3 = new TextureRegionDrawable(current);

        //add buttons
        ImageButton level1Button = new ImageButton(drawable);
        level1Button.setName("LevelSelectScreen");
        buttons.add(level1Button).padBottom(100);

        
        // add tutorial button
        ImageButton tutorialButton = new ImageButton(drawable2);
        tutorialButton.setName("TutorialScreen");
        buttons.add(tutorialButton).padBottom(100).padLeft(20);

        // add settings button
        ImageButton settingsButton = new ImageButton(drawable3);
        settingsButton.setName("PauseMenu");
        buttons.add(settingsButton).padBottom(100).padLeft(20);


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
        level1Button.addListener(buttonsListener);
        tutorialButton.addListener(buttonsListener);
        settingsButton.addListener(buttonsListener);

        stage.addActor(buttons);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // True since camera position with UI is rarely changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
