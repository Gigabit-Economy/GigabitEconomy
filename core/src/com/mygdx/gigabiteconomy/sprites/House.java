package com.mygdx.gigabiteconomy.sprites;

import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.tiled.StaticSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.TileIndicator;

/**
 * Class representing a house
 */
public class House extends StaticSprite {
    private static final int HEIGHT = 9;
    private static final int DOOR_INDEX = 2;

    private Tile deliveryTile;
    private TileIndicator deliveryTileIndicator;

    /**
     * Create a new House game object
     *
     * @param type the type of house (HouseType enum)
     * @param x position of Tile (within tile grid) to place sprite
     */
    public House(HouseType type, int x) {
        super(String.format("finished_assets/houses/%s.png", type.name().toLowerCase()), x, HEIGHT, 1, 6);

        // set delivery tile to tile covering door
        this.deliveryTile = getCurrentTiles().get(DOOR_INDEX);
        // set delivery tile indicator to match coordinates of delivery tile
        float[] tilePosition = this.deliveryTile.getTileCoords();
        this.deliveryTileIndicator = new TileIndicator((int) tilePosition[0], (int) tilePosition[1]);
    }

    /**
     * Mark the House as the delivery location for a level's current Parcel.
     * Defines the nearest Tile to the House's screen coordinates as its delivery location.
     *
     * @param level the level the House is to be a delivery location in
     */
    public void markAsDeliveryLocation(LevelScreen level) {
        // get delivery tile to be owned by House (so it's deliverable to)
        this.deliveryTile.setOwned(this);

        level.addSprite(deliveryTileIndicator);
    }

    /**
     * Un-mark the House as the delivery location for a level
     */
    public void unmarkAsDeliveryLocation(LevelScreen level) {
        this.deliveryTile.setOwned(null);

        level.removeSprite(deliveryTileIndicator);
    }

    public enum HouseType {
        DETACHED,
        TWO_STORY
    }
}