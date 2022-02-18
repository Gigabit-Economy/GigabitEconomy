package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.GameObject;

import java.lang.Exception;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Abstract class which all tiled sprites/game objects derive from.
 * Implements GameObject.
 */
public abstract class TiledObject extends GameObject {
    private TileManager tileManager;

    //private Tile currentTile;
    private ArrayList<Tile> currentTiles;
    private int height;
    private int width;

    public TiledObject(float x, float y, int height, int width) {
        super(x, y);

        this.height = height <= 0 ? 1 : height; //Implement a max h/w?
        this.width = width <= 0 ? 1 : width;
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
     * Set the Tile(s) the sprite the currently occupies
     *
     * @param currentTiles the new Tile(s) for the sprite to occupy
     */
    public void setCurrentTiles(ArrayList<Tile> currentTiles) {
        if (currentTiles.contains(null)) {
            return;
        }

        this.currentTiles = currentTiles;

        setPos(currentTiles.get(0).getTileCoords()[0], currentTiles.get(0).getTileCoords()[1]);
    }
}
