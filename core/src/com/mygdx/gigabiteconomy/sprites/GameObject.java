package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Interface for controlling sprite
 */
public interface GameObject {
    /**
     * @return The current TextureRegion the animation is on
     */
    TextureRegion getCurrRegion();

    int getActorX();
    int getActorY();

    Rectangle getRectangle();
}
