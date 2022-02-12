package com.mygdx.gigabiteconomy.sprites.tiled;

import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;

/**
 * Class representing an enemy sprite (many per level)
 */
public class Enemy extends MovingSprite {
    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param movementConfig path of texture atlas movement config file (.txt)
     * @param attackingConfig path of texture atlas attacking config file (.txt)
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public Enemy(String movementConfig, String attackingConfig, int x, int y) {
        super(movementConfig, attackingConfig, x, y);
    }
}
