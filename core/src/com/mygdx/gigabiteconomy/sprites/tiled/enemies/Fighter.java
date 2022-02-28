package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class Fighter extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param targetEntity
     */
    public Fighter(int x, int y, Player targetEntity) {
        super(BASE_PATH, MovingSprite.Weapon.NONE, x, y, 1, 1, targetEntity, 2f, 1.5f, 6, 2, DIRECTION.randomPath(6));
    }

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param movementPath define a specific movement path
     * @param targetEntity
     */
    public Fighter(int x, int y, Player targetEntity, float deltaHoriz, float deltaVert, int horizAgroTiles, int vertAgroTiles, LinkedList<MovingSprite.DIRECTION> movementPath) {
        super(BASE_PATH, MovingSprite.Weapon.NONE, x, y, 1, 1, targetEntity, deltaHoriz, deltaVert, horizAgroTiles, vertAgroTiles, movementPath);
    }
}
