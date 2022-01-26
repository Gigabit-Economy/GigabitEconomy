package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.Player;

import java.util.ArrayList;

/**
 * Base class for all level screens.
 */
abstract class LevelScreen implements Screen, ApplicationListener {
    private GigabitEconomy director;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private ArrayList<GameObject> sprites = new ArrayList<GameObject>(); // First sprite is ALWAYS player
    private SpriteBatch batch;
    private Player player;
    private ArrayList<GameObject> enemies;

    /**
     * A template constructor for use by all level screen subclasses. Sets properties that differ between levels
     * such as game director, player/enemy character sprites & background texture.
     *
     * @param director the instance of the game director
     * @param player the player character for the level
     * @param enemies an ArrayList containing all enemy characters for the level
     * @param backgroundTexture the background graphic of the level
     */
    public LevelScreen(GigabitEconomy director, Player player, ArrayList<GameObject> enemies, Texture backgroundTexture) {
        this.director = director;
        this.player = player;
        this.enemies = enemies;
        this.backgroundTexture = backgroundTexture;
    }

    /**
     * Initialises the sprite batch (which contains all character sprites) and adds the player/enemy characters to it.
     * Called by LibGDX when the screen is set as active by the game director.
     */
    @Override
    public void show() {
        batch = new SpriteBatch();

        // Add background
        backgroundSprite = new Sprite(backgroundTexture);

        // Add player
        sprites.add(player);
        Gdx.input.setInputProcessor(this.player);

        // Add enemies
        sprites.addAll(enemies);
    }

    /**
     * Sets the current view of the screen, drawing sprites on the screen based on their current position; also
     * calls the player collision check system to check for any collisions since the previous render.
     * Called by LibGDX upon each render of the screen.
     *
     * @param delta the time elapsed since the previous render (in seconds)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set screen's projection matrix to director's ortho camera
        batch.setProjectionMatrix(director.getCameraCombined());

        // This should only take place when player gets to a certain position in camera view
        // update camera position to follow player
        director.updateCameraPos(player.getActorX(), director.getCameraPos().y);

        batch.begin();

        // Draw the background
        backgroundSprite.draw(batch);

        // Draw sprites
        for (GameObject sprite : sprites) {
            if (sprite.isMoving()) sprite.move();
            batch.draw(sprite.getCurrRegion(), sprite.getActorX(), sprite.getActorY());
        }

        batch.end();

        playerCollisionCheck();
    }

    /**
     * Iterates through each enemy character sprite, checking if the player character sprite is overlapping
     * and has therefore collided with the enemy.
     */
    private void playerCollisionCheck() {
        for (GameObject enemy : enemies) {
            if (player.getRectangle().overlaps(enemy.getRectangle())) {
                System.out.println("Enemy collision detected");
            }
        }
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

    /**
     * Sets the input processor to null to prevent the LevelScreen's application listener from listening to user
     * inputs; then calls dispose() to remove the screen's assets from memory.
     * Called by LibGDX when the screen is made inactive.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    /**
     * Removes the screen's assets (background texture and sprite batch) from memory when the screen is made inactive
     * and they're therefore no longer needed.
     * Called by hide().
     */
    @Override
    public void dispose() {
        backgroundTexture.dispose();
        batch.dispose();
    }
}