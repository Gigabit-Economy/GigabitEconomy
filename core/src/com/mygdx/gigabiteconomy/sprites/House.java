package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;

import java.util.ArrayList;

/**
 * Class representing a house
 */
public class House extends GameObject {
    private LevelScreen level;
    private Texture texture;

    private Tile tile;

    private boolean isDeliveryLocation = false;

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

    /**
     * Set the level the House is in
     *
     * @param level the LevelScreen instance the House has been added to
     */
    public void setLevel(LevelScreen level) {
        this.level = level;
    }

    public void markAsDeliveryLocation() throws Exception {
        if (this.level == null) {
            throw new Exception("Attempted to mark a House with no assigned level as a delivery location");
        }

        // get tile nearest to House coordinates and mark as House's delivery tile
        this.tile = level.getTileManager().getNearestTileToCoords(getX(), getY());

        this.isDeliveryLocation = true;
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