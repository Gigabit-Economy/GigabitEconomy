package com.mygdx.gigabiteconomy.scenes;

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
public class Tile implements GameObject {

    //Maybe we can start representing positions as Vector2Ds instead of int[2]?
    //Might help with collisons later on if things with Tile is harder than expected... only issue is x/y is float
    private Vector2 position;
    private int sideLength;
    private boolean occupied; //Makes tile impassible
    private GameObject occupiedBy;

    /**
     *
     * @param x Float value x of bottom left of Tile
     * @param y Float value y of bottom left of Tile
     * @param sideLength
     */
    public Tile(float x, float y, int sideLength) {
        position = new Vector2(x, y);
        this.sideLength = sideLength;
    }

    @Override
    public void setMoving(boolean moving) {

    }

    @Override
    public float getActorX() {
        return position.x;
    }

    @Override
    public float getActorY() {
        return position.y;
    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    public void setOccupied(GameObject occupying) {
        this.occupiedBy = occupying;
    }

    public void removeOccupied() {
        this.occupiedBy = null;
    }
}
