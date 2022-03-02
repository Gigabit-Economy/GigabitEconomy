package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class Fighter extends Enemy {
    private static final float DEFAULT_HEALTH = 85f;
    private static final float DEFAULT_DELTAHORIZ = 2f;
    private static final float DEFAULT_DELTAVERT = 1.5f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 5;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param targetEntity
     */
    public Fighter(int x, int y, String level, Player targetEntity) {
        super(level, MovingSprite.Weapon.NONE, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, new LinkedList<>(
            Arrays.asList(MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.EAST,
                    MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.EAST,
                    MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.WEST,
                    MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.WEST)
        ));
    }

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param movementPath define a specific movement path
     * @param targetEntity
     */
    public Fighter(int x, int y, String level, Player targetEntity, float deltaHoriz, float deltaVert, float health, LinkedList<MovingSprite.DIRECTION> movementPath) {
        super(level, MovingSprite.Weapon.NONE, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, deltaHoriz, deltaVert, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, health, movementPath);
    }
}
