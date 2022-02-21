package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.Texture;

public class ParcelVan extends StaticSprite {
    private static final String PNG = "finished_assets/static_sprites/van.png";
    private static final String W_PARCELS_PNG = "finished_assets/static_sprites/vanWParcels.png";

    private static final int HEIGHT = 1; // the height of the parcel van sprite in Tiles
    private static final int WIDTH = 7; // the width of the parcel van sprite in Tiles

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
     * Update the ParcelVan's texture to be empty (with no parcels)
     */
    public void setToEmpty() {
        setTexture(new Texture(PNG));
    }
}
