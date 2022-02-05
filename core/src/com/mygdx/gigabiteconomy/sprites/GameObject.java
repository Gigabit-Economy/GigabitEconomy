package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gigabiteconomy.screens.TileManager;

/**
 * Interface for controlling sprite
 */
public interface GameObject {
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
