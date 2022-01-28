package com.mygdx.gigabiteconomy.scenes;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gigabiteconomy.sprites.Enemy;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base class for all level screens.
 */
abstract class LevelScreen implements Screen, ApplicationListener {
    private GigabitEconomy director;
    private TileManager tileManager;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private ArrayList<GameObject> sprites = new ArrayList<GameObject>(); //First sprite is ALWAYS player
    private SpriteBatch batch;
    private Player player;
    private ArrayList<GameObject> enemies;

    public LevelScreen(GigabitEconomy director, Player player, ArrayList<GameObject> enemies, Texture backgroundTexture) {
        this.director = director;
        this.player = player;
        this.enemies = enemies;
        this.backgroundTexture = backgroundTexture;

        //Add players to TileManager
    }

    public void show() {
        batch = new SpriteBatch();

        // Add background
        backgroundSprite = new Sprite(backgroundTexture);
        System.out.println("Texture dimensions: h:" + backgroundTexture.getHeight() + " w:" + backgroundTexture.getWidth());
        tileManager = new TileManager(135, backgroundTexture.getHeight()/2, backgroundTexture.getWidth(), 0, 0);
        
        // Add player
        sprites.add(player);
        Gdx.input.setInputProcessor(this.player);

        // Add enemies
        for (GameObject enemy : enemies) {
            sprites.add(enemy);
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // drawing the background
        backgroundSprite.draw(batch);

        // this thing makes the camera work XD
        batch.setProjectionMatrix(director.getCombined());

        //This should only take place when player gets to a certain position in camera view
        // update camera position
        director.updateCameraPos(player.getActorX(), player.getActorY());

        for (GameObject sprite : sprites) {
            if (sprite.isMoving()) sprite.move();
            batch.draw(sprite.getCurrRegion(), sprite.getActorX(), sprite.getActorY());
        }
        batch.end();

    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        batch.dispose();
    }
}
