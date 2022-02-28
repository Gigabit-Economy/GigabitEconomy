package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class Fighter extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";
    private static final float DEFAULT_HEALTH = 85f;
    private static final float DEFAULT_DELTAHORIZ = 2f;
    private static final float DEFAULT_DELTAVERT = 1.5f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param targetEntity
     */
    public Fighter(int x, int y, Player targetEntity) {
        super(BASE_PATH, Weapon.NONE, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HEALTH, new LinkedList<>(

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
    public Fighter(int x, int y, Player targetEntity, float deltaHoriz, float deltaVert, float health, LinkedList<MovingSprite.DIRECTION> movementPath) {
        super(BASE_PATH, MovingSprite.Weapon.NONE, x, y, 1, 1, targetEntity, deltaHoriz, deltaVert, health, movementPath);

    }
}
