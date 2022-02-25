package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;

public class ParcelVan extends StaticSprite {
    private static final String PNG = "finished_assets/static_sprites/van.png";
    private static final String W_PARCELS_PNG = "finished_assets/static_sprites/vanWParcels.png";

    private static final int HEIGHT = 1; // the height of the parcel van sprite in Tiles
    private static final int WIDTH = 7; // the width of the parcel van sprite in Tiles

    private TileIndicator tileIndicator;

    /**
     * Create a new ParcelVan (StaticSprite)
     *
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public ParcelVan(int x, int y) {
        super(W_PARCELS_PNG, x, y, HEIGHT, WIDTH);
    }

    /**
     * Set the Parcel Van to have tile indicator.
     * A Parcel Van is active when the Player doesn't have a Parcel and has some left to collect.
     */
    public void setActive() {
        if (this.tileIndicator == null) {
            int[] tilePosition = this.getCurrentTiles().get((WIDTH -1) / 2).getPositionTile(); // get middle Tile
            this.tileIndicator = new TileIndicator(tilePosition[0], tilePosition[1]);
            // add tile indicator to Tile
            ArrayList<TileIndicator> tileIndicatorArrayList = new ArrayList<>(Arrays.asList(this.tileIndicator));
            getTileManager().initObjects(tileIndicatorArrayList);
        } else {
            System.out.println("Placing arrow over tile");
            getTileManager().placeObject(this.tileIndicator, this.tileIndicator.getCurrentTiles());
        }


    }

    /**
     * Set the Parcel Van to not have tile indicator.
     * A Parcel Van is inactive when the Player has a Parcel or has none left to collect.
     */
    public void setInactive() {
        // remove tile indicator from Tile
        getTileManager().removeFromRows(this.tileIndicator);
    }

    /**
     * Update the ParcelVan's texture to be empty (with no parcels)
     */
    public void setToEmpty() {
        setTexture(new Texture(PNG));
    }
}
