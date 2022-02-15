package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.exceptions.TileException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;

/**
 * Class representing a house
 */
public class House extends GameObject {
    private Texture texture;

    private boolean isDeliveryLocation = false;
    private Tile tile;

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
     * Mark the House as the delivery location for a level's current Parcel.
     * Defines the nearest Tile to the House's screen coordinates as its delivery location.
     *
     * @param level the level the House is to be a delivery location in
     */
    public void markAsDeliveryLocation(LevelScreen level) {
        // get tile nearest to House coordinates and mark as House's delivery tile
        try {
            this.tile = level.getTileManager().getNearestTileToCoords(getX(), getY());
        } catch (TileException ex) {
            Gdx.app.error("Exception", "Error trying to mark House as delivery location", ex);
            System.exit(-1);
        }
        this.tile.setOwned(this);

        this.isDeliveryLocation = true;
    }

    /**
     * Un-mark the House as the delivery location for a level
     */
    public void unmarkAsDeliveryLocation() {
        this.tile.setOwned(null);
        this.tile = null;

        this.isDeliveryLocation = false;
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