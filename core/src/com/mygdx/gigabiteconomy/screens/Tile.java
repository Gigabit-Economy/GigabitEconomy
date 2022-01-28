package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
    private int[] position = new int[2]; //x,y coords of tile on screen
    private int[] positionTile = new int[2];
    private int sideLength;
    private boolean occupied; //Makes tile impassible
    private GameObject ownedBy; //Owned by entity (for homeowners)
    private GameObject occupiedBy; //Impassible or delivery spot!

    /**
     *
     * @param x Float value x of bottom left of Tile
     * @param y Float value y of bottom left of Tile
     * @param sideLength
     */
    public Tile(int x, int y, int sideLength, int tileX, int tileY) {
        positionTile[0] = tileX; positionTile[1] = tileY;
        position[0] = x; position[1] = y;
        this.sideLength = sideLength;
    }

    /**
     * Sets current Tile to occupied
     * @param occupying GameObject to occupy current Tile
     */
    public void setOccupied(GameObject occupying) {
        this.occupiedBy = occupying;
    }

    /**
     * @return The position of the Tile on the game board
     */
    public int[] getPositionTile() {
        return positionTile;
    }

    /**
     * @return GameObject occupying current Tile
     */
    public GameObject getOccupiedBy() {
        return occupiedBy;
    }

}
