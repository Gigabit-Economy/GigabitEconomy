package com.mygdx.gigabiteconomy.sprites.coordinated;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gigabiteconomy.sprites.GameObject;

/**
 * Class to create a new enemy. Will have the ability to:
 * > Specify initial x, y
 * > Specify movement pattern (random about defined area or movement along radius of circle for the doggies)
 */
public class House extends GameObject {
    private Texture texture;

    /**
     * Constructor used to create a new house static sprite
     *
     * @param png the .png file of the sprite's texture
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public House(String png, int x, int y) {
        super (x, y);

        texture = new Texture(png);
    }
}