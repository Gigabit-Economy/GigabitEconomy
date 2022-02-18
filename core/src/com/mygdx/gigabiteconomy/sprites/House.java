package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.exceptions.TileException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.tiled.StaticSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.TileIndicator;

/**
 * Class representing a house
 */
public class House extends StaticSprite {
    private static final int HEIGHT = 5;

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
    }

    /**
     * Mark the House as the delivery location for a level's current Parcel.
     * Defines the nearest Tile to the House's screen coordinates as its delivery location.
     *
     * @param level the level the House is to be a delivery location in
     */
    public void markAsDeliveryLocation(LevelScreen level) {
        // get tile nearest to House coordinates and mark as House's delivery tile
        System.out.println(getCurrentTiles());
        this.deliveryTile = getCurrentTiles().get(2);
        this.deliveryTile.setOwned(this);

        float[] tilePosition = this.deliveryTile.getTileCoords();
        this.deliveryTileIndicator = new TileIndicator((int) tilePosition[0], (int) tilePosition[1]);
        level.addSprite(deliveryTileIndicator);
    }

    /**
     * Un-mark the House as the delivery location for a level
     */
    public void unmarkAsDeliveryLocation(LevelScreen level) {
        this.deliveryTile.setOwned(null);
        this.deliveryTile = null;

        level.removeSprite(deliveryTileIndicator);
        this.deliveryTileIndicator = null;
    }

    public enum HouseType {
        DETACHED,
        TWO_STORY
    }
}