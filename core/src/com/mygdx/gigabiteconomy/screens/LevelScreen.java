package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;
import com.mygdx.gigabiteconomy.sprites.*;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.StaticSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class which acts as a base class for all level screens.
 * Each individual level class extends this class and defines properties such as the player, enemies, houses &
 * static sprites etc.
 */
public abstract class LevelScreen implements Screen, InputProcessor {
    private GigabitEconomy director;
    private TileManager tileManager;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private ArrayList<GameObject> sprites = new ArrayList<GameObject>(); // First sprite is ALWAYS player
    private SpriteBatch batch;
    private Player player;
    private ArrayList<TiledObject> enemies;
    private ArrayList<House> houses;
    private ArrayList<GameObject> staticSprites;

    private int scoreCount, parcelCount, healthCount;
    private String scoreText, parcelText, healthText;
    private BitmapFont bitmapFont;

    /**
     * A template constructor for use by all level screen subclasses. Sets properties that differ between levels
     * such as game director, player/enemy character sprites & background texture.
     *
     * @param director the instance of the game director
     * @param player the player character for the level
     * @param enemies an ArrayList containing all enemy characters for the level
     * @param houses an ArrayList containing all houses for the level
     * @param staticSprites an ArrayList containing all static sprites (such as fences etc.) for the level
     * @param backgroundTexture the background graphic of the level
     */
    public LevelScreen(GigabitEconomy director, Player player, ArrayList<TiledObject> enemies, ArrayList<House> houses, ArrayList<GameObject> staticSprites, Texture backgroundTexture) {
        this.director = director;
        this.player = player;
        this.houses = houses;
        this.enemies = enemies;
        this.staticSprites = staticSprites;
        this.backgroundTexture = backgroundTexture;

        scoreCount = 0;
        parcelCount = 0;
        healthCount = 0;
        scoreText = "score: 0";
        parcelText = "no. parcels: 0";
        healthText = "health: 100 %"; 
        bitmapFont = new BitmapFont();

        // Create tile manager instance (stated variables explicitly here in case we want to mess about with them)
        //-> Might need to move creating tileManager to other method (after we add level switching)
        //-> One TileManager per level sounds like the cleanest option
        int backgroundTextureHeight = backgroundTexture.getHeight();
        int backgroundTextureWidth = backgroundTexture.getWidth();
        int numberOfTilesHigh = 18;
        tileManager = new TileManager(backgroundTextureHeight/numberOfTilesHigh, backgroundTextureHeight/2, backgroundTextureWidth, 0, 0);

        // Initialise each sprite's position on tiles using the tile manager
        ArrayList<TiledObject> playerList = new ArrayList<TiledObject>(Arrays.asList(player));
        tileManager.initObjects(playerList, enemies); // in priority order
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
        System.out.println("Texture dimensions: h:" + backgroundTexture.getHeight() + " w:" + backgroundTexture.getWidth());
        //tileManager = new TileManager(135, backgroundTexture.getHeight()/2, backgroundTexture.getWidth(), 0, 0);

        // Add static sprites
        sprites.addAll(staticSprites);

        // Add houses
        sprites.addAll(houses);

        // Add player
        sprites.add(player);
        Gdx.input.setInputProcessor(this);

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
        director.updateCameraPos(player.getX(), player.getY());

        batch.begin();

        // Draw the background
        backgroundSprite.draw(batch);
        
        // Move (if moving sprite) & draw sprites
        for (GameObject sprite : sprites) {
            if (sprite instanceof MovingSprite) {
                MovingSprite movingSprite = (MovingSprite) sprite;

                try {
                    movingSprite.move(delta);
                } catch (TileMovementException ex) {
                    // ignore exception (could act on it and display message to user etc. later)
                }

                batch.draw(movingSprite.getTextureRegion(), movingSprite.getX(), movingSprite.getY());
            } else if (sprite instanceof StaticSprite) {
                StaticSprite staticSprite = (StaticSprite) sprite;

                batch.draw(staticSprite.getTexture(), staticSprite.getX(), staticSprite.getY());
            }
        }

        bitmapFont.setColor(Color.CORAL);
        bitmapFont.draw(batch, scoreText, 25, 1040);
        bitmapFont.draw(batch, parcelText, 25, 1020);
        bitmapFont.draw(batch, healthText, 25, 1000);

        batch.end();
    }

    /**
     * Deal with a user's key press (initiate movement/attacking, go to pause menu etc.).
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
            player.handleMovement(keycode);

        } else if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            pause();
        } else if (keycode == Input.Keys.SPACE) {
            player.attack();
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
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        try {
            director.switchScreen("pausemenu");
        } catch (Exception ex) {
            Gdx.app.error("Exception", "Error switching screen to pause menu", ex);
            System.exit(-1);
        }
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Sets the input processor to null to prevent the Player's application listener from listening to user
     * inputs; then calls dispose() to remove the screen's assets from memory.
     * Called by LibGDX when the screen is made inactive.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    /**
     * Removes the screen's assets (background texture, sprite batch and moving sprites) from memory
     * when the screen is made inactive and they're therefore no longer needed.
     * Called by hide().
     */
    @Override
    public void dispose() {
        backgroundTexture.dispose();
        batch.dispose();

        // dispose of moving sprites (to dispose their texture atlas)
        for (GameObject sprite : sprites)
        {
            if (sprite instanceof MovingSprite) {
                ((MovingSprite) sprite).dispose();
            }
            else if (sprite instanceof StaticSprite) {
                ((StaticSprite) sprite).dispose();
            }
        }
    }
}
