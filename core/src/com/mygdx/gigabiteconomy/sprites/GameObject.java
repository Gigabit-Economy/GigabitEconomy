package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gigabiteconomy.screens.TileManager;

import java.lang.Exception;

/**
 * Interface for controlling sprite
 */
public interface GameObject {
    TextureRegion getCurrRegion();

    float getActorX();
    float getActorY();

    void setActorX();
    void setActorY();

    void initTile(TileManager tm) throws Exception;
}
