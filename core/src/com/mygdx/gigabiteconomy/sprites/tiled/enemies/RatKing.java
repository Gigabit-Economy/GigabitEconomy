package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class RatKing extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";
    private static final float DEFAULT_HEALTH = 65f;
    private static final float DEFAULT_DELTAHORIZ = 5f;
    private static final float DEFAULT_DELTAVERT = 4f;
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 5;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     */
    public RatKing(int x, int y, Player targetEntity) {

        super(BASE_PATH, Weapon.BAT, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HEALTH, new LinkedList<>(
                Arrays.asList(
                        MovingSprite.DIRECTION.NORTH, MovingSprite.DIRECTION.NORTH,
                        MovingSprite.DIRECTION.NORTH, MovingSprite.DIRECTION.NORTH,
                        MovingSprite.DIRECTION.NORTH, MovingSprite.DIRECTION.NORTH,
                        MovingSprite.DIRECTION.NORTH, MovingSprite.DIRECTION.NORTH,
                        MovingSprite.DIRECTION.SOUTH, MovingSprite.DIRECTION.SOUTH,
                        MovingSprite.DIRECTION.SOUTH, MovingSprite.DIRECTION.SOUTH,
                        MovingSprite.DIRECTION.SOUTH, MovingSprite.DIRECTION.SOUTH,
                        MovingSprite.DIRECTION.SOUTH, MovingSprite.DIRECTION.SOUTH
                )
        ));
    }
}
