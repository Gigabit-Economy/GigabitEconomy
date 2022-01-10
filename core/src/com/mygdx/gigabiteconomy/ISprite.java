package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Interface for controlling sprite
 */
public interface ISprite {

    /**
     * Set sprite to be moving or not
     * @param moving true: moving; false: still
     */
    void setMoving(boolean moving);
    boolean isMoving();

    /**
     * Method to change coords by values in delta coords
     * Switches image to next in cycle, defined by regions array
     */
    void move();

    /**
     * Method used for sprite movement
     * Increments current dcoords for multiple key presses at once
     * @param dx Change in x
     * @param dy Change in y
     */
    void setDCoords(int dx, int dy);

    /**
     * @return The current TextureRegion the animation is on
     */
    TextureRegion getCurrRegion();

    int getX();
    int getY();

    Rectangle getRectangle();
}
