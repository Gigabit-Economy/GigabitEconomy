package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.ScoreSystem;
import com.mygdx.gigabiteconomy.exceptions.ParcelException;
import com.mygdx.gigabiteconomy.exceptions.ScreenException;
import com.mygdx.gigabiteconomy.sprites.*;
import com.mygdx.gigabiteconomy.sprites.tiled.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class which acts as a base class for all level screens.
 * Each individual level class extends this class and defines properties such as
 * the player, enemies, houses &
 * static sprites etc.
 */
public abstract class LevelScreen implements Screen, InputProcessor {
    private GigabitEconomy director;
    private boolean paused = false;

    private TileManager tileManager;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private SpriteBatch batch;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ParcelVan parcelVan;

    private ScoreSystem score = new ScoreSystem(this.getClass().getSimpleName());
    private int parcels = 5;

    private BitmapFont font;
    private String errorText;
    private float errorCountdown;

    /**
     * A template constructor for use by all level screen subclasses. Sets
     * properties that differ between levels such as game director & background texture.
     *
     * @param director          the instance of the game director
     * @param backgroundTexture the background graphic of the level
     */
    public LevelScreen(GigabitEconomy director, Texture backgroundTexture) {
        this.director = director;
        this.backgroundTexture = backgroundTexture;

        // Create Tile Manager for level
        int backgroundTextureHeight = backgroundTexture.getHeight();
        int backgroundTextureWidth = backgroundTexture.getWidth();
        int numberOfTilesHigh = 18;
        tileManager = new TileManager(backgroundTextureHeight / numberOfTilesHigh, backgroundTextureHeight / 2,
                backgroundTextureWidth, 0, 0);
    }

    /**
     * Add a Player to the level.
     * If level already has a Player, as a level can only have one, it'll be overridden.
     *
     * @param player the Player to be added to the level
     */
    public void addPlayer(Player player) {
        this.player = player;
        this.player.setLevel(this);

        addSprite(player);
    }

    /**
     * Add Enemies to the level.
     * Any passed Enemies will be added to the list of existing Enemies already added.
     *
     * @param enemies an ArrayList of enemies to be added
     */
    public void addEnemies(ArrayList<Enemy> enemies) {
        this.enemies.addAll(enemies);
        addSprites(enemies);
    }

    /**
     * Add a Parcel Van to the level.
     * If level already has a Parcel Van, as a level can only have one, it'll be overridden.
     *
     * @param parcelVan the Parcel Van to be added to the level
     */
    public void addParcelVan(ParcelVan parcelVan) {
        this.parcelVan = parcelVan;
        addSprite(parcelVan);
    }

    /**
     * Adds ArrayList(s) of sprites to the level, instantiating them with the Tile Manager
     *
     * @param objArr ArrayList(s) containing sprites (TiledObjects) - in priority order
     */
    public void addSprites(ArrayList<? extends TiledObject>... objArr) {
        tileManager.initObjects(objArr);
    }

    /**
     * Add a sprite (TiledObject) to the level
     *
     * @param sprite the TiledObject representing the sprite
     */
    public void addSprite(TiledObject sprite) {
        ArrayList<TiledObject> spriteList = new ArrayList<TiledObject>(Arrays.asList(sprite));
        tileManager.initObjects(spriteList);
    }

    /**
     * Remove a sprite (TiledObject) from the level
     *
     * @param sprite the TiledObject (sprite) to be removed
     */
    public void removeSprite(TiledObject sprite) {
        tileManager.removeFromRows(sprite);
    }

    /**
     * Initialises the sprite batch (which contains all character sprites) and adds
     * the player/enemy characters to it.
     * Called by LibGDX when the screen is set as active by the game director.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        // Add background
        backgroundSprite = new Sprite(backgroundTexture);

        System.out.println(
                "Texture dimensions: h:" + backgroundTexture.getHeight() + " w:" + backgroundTexture.getWidth());

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    /**
     * Sets the current view of the screen, drawing sprites on the screen based on
     * their current position; also
     * calls the player collision check system to check for any collisions since the
     * previous render.
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
        Vector3 cam = director.getCameraPos();
        director.updateCameraPos(player.getX(), player.getY());

        batch.begin();

        // Draw the background
        batch.draw(backgroundSprite.getTexture(), 0, 0);

        // For each TiledObject initialised in Tile Manager, call its drawOn to draw it with sprite batch
        for (ArrayList<TiledObject> toArray : tileManager.getRowArray()) {
            for (TiledObject to : toArray) {
                if (to!=null) to.drawOn(batch, delta);
            }
        }

        String scoreText = String.format("score: %d", score.getScore());
        String parcelText = String.format("parcels remaining: %d", parcels);
        String healthText = String.format("health: %d", player.getHealth());

        font.setColor(Color.CORAL);
        font.draw(batch, scoreText, (cam.x - 900), 1040);
        font.draw(batch, parcelText, (cam.x - 900), 1020);
        font.draw(batch, healthText, (cam.x - 900), 1000);

        // if one is set, display error message
        if (this.errorCountdown > 0 && this.errorText != null && this.errorText.length() != 0) {
            font.draw(batch, this.errorText, 25, 980);

            errorCountdown -= 1 * delta; // decrement error countdown by seconds passed in prev render
        }

        batch.end();
    }

    /**
     * Show an error message to the user
     *
     * @param error the error message
     */
    public void showErrorText(String error) {
        this.errorText = error;
        // set for error to display for 5 seconds
        this.errorCountdown = 5;
    }

    /**
     * Get the level's TileManager instance
     *
     * @return the level's TileManager instance
     */
    public TileManager getTileManager() {
        return tileManager;
    }

    /**
     * Get the level's Houses
     *
     * @return an ArrayList containing all the level's Houses
     */
    public ArrayList<House> getHouses()
    {
        ArrayList<House> houses = new ArrayList<House>();

        for (ArrayList<TiledObject> sprites : tileManager.getRowArray()) {
            for (TiledObject sprite : sprites) {
                if (sprite instanceof House) {
                    houses.add((House) sprite);
                }
            }
        }

        return houses;
    }

    /**
     * Get the level's player van
     *
     * @return the level's PlayerVan
     */
    public ParcelVan getParcelVan() {
        return parcelVan;
    }

    /**
     * Get the number of parcels remaining to be collected in the level
     *
     * @return number of parcels remaining in level
     */
    public int getParcels() {
        return parcels;
    }

    /**
     * Decrement the parcels count (number of parcels remaining to be collected in the level)
     */
    public void decrementParcels() {
        parcels--;
    }

    /**
     * Add to the score points count
     *
     * @param points the number of points to add
     */
    public void addToScore(int points) {
        score.addToScore(points);
    }

    /**
     * Deal with a user's key press (initiate movement/attacking, go to pause menu
     * etc.).
     * Part of ApplicationListener implementation.
     *
     * @param keycode the pressed key
     * @return if the key press was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("key pressed: " + keycode);

        /**
         * Movement calculated by:
         * -> Adding values defined above to deltaMove vector
         * -> Every time move() is called, deltaMove is added to position vector
         * -> This allows for diagonal movement
         */
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT || keycode == Input.Keys.D ||
                keycode == Input.Keys.RIGHT || keycode == Input.Keys.W ||
                keycode == Input.Keys.UP || keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move player
            System.out.println("Moving");
            player.handleMovement(keycode);
        } else if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            // Pause play
            pause();
        } else if (keycode == Input.Keys.SPACE) {
            // Launch attack
            player.launchAttack();
        }
        else if (keycode == Input.Keys.TAB) {
            // Open parcel (if any)
            try {
                player.openParcel();
            } catch (ParcelException ex) {
                showErrorText(ex.getMessage());
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Deal with a key press being lifted.
     * Part of ApplicationListener implementation.
     *
     * @param keycode the key lifted
     * @return if the key lift was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        player.stopMovement();

        return true;
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
    public void resize(int width, int height) {}

    @Override
    public void pause() {
        try {
            this.paused = true;
            director.switchScreen("pausemenu");
        } catch (Exception ex) {
            Gdx.app.error("Exception", "Error switching screen to pause menu", ex);
            System.exit(-1);
        }
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(this);
        this.paused = false;
    }

    /**
     * End the level (called when Player is destroyed)
     */
    public void end() {
        try {
            director.switchScreen("levelfailed");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level failed", ex);
            System.exit(-1);
        }
    }

    /**
     * Complete the level (called when the level is complete i.e. the final parcel is delivered)
     */
    public void complete() {
        score.saveScore();

        try {
            director.switchScreen("levelcomplete");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level complete", ex);
            System.exit(-1);
        }
    }

    /**
     * Sets the input processor to null to prevent the Player's application listener
     * from listening to user
     * inputs; then calls dispose() to remove the screen's assets from memory.
     * Called by LibGDX when the screen is made inactive.
     */
    @Override
    public void hide() {
       Gdx.input.setInputProcessor(null);

       if (!paused) {
           dispose();
       }
    }

    /**
     * Removes the screen's assets (background texture, sprite batch and moving sprites) from memory
     * when the screen is made inactive and they're therefore no longer needed.
     * Called by hide().
     */
    @Override
    public void dispose() {
        if (paused) {
            return;
        }

        backgroundTexture.dispose();
        batch.dispose();
        font.dispose();

        // dispose of Tile Manager & its sprites (to dispose their texture/texture atlas)
        tileManager.dispose();
    }
}
