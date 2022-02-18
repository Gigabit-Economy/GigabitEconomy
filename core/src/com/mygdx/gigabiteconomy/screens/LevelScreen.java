package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.ParcelException;
import com.mygdx.gigabiteconomy.exceptions.ScreenException;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
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

    private Stage stage;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private ArrayList<GameObject> sprites = new ArrayList<GameObject>(); // First sprite is ALWAYS player
    private SpriteBatch batch;
    private Player player;
    private ArrayList<TiledObject> enemies;
    private ArrayList<House> houses;
    private ParcelVan parcelVan;
    private ArrayList<TiledObject> staticSprites;

    private ScoreSystem score = new ScoreSystem();
    private int parcels = 5;

    private BitmapFont font;
    private String errorText;
    private float errorCountdown;

    /**
     * A template constructor for use by all level screen subclasses. Sets
     * properties that differ between levels
     * such as game director, player/enemy character sprites & background texture.
     *
     * @param director          the instance of the game director
     * @param player            the player character for the level
     * @param enemies           an ArrayList containing all enemy characters for the
     *                          level
     * @param houses            an ArrayList containing all houses for the level
     * @param staticSprites     an ArrayList containing all static sprites (such as
     *                          fences etc.) for the level
     * @param backgroundTexture the background graphic of the level
     */
    public LevelScreen(GigabitEconomy director, Player player, ArrayList<TiledObject> enemies, ArrayList<House> houses, ParcelVan parcelVan, ArrayList<TiledObject> staticSprites, Texture backgroundTexture) {
        this.director = director;
        this.player = player;
        this.houses = houses;
        this.enemies = enemies;
        this.parcelVan = parcelVan;
        this.staticSprites = staticSprites;
        this.backgroundTexture = backgroundTexture;

        // Create tile manager instance (stated variables explicitly here in case we
        // want to mess about with them)
        // -> Might need to move creating tileManager to other method (after we add
        // level switching)
        // -> One TileManager per level sounds like the cleanest option
        int backgroundTextureHeight = backgroundTexture.getHeight();
        int backgroundTextureWidth = backgroundTexture.getWidth();
        int numberOfTilesHigh = 18;
        tileManager = new TileManager(backgroundTextureHeight / numberOfTilesHigh, backgroundTextureHeight / 2,
                backgroundTextureWidth, 0, 0);

        // Initialise each sprite's position on tiles using the tile manager
        ArrayList<TiledObject> playerList = new ArrayList<TiledObject>(Arrays.asList(player, parcelVan));
        tileManager.initObjects(playerList, staticSprites, enemies); // in priority order
    }

    /**
     * Initialises the sprite batch (which contains all character sprites) and adds
     * the player/enemy characters to it.
     * Called by LibGDX when the screen is set as active by the game director.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        if (paused) {
            return;
        }

        // Add background
        backgroundSprite = new Sprite(backgroundTexture);
        System.out.println(
                "Texture dimensions: h:" + backgroundTexture.getHeight() + " w:" + backgroundTexture.getWidth());

        // Add houses
        sprites.addAll(houses);
        // Add static sprites
        sprites.addAll(staticSprites);
        // Add parcel van
        sprites.add(parcelVan);
        // Add player
        sprites.add(player);
        // Add enemies
        sprites.addAll(enemies);

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

        // stage.act(delta);
        // stage.draw();
        // Set screen's projection matrix to director's ortho camera
        batch.setProjectionMatrix(director.getCameraCombined());

        // This should only take place when player gets to a certain position in camera
        // view
        // update camera position to follow player
        director.updateCameraPos(player.getX(), player.getY());

        batch.begin();

        // Draw the background
        backgroundSprite.draw(batch);

        // Move (if moving sprite) & draw sprites
        for (GameObject sprite : sprites) {
            if (sprite instanceof House) {
                House house = (House) sprite;

                batch.draw(house.getTexture(), house.getX(), house.getY());
            }
            else if (sprite instanceof StaticSprite) {
                StaticSprite staticSprite = (StaticSprite) sprite;

                batch.draw(staticSprite.getTexture(), staticSprite.getX(), staticSprite.getY());
            }
            else if (sprite instanceof MovingSprite) {
                MovingSprite movingSprite = (MovingSprite) sprite;

                try {
                    movingSprite.move(delta);
                } catch (TileMovementException ex) {
                    // ignore exception (could act on it and display message to user etc. later)
                    System.out.println("Sprite was blocked");
                }

                batch.draw(movingSprite.getTextureRegion(), movingSprite.getX(), movingSprite.getY());
            }
        }

        String scoreText = String.format("score: %d", score.alterScore(0));
        String parcelText = String.format("parcels remaining: %d", parcels);
        String healthText = String.format("health: %d", player.getHealth());

        Vector3 cam = director.getCameraPos();

        font.setColor(Color.CORAL);
        font.draw(batch, scoreText, (cam.x - 900), 1040);
        font.draw(batch, parcelText, (cam.x - 900), 1020);
        font.draw(batch, healthText, (cam.x - 900), 1000);

        if (this.errorCountdown > 0 && this.errorText != null && this.errorText.length() != 0) {
            font.draw(batch, this.errorText, 25, 980);
            // decrement error countdown by seconds passed in prev render
            errorCountdown -= 1 * delta;
        }

        batch.end();
    }

    /**
     * Add a sprite to the level
     *
     * @param sprite the GameObject representing the sprite
     */
    public void addSprite(GameObject sprite) {
        sprites.add(sprite);
    }

    /**
     * Remove a sprite from the level
     *
     * @param sprite the GameObject representing the sprite
     */
    public void removeSprite(GameObject sprite) {
        sprites.remove(sprite);
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
        score.alterScore(points);
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
            director.switchScreen("levelFailed");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level failed", ex);
        }

        hide();
    }

    /**
     * Complete the level (called when the level is complete i.e. the final parcel is delivered)
     */
    public void complete() {
        score.submitScore();

        try {
            director.switchScreen("levelComplete");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level complete", ex);
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

        // dispose of moving sprites (to dispose their texture atlas)
        for (GameObject sprite : sprites) {
            sprite.dispose();
        }
    }
}
