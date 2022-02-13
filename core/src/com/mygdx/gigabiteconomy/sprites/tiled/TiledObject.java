package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.GameObject;

import java.lang.Exception;

/**
 * Abstract class which all tiled sprites/game objects derive from.
 * Implements GameObject.
 */
public abstract class TiledObject extends GameObject {
    private TileManager tileManager;

    private Tile currentTile;

    public TiledObject(float x, float y)
    {
        super(x, y);
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
     * Get the tile the sprite currently occupies
     *
     * @return the Tile instance of the current tile
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Set the tile the sprite the currently occupies
     *
     * @param tile the new Tile instance for the sprite to occupy
     */
    public void setCurrentTile(Tile tile) {
        if (tile == null) {
            return;
        }

        currentTile = tile;

        setPos(currentTile.getTileCoords()[0], currentTile.getTileCoords()[1]);
    }
}
