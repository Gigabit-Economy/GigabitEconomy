package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class Dog extends Enemy {
    private static final float DEFAULT_HEALTH = 50f;
    private static final float DEFAULT_DELTAHORIZ = 4f;
    private static final float DEFAULT_DELTAVERT = 3.25f;
    private static final int DEFAULT_WIDTH = 3;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 5;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param level        the level of the Dog
     * @param targetEntity
     */
    public Dog(int x, int y, String level, Player targetEntity) {
        super(level, Weapon.DOG, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HORIZAGROTILES, DEFAULT_HORIZAGROTILES, DEFAULT_HEALTH, new LinkedList<>(

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
     * @param level        the level of the Dog
     * @param movementPath define a specific movement path
     */
    public Dog(int x, int y, String level, Player targetEntity, float deltaHoriz, float deltaVert, float health, LinkedList<DIRECTION> movementPath) {
        super(level, Weapon.DOG, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, deltaHoriz, deltaVert, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, health, movementPath);
    }

    @Override
    public void destroy() {
        if (getCurrentTiles() != null) getTileManager().purge(getCurrentTiles().get(0).getPositionTile()[1], this);
        if (getTargetTiles() != null) getTileManager().purge(getTargetTiles().get(0).getPositionTile()[1], this);
        super.destroy();
    }
}
