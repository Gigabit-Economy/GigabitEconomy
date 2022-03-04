package com.mygdx.gigabiteconomy.screens.levels;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.ScoreSystem;
import com.mygdx.gigabiteconomy.exceptions.ParcelException;
import com.mygdx.gigabiteconomy.exceptions.ScreenException;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.RatKing;

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

    private String backgroundTexturePng;
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
     * @param director              the instance of the game director
     * @param backgroundTexturePng  the background graphic png of the level
     */
    public LevelScreen(GigabitEconomy director, String backgroundTexturePng) {
        this.director = director;

        this.backgroundTexturePng = backgroundTexturePng;
        // Add background
        this.backgroundTexture = new Texture(this.backgroundTexturePng);

        // Create Tile Manager for level
        int backgroundTextureHeight = backgroundTexture.getHeight();
        int backgroundTextureWidth = backgroundTexture.getWidth();
        int numberOfTilesHigh = 18;
        this.tileManager = new TileManager(backgroundTextureHeight / numberOfTilesHigh, backgroundTextureHeight / 2,
                backgroundTextureWidth, 0, 0);
    }

    /**
     * Get the game director (GigabitEconomy)
     *
     * @return the current game director class (GigabitEconomy) instance
     */
    public GigabitEconomy getDirector() {
        return this.director;
    }

    /**
     * Get the level's score system instance
     *
     * @return the level's score system instance
     */
    public ScoreSystem getScore() {
        return score;
    }

    /**
     * Add a Player to the level.
     * If level already has a Player, as a level can only have one, it'll be overridden.
     *
     * @param player the Player to be added to the level
     */
    public void addPlayer(Player player) {
        this.player = player;
        player.addHealthBar(director);
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
        for (Enemy enemy : enemies) {
            enemy.addHealthBar(director);
            if (enemy instanceof RatKing) {
                ((RatKing) enemy).setLevel(this);
            }
        }
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

        parcelVan.setActive();
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

        this.backgroundSprite = new Sprite(backgroundTexture);

        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
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

        // Add & draw score text in top-right corner
        String scoreText = String.format("Score: %d", score.getScore());
        font.getData().setScale(3, 2);
        font.setColor(Color.CORAL);
        font.draw(batch, scoreText, cam.x+(director.getViewport().getScreenWidth()/100*38), cam.y+(director.getViewport().getScreenHeight()/100*47));

        // if one is set, display error message
        if (this.errorCountdown > 0 && this.errorText != null && this.errorText.length() != 0) {
            font.draw(batch, this.errorText, cam.x+(director.getViewport().getScreenWidth()/100*38), cam.y+(director.getViewport().getScreenHeight()/100*47));

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
     * Set the score points count
     *
     * @param points the number of points to set the score to
     */
    public void setScore(int points) {
        score.setScore(points);
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
            player.handleMovement(keycode);
        } else if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            // Pause play
            pause();
        } else if (keycode == Input.Keys.SPACE) {
            // Launch attack
            player.setAttacking(true);
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
            director.switchScreen("PauseMenu");
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
            director.switchScreen("LevelFailed");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level failed", ex);
            System.exit(-1);
        }
    }

    /**
     * Complete the level (called when the level is complete i.e. the final parcel is delivered)
     */
    public void complete() {
        // save score
        score.saveScore();

        // switch to LevelComplete screen
        try {
            director.switchScreen("LevelComplete");
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

    }
}
