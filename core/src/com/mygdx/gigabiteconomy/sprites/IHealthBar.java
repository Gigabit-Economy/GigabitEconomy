package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IHealthBar {
    void drawOn(SpriteBatch batch);

    void modifyHealth(float dhealth);

}
