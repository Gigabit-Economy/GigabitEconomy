package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class RatKing extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/level1";
    private static final float DEFAULT_HEALTH = 65f;
    private static final float DEFAULT_DELTAHORIZ = 5f;
    private static final float DEFAULT_DELTAVERT = 4f;
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 5;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 5;

    private LevelScreen level;


    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     */
    public RatKing(int x, int y, Player targetEntity) {

        super(BASE_PATH, Weapon.BAT, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, new LinkedList<DIRECTION>(
                Arrays.asList(
                        DIRECTION.WEST, DIRECTION.WEST,
                        DIRECTION.EAST, DIRECTION.EAST
                )

        ));
    }

    /**
     * Rat king needs power to spawn in enemies
     * @param level
     */
    public void setLevel(LevelScreen level) {
        this.level = level;
    }

    /**
     * Defines what the Rat King does when Player agros
     * -> If box fort not destroyed:
     *      -> Spawn enemy (parcel) with direction
     */
    @Override
    public void agro_action() {

        /**
         * Call level to spawn an enemy at random y
         */

    }

    public void underAttack(int y) {
        //Spawn a minion in level
        System.out.println("Under attack at " + y);
        level.addEnemies(new ArrayList<Enemy>(Arrays.asList(
                new BatGuy(34, y, this.getTargetEntity(), 6f, 1.5f, 65f, new LinkedList<>(Arrays.asList(DIRECTION.WEST, DIRECTION.WEST)))
        )));
    }
}
