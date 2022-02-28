package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class HealthBar {
    private ShapeRenderer ellipse;

    private float[] dimensions = new float[2];
    private final float INIT_HEIGHT;
    private final float INIT_WIDTH;

    public HealthBar(float height, float width) {
        dimensions[0] = width; dimensions[1] = height;
        ellipse = new ShapeRenderer();

        INIT_HEIGHT = height;
        INIT_WIDTH = width;
    }

    public abstract void drawOn(SpriteBatch batch);

    /**
     * Set the value of the health bar
     *
     * @param health the new health value
     */
    public void setHealth(float health) {
        System.out.println(health);
        float newHealth = (health * (INIT_WIDTH/100));

        if (newHealth <= 0) {
            dimensions[0] = 0;
        }
        else {
            dimensions[0] = newHealth;
        }
    }

    /**
     * Get the ellipse
     *
     * @return the ellipse
     */
    public ShapeRenderer getEllipse() {
        return ellipse;
    }

    /**
     * Get dimensions (x/y) of the health bar
     *
     * @return x and y dimension coordinates
     */
    public float[] getDimensions() {
        return dimensions;
    }
}
