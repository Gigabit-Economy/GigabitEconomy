package com.mygdx.gigabiteconomy.scenes;

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
public class Tile implements GameObject {

    //Might help with collisons later on if things with Tile is harder than expected... only issue is x/y is float
    private int[] position = new int[2];
    private int sideLength;
    private boolean occupied; //Makes tile impassible
    private GameObject occupiedBy;

    /**
     *
     * @param x Float value x of bottom left of Tile
     * @param y Float value y of bottom left of Tile
     * @param sideLength
     */
    public Tile(int x, int y, int sideLength) {
        position[0] = x; position[1] = y;
        this.sideLength = sideLength;
    }

    @Override
    public void setMoving(boolean moving) {

    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public void move() {

    }

    @Override
    public void setDCoords(int dx, int dy) {

    }

    @Override
    public TextureRegion getCurrRegion() {
        return null;
    }

    @Override
    public int getActorX() {
        return position[0];
    }

    @Override
    public int getActorY() {
        return position[1];
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
