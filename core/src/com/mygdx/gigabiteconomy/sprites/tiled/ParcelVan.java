package com.mygdx.gigabiteconomy.sprites.tiled;

public class ParcelVan extends StaticSprite {
    private static final String PNG = "finished_assets/static_sprites/van.png";
    private static final String W_PARCELS_PNG = "finished_assets/static_sprites/vanWParcels.png";

    /**
     * Create a new ParcelVan (StaticSprite)
     *
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public ParcelVan(int x, int y) {
        super(W_PARCELS_PNG, x, y);
    }
}
