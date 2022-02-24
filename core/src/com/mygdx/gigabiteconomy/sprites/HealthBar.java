package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class HealthBar extends GameObject {

    private GigabitEconomy director; // Might need this
    private ShapeRenderer healthRect;
    private Texture healthBarTexture;
    private int[] dimensions; // [ H , W ]

    public HealthBar() {
        super(50, 900);
        healthRect = new ShapeRenderer();
        dimensions = new int[]{318, 72}; // More specific values needed, size of health bar texture
        healthBarTexture = new Texture("finished_assets/ui_elements/health bar1.png");
    }

    @Override
    public void dispose() {

    }

    public void drawOn(SpriteBatch batch) {
        batch.end();

        healthRect.begin(ShapeRenderer.ShapeType.Filled);
        healthRect.setColor(Color.RED);
        healthRect.rect(getX()+100, getY()+39, dimensions[0], dimensions[1]);
        healthRect.end();

        batch.begin();
        batch.draw(healthBarTexture, getX(), getY());
    }

    public void modifyHeath(int dHealth) {
        dimensions[1]+=dHealth; //Needs to be as proportion
    }
}