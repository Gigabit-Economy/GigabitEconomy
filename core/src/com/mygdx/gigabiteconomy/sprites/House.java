package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.exceptions.TileException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.tiled.TileIndicator;

/**
 * Class representing a house
 */
public class House extends GameObject {
    private static final int DOOR_WIDTH = 268;

    private Texture texture;

    private boolean isDeliveryLocation = false;

    private Tile tile;
    private TileIndicator tileIndiactor;

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
            this.tile = level.getTileManager().getNearestTileToCoords(getX() + DOOR_WIDTH, getY());
        } catch (TileException ex) {
            Gdx.app.error("Exception", "Error trying to mark House as delivery location", ex);
            System.exit(-1);
        }
        this.tile.setOwned(this);

        float[] tilePosition = this.tile.getTileCoords();
        this.tileIndiactor = new TileIndicator((int) tilePosition[0], (int) tilePosition[1]);
        level.addSprite(tileIndiactor);

        this.isDeliveryLocation = true;
    }

    /**
     * Un-mark the House as the delivery location for a level
     */
    public void unmarkAsDeliveryLocation(LevelScreen level) {
        this.tile.setOwned(null);
        this.tile = null;

        level.removeSprite(tileIndiactor);
        this.tileIndiactor = null;

        this.isDeliveryLocation = false;
    }

    /**
     * Get the House's Texture (a single png)
     *
     * @return the House's Texture
     */
    public Texture getTexture() {
        return texture;
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