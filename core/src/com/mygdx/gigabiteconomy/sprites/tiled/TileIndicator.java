package com.mygdx.gigabiteconomy.sprites.tiled;

public class TileIndicator extends StaticSprite {
    private static final String PNG = "static_sprites/arrow.png";

    public TileIndicator(int x, int y) {
        super(PNG, x, y, 1, 1);

        // TODO: set Tile as unoccupied to allow sprites to access it
        //getCurrentTiles().get(0).setOccupied(null);
    }
}
