package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.GameObject;

import java.lang.Exception;

/**
 * Interface for controlling sprite
 */
public abstract class TiledObject extends GameObject {
    private TileManager tileManager;

    private Tile currentTile;

    public TiledObject(float x, float y)
    {
        super(x, y);
    }

    public void initTile(TileManager tileManager) throws Exception {
        // Check if tile manager has already been initialised
        if (this.tileManager != null)
            throw new Exception("The tile manager has already been initialised");

        this.tileManager = tileManager;

        currentTile = tileManager.placeObject((int) getX(), (int) getY(), this);
        System.out.println(getX() + " " + getY());
        if (currentTile == null) {

            throw new Exception("THERE IS ALREADY A SPRITE IN THIS LOCATION");
        }

        System.out.println("Current tile coords: " + currentTile.getTileCoords()[0] + " " + currentTile.getTileCoords()[1]);

        setPos(currentTile.getTileCoords()[0], currentTile.getTileCoords()[1]);

        System.out.println("Initialised at " + getX() + " " + getY());
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
        currentTile = tile;
    }
}
