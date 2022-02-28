package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class BatGuy extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     */
    public BatGuy(int x, int y, Player targetEntity) {
        super(BASE_PATH, MovingSprite.Weapon.BAT, x, y, 1, 1, targetEntity, 2f, 1.5f, 3, 2, new LinkedList<>(
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
     */
    public BatGuy(int x, int y, Player targetEntity, float deltaHoriz, float deltaVert, int horizAgroTiles, int vertAgroTiles, LinkedList<MovingSprite.DIRECTION> movementPath) {
        super(BASE_PATH, MovingSprite.Weapon.BAT, x, y, 1, 1, targetEntity, deltaHoriz, deltaVert, horizAgroTiles, vertAgroTiles, movementPath);
    }
}
