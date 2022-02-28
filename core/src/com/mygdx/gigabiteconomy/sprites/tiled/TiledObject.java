package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;

import java.util.ArrayList;

/**
 * Abstract class which all tiled sprites/game objects derive from.
 * Implements GameObject.
 */
public abstract class TiledObject implements Disposable {
    private TileManager tileManager;

    private ArrayList<Tile> currentTiles;
    private int height;
    private int width;

    private Vector2 pos; // Coordinates of sprite on screen

    public TiledObject(float x, float y, int height, int width) {
        this.pos = new Vector2(x, y);
        this.height = height <= 0 ? 1 : height; //Implement a max h/w?
        this.width = width <= 0 ? 1 : width;
    }

    /**
     * Get the Vector2 position of the sprite
     *
     * @return the current X coordinate of the sprite
     */
    public float getX()
    {
        return pos.x;
    }

    /**
     * Get the Vector2 position of the sprite
     *
     * @return the current U coordinate of the sprite
     */
    public float getY()
    {
        return pos.y;
    }

    /**
     * Set the Vector2 position of the sprite
     *
     * @param x the new X coordinate
     * @param y the new Y coordinate
     */
    public void setPos(float x, float y)
    {
        pos.x = x;
        pos.y = y;
    }

    /**
     * Add to the sprite's current Vector2 position.
     *
     * @param deltaMove the Vector 2 coordinates to move by
     */
    public void addToPos(Vector2 deltaMove)
    {
        pos.add(deltaMove);
    }

    /**
     * @return Height of Tiles occupied
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Width of Tiles occupied
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the instance of Tile Manager
     *
     * @return the TileManager instance
     */
    public TileManager getTileManager()
    {
        return tileManager;
    }

    /**
     * Set the instance of Tile Manager
     *
     * @param tileManager the TileManager instance
     */
    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    /**
     * Get the Tile(s) the sprite currently occupies
     *
     * @return the Tile instance(s) of the current tile
     */
    public ArrayList<Tile> getCurrentTiles() {
        return currentTiles;
    }

    /**
     * Abstract method defines how sprite is drawn
     * @param batch
     */
    public abstract void drawOn(SpriteBatch batch, float delta);

    /**
     * Set the Tile(s) the sprite the currently occupies
     * If currentTiles is already set, method occupies those to null before changing
     * @param currentTiles the new Tile(s) for the sprite to occupy
     */
    public void setCurrentTiles(ArrayList<Tile> currentTiles) {
        if (this.currentTiles != null) tileManager.placeObject(null, this.currentTiles);
        if (tileManager.placeObject(this, currentTiles) == null) {
            return;
        }

        this.currentTiles = currentTiles;

        setPos(currentTiles.get(0).getTileCoords()[0], currentTiles.get(0).getTileCoords()[1]);
    }
}
