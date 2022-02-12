package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;

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
    public House(HouseType type, int x, int y) {
        super (x, y);

        texture = new Texture(String.format("finished_assets/houses/%s.png", type.name().toString().toLowerCase()));
    }

    public enum HouseType {
        DETACHED
    }
}