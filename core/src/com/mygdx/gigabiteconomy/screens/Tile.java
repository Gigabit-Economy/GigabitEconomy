package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

/**
 * Tile class
 * -> Contains:
 * --> Position (relative to texture)
 * --> Height/Width
 * --> Occupied boolean
 * --> Associated methods to provide encapsulation
 */
public class Tile {

    //Might help with collisons later on if things with Tile is harder than expected... only issue is x/y is float
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
     * Sets current Tile to occupied
     * @param occupying GameObject to occupy current Tile
     */
    public void setOccupied(TiledObject occupying) {
        this.occupiedBy = occupying;
    }

    /**
     * @return The position of the Tile on the game board
     */
    public int[] getPositionTile() {
        return positionTile;
    }

    /**
     * Method to return screen coordinates of given tile (from bottom left)
     * @param tileOccupied Tile to return coordinates of
     * @return float[2] of form [screen coord x, screen coord y]
     */
    public float[] getTileCoords() {
        int[] pos = this.getPositionTile().clone();
        float[] coords = new float[2];
//        coords[0] = (pos[0]*sideLength)+(sideLength/2); coords[1] = (pos[1]*sideLength)+(sideLength/2);
//        return coords;
        coords[0] = pos[0]*sideLength; coords[1] = pos[1]*sideLength;
        return coords;
    }

    /**
     * @return GameObject occupying current Tile
     */
    public TiledObject getOccupiedBy() {
        return occupiedBy;
    }

    public int getSideLength() {
        return sideLength;
    }

    /**
     * Method to check if sprite passed is within range to be placed in the centre of an object
     *
     * @return position of Tile on screen if success ; null if failure
     */
    public float[] isOnTile(float spriteX, float spriteY) {
        int offset = 5;
        if ((Math.abs(spriteX-position[0]) < offset) && (Math.abs(spriteY-position[1]) < offset)) {
            return position;
        }
        return null;
    }

}
