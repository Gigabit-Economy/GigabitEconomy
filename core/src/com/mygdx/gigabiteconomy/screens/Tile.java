package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.sprites.tiled.Player;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

/**
 * Class representing a Tile on the player-accessible parts of level screen.
 * -> Contains:
 * --> Position (relative to texture)
 * --> Height/Width
 * --> Occupied boolean
 * --> Associated methods to provide encapsulation
 */
public class Tile {
    private float[] position = new float[2]; //x,y coords of tile on screen
    private int[] positionTile = new int[2]; //Holds relative position on screen
    private int sideLength;
    private boolean occupied; //Makes tile impassible
    private TiledObject ownedBy; //Owned by entity (for homeowners)
    private TiledObject occupiedBy; //Impassible or delivery spot!

    /**
     *
     * @param x Float value x of bottom left of Tile
     * @param y Float value y of bottom left of Tile
     * @param tileX Relative x position in tile grid
     * @param tileY Relative x position in tile grid
     * @param sideLength
     */
    public Tile(float x, float y, int sideLength, int tileX, int tileY) {
        positionTile[0] = tileX; positionTile[1] = tileY;
        position[0] = x; position[1] = y;
        this.sideLength = sideLength;
    }

    /**
     * @return The position of the Tile on the game board
     */
    public int[] getPositionTile() {
        return positionTile;
    }

    /**
     * Method to return screen coordinates of given tile (from bottom left)
     *
     * @return float[2] of form [screen coord x, screen coord y]
     */
    public float[] getTileCoords() {
        return position;
    }

    /**
     * @return GameObject occupying current Tile
     */
    public TiledObject getOccupiedBy() {
        return occupiedBy;
    }

    /**
     * Sets current Tile to occupied
     * @param occupying GameObject to occupy current Tile
     */
    public void setOccupied(TiledObject occupying) {
        this.occupiedBy = occupying;
    }

    /**
     * Get the GameObject which owns the Tile (if any; returns null if none)
     *
     * @return the GameObject owning the Tile
     */
    public TiledObject getOwnedBy() {
        return ownedBy;
    }

    /**
     * Set the owner of the Tile
     *
     * @param owner the owner of the Tile
     */
    public void setOwned(TiledObject owner) {
        this.ownedBy = owner;
    }

    /**
     * @return True if tile occupied by TiledObject ; False otherwise
     */
    public boolean isOccupiedBy(TiledObject to) {
        return getOccupiedBy() == to;
    }

    /**
     * Method to check if sprite passed is within range to be placed in the centre of an object
     *
     * @return true: Sprite is on Object
     */
    public boolean withinTile(TiledObject o) {
        return (Math.abs(o.getX()-getTileCoords()[0])<5) && (Math.abs(o.getY()-getTileCoords()[1])<5);
    }
}
