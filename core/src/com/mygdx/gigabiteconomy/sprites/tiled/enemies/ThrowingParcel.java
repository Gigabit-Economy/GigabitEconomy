package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingAnimation;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ThrowingParcel extends Enemy {
    private static final String BASE_PATH = "ratking";
    private static final float DEFAULT_HEALTH = 1f;
    private static final float DEFAULT_DELTAHORIZ = 9f;
    private static final float DEFAULT_DELTAVERT = 1.5f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 5;

    private MovingAnimation<TextureRegion> customMove;

    /**
     * Creates a new parcel for RatKing to throw
     */
    public ThrowingParcel(int x, int y, Player targetEntity) {
        super(BASE_PATH, Weapon.THROWBOX, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, new LinkedList<>(
                Arrays.asList(
                        DIRECTION.WEST, DIRECTION.WEST
                )
        ));
        setAgroPath(new LinkedList<>(
                Arrays.asList(
                        DIRECTION.WEST, DIRECTION.WEST
                )
        ));
    }

    @Override
    public void agro_action() {};

    @Override
    public void updateTextureRegions(DIRECTION directionFacing) {
        /**
         * Always facing WEST
         * Once comes in contact with anything it'll inflict damage
         * Only needs to run once
         */


        setMovementAnimation(1/14f, "enemies/ratking/throwbox.txt");
        setAttackAnimation(1/14f, "enemies/ratking/throwbox.txt");
    }

    @Override
    public boolean move(float delta) throws TileMovementException {

        Tile nextTile = getTileManager().getAdjacentTile(getCurrentTiles().get(0), DIRECTION.WEST, 1);

        if (nextTile == null) {
            setAttacking(false);
            destroy();
            return false;
        } else if (nextTile.getOccupiedBy() == getTargetEntity()) {
            launchAttack();
            destroy();
            return false;
        }
        setAttacking(false);

        return super.move(delta);
    }
}
