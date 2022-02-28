package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public abstract class HealthBar {
    private ShapeRenderer ellipse;

    private float[] dimensions = new float[2];

    public HealthBar(float width, float height) {
        dimensions[0] = width; dimensions[1] = height;
        ellipse = new ShapeRenderer();
    }

    public abstract void drawOn(SpriteBatch batch);

    /**
     * Set the value of the health bar
     *
     * @param health the new health value
     */
    public void setHealth(float health) {
        float newHealth = dimensions[0] - (health * (dimensions[0]/100));

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
