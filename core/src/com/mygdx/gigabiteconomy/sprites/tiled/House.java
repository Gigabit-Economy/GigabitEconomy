package com.mygdx.gigabiteconomy.sprites.tiled;

import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;

import java.util.ArrayList;
import java.util.Arrays;

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
     */
    public void markAsDeliveryLocation() {
        // if not yet defined, set delivery tile to tile covering door
        if (this.deliveryTile == null) {
            this.deliveryTile = getCurrentTiles().get(DOOR_INDEX);
        }
        // if not yet defined, set delivery tile indicator to match coordinates of delivery tile
        if (this.deliveryTileIndicator == null) {
            int[] tilePosition = this.deliveryTile.getPositionTile();
            this.deliveryTileIndicator = new TileIndicator(tilePosition[0], tilePosition[1]);
        }

        // get delivery tile to be owned by House (so it's deliverable to)
        this.deliveryTile.setOccupied(null);
        this.deliveryTile.setOwned(this);

        // add indicator to Tile
        ArrayList<TileIndicator> deliveryTileIndicatorArrayList = new ArrayList<>(Arrays.asList(this.deliveryTileIndicator));
        getTileManager().initObjects(deliveryTileIndicatorArrayList);
    }

    /**
     * Un-mark the House as the delivery location for a level
     */
    public void unmarkAsDeliveryLocation() {
        if (this.deliveryTile != null) {
            this.deliveryTile.setOccupied(this);
            this.deliveryTile.setOwned(null);
        }

        if (this.deliveryTileIndicator != null) {
            getTileManager().removeFromRows(deliveryTileIndicator);
        }
    }

    public enum HouseType {
        DETACHED,
        TWO_STORY
    }
}