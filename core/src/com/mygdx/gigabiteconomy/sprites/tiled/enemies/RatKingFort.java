package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.LinkedList;

/**
 * Rat King Fort enemy sprite (in level 3)
 */
public class RatKingFort extends Enemy {
    private static final String BASE_PATH = "ratking";
    private static final float DEFAULT_HEALTH = 400f;
    private static final float DEFAULT_DELTAHORIZ = 2f;
    private static final float DEFAULT_DELTAVERT = 1.5f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 9;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 10;

    private RatKing overlord;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     */
    public RatKingFort(int x, int y, Player targetEntity, RatKing overlord) {
        super(BASE_PATH, Weapon.BOXFORT, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, 2f, 1.5f, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, new LinkedList<MovingSprite.DIRECTION>());
        this.overlord = overlord;
        overlord.setParcelFort(this);
    }

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     * @param movementPath define a specific movement path
     */
    public RatKingFort(int x, int y, Player targetEntity, RatKing overlord, float deltaHoriz, float deltaVert, float health, LinkedList<MovingSprite.DIRECTION> movementPath) {
        super(BASE_PATH, MovingSprite.Weapon.BAT, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, deltaHoriz, deltaVert, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, health, movementPath);
        this.overlord = overlord;
    }

    @Override
    public void setHealth(float health) {
        if ((Math.abs(health-(DEFAULT_HEALTH/2)) < 10) || (Math.abs(health-(DEFAULT_HEALTH/3)) < 10)) {
            //Ask rat king to spawn a minion
            overlord.underAttack();


        } else if (health <= 0) {
            for (int i=0; i<3; i++) overlord.underAttack();
        }

        super.setHealth(health);
    }

    @Override
    public void destroy() {
        overlord.setParcelFort(null);
        super.destroy();
    }
}
