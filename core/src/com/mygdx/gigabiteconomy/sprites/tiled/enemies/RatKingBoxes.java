package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class RatKingBoxes extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";
    private static final float DEFAULT_HEALTH = 65f;
    private static final float DEFAULT_DELTAHORIZ = 2f;
    private static final float DEFAULT_DELTAVERT = 1.5f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 5;

    private RatKing overlord;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     */
    public RatKingBoxes(int x, int y, Player targetEntity, RatKing overlord) {
        super(BASE_PATH, MovingSprite.Weapon.BAT, x, y, 1, 1, targetEntity, 2f, 1.5f, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, DIRECTION.randomPath(10));
        this.overlord = overlord;
    }

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param movementPath define a specific movement path
     */
    public RatKingBoxes(int x, int y, Player targetEntity, RatKing overlord, float deltaHoriz, float deltaVert, float health, LinkedList<MovingSprite.DIRECTION> movementPath) {
        super(BASE_PATH, MovingSprite.Weapon.BAT, x, y, 1, 1, targetEntity, deltaHoriz, deltaVert, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, health, movementPath);
        this.overlord = overlord;
    }

    @Override
    public void setHealth(float health) {
        if (health <= 0) {
            //Ask rat king to spawn a minion
            overlord.underAttack(getCurrentTiles().get(0).getPositionTile()[1]);
        }

        super.setHealth(health);
    }
}
