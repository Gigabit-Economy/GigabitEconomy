package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class Dog extends Enemy {

    private static final String BASE_PATH = "enemies/level1";
    private static final float DEFAULT_HEALTH = 50f;
    private static final float DEFAULT_DELTAHORIZ = 4f;
    private static final float DEFAULT_DELTAVERT = 3.25f;
    private static final int DEFAULT_WIDTH = 3;
    private static final int DEFAULT_HEIGHT = 1;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param targetEntity
     */
    public Dog(int x, int y, Player targetEntity) {
        super(BASE_PATH, Weapon.DOG, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HEALTH, new LinkedList<>(

                Arrays.asList(MovingSprite.DIRECTION.NORTH,MovingSprite.DIRECTION.NORTH,
                        MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.EAST,
                        MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.EAST,
                        MovingSprite.DIRECTION.SOUTH,MovingSprite.DIRECTION.SOUTH,
                        MovingSprite.DIRECTION.SOUTH,MovingSprite.DIRECTION.SOUTH,
                        MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.WEST,
                        MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.WEST,
                        MovingSprite.DIRECTION.NORTH,MovingSprite.DIRECTION.NORTH)
        ));
    }

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param movementPath define a specific movement path
     */
    public Dog(int x, int y, Player targetEntity, float deltaHoriz, float deltaVert, float health, LinkedList<DIRECTION> movementPath) {
        super(BASE_PATH, Weapon.DOG, x, y, 1, 1, targetEntity, deltaHoriz, deltaVert, health, movementPath);

    }
}
