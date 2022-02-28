package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class HealthBar {
    public ShapeRenderer renderer;

    public abstract void drawOn(SpriteBatch batch);
}
