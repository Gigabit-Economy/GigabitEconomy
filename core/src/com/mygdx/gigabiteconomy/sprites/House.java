package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class representing a house
 */
public class House extends GameObject {
    private Texture texture;

    /**
     * Create a new House game object
     *
     * @param type the type of house (HouseType enum)
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public House(HouseType type, int x, int y) {
        super (x, y);

        texture = new Texture(String.format("finished_assets/houses/%s.png", type.name().toLowerCase()));
    }

    public enum HouseType {
        DETACHED,
        TWO_STORY
    }

    /**
     * Remove the House's texture from memory once the sprite is no longer needed
     */
    public void dispose() {
        texture.dispose();
    }
}