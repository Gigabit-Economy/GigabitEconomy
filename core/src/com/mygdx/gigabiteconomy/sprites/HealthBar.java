package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class HealthBar extends GameObject/* implements GameObject for getX() and getY() */ {

    public ShapeRenderer renderer;

    /**
     * Constructor to create a new GameObject (sets the x and y coordinates for subclasses).
     *
     * @param x the game object's X coordinate
     * @param y the game object's Y coordinate
     */
    public HealthBar(float x, float y) {
        super(x, y);
    }

    public abstract void drawOn(SpriteBatch batch);


}
