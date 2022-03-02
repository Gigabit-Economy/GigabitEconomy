package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.LinkedList;

public class ThrowingParcel extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";
    private static final float DEFAULT_HEALTH = 1f;
    private static final float DEFAULT_DELTAHORIZ = 2f;
    private static final float DEFAULT_DELTAVERT = 1.5f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 5;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param BASE_PATH      of texture
     * @param weapon         the weapon the Enemy is carrying
     * @param x              position of Tile (within tile grid) to place sprite
     * @param y              position of Tile (within tile grid) to place sprite
     * @param height         of Tiles to occupy
     * @param width          of Tiles to occupy
     * @param targetEntity
     * @param deltaHoriz     horizontal speed
     * @param deltaVert      vertical speed
     * @param horizAgroTiles
     * @param vertAgroTiles
     * @param health
     * @param movePath
     */
    public ThrowingParcel(int x, int y, Player targetEntity, int height, int width) {
        super(BASE_PATH, Weapon.KATANA, x, y, height, width, targetEntity, deltaHoriz, deltaVert, horizAgroTiles, vertAgroTiles, health, movePath);
    }

    @Override
    public void updateTextureRegions(DIRECTION directionFacing) {
        /**
         * Always facing WEST
         * Once comes in contact with anything it'll inflict damage
         */

    }
}
