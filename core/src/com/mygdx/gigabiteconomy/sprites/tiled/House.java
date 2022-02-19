package com.mygdx.gigabiteconomy.sprites.tiled;

import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;

/**
 * Class representing a house (where Parcels are delivered to by the Player)
 */
public class House extends StaticSprite {
    private static final int Y = 8; // Y coordinate of the highest row of Tiles in the tile grid
    private static final int DOOR_INDEX = 3; // the number of Tiles to the door area (from the left side of the House)

    private Tile deliveryTile;
    private TileIndicator deliveryTileIndicator;

    /**
     * Create a new House game object
     *
     * @param type the type of house (HouseType enum)
     * @param x position of Tile (within tile grid) to place sprite
     */
    public House(HouseType type, int x) {
        super(String.format("finished_assets/houses/%s.png", type.name().toLowerCase()), x, Y, 1, 6);
    }

    /**
     * Mark the House as the delivery location for a level's current Parcel.
     * Defines the nearest Tile to the House's screen coordinates as its delivery location.
     *
     * @param level the level the House is to be a delivery location in
     */
    public void markAsDeliveryLocation(LevelScreen level) {
        // if not yet defined, set delivery tile to tile covering door
        if (this.deliveryTile == null) {
            this.deliveryTile = getCurrentTiles().get(DOOR_INDEX);
        }
        // if not yet defined, set delivery tile indicator to match coordinates of delivery tile
        if (this.deliveryTileIndicator == null) {
            float[] tilePosition = this.deliveryTile.getTileCoords();
            this.deliveryTileIndicator = new TileIndicator((int) tilePosition[0], (int) tilePosition[1]);
        }

        // get delivery tile to be owned by House (so it's deliverable to)
        this.deliveryTile.setOccupied(null);
        this.deliveryTile.setOwned(this);

        level.addSprite(deliveryTileIndicator);
    }

    /**
     * Un-mark the House as the delivery location for a level
     *
     * @param level the level the House was a delivery location in (i.e. is to be removed from)
     */
    public void unmarkAsDeliveryLocation(LevelScreen level) {
        if (this.deliveryTile != null) {
            this.deliveryTile.setOccupied(this);
            this.deliveryTile.setOwned(null);
        }

        if (this.deliveryTileIndicator != null) {
            level.removeSprite(deliveryTileIndicator);
        }
    }

    public enum HouseType {
        DETACHED,
        TWO_STORY
    }
}