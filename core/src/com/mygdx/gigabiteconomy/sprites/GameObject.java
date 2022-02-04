package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gigabiteconomy.screens.TileManager;

/**
 * Interface for controlling sprite
 */
public interface GameObject {

    /**
     * Set sprite to be moving or not
     * @param moving true: moving; false: still
     */
    void setMoving(boolean moving);
    //boolean isMoving();

    /**
     * Method to change coords by values in delta coords
     * Switches image to next in cycle, defined by regions array
     */
    void move(float delta);

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

    float getActorX();
    float getActorY();

    void setActorX();
    void setActorY();

    int initTile(TileManager tm); //Adds all sprites to starting tiles

    Rectangle getRectangle();
}
