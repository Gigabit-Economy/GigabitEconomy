package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingAnimation;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.*;

public class RatKing extends Enemy {
    private static final float DEFAULT_HEALTH = 350f;
    private static final float DEFAULT_DELTAHORIZ = 20f;
    private static final float DEFAULT_DELTAVERT = 4f;
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 3;
    private static final int DEFAULT_VERTAGROTILES = 5;
    private static final int DEFAULT_HORIZAGROTILES = 20;

    private LevelScreen level;
    private RatKingFort fort;
    private int initX;

    private MovingAnimation stunned;

    private FallingParcel parcelFalling;

    private Random rand = new Random();

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param x            position of Tile (within tile grid) to place sprite
     * @param y            position of Tile (within tile grid) to place sprite
     */
    public RatKing(int x, int y, String level, Player targetEntity) {
        super(level, Weapon.RATKING, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, new LinkedList<DIRECTION>(
            Arrays.asList(
                DIRECTION.WEST
            )
        ));
        initX = x;
        addPath("charge", new LinkedList<>(
                Arrays.asList(
                        DIRECTION.WEST, DIRECTION.WEST,
                        DIRECTION.WEST, DIRECTION.WEST
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

    public void setParcelFort(RatKingFort fort) {
        this.fort = fort;
    }

    /**
     * Only runs when enemy reaches target tile, slow enemy down to slow attacks down
     *
     * Defines what the Rat King does when Player agros
     * -> If box fort not destroyed:
     *      -> Spawn enemy (parcel) with direction
     */
    @Override
    public void agro_action() {
        if (stunned != null) return;
        TileManager tm = getTileManager();

        /**
         * If the fort is still there, and RK is not already attacking, pick from attacks
         */
        if (fort != null && !isAttacking()) {
            //Throw box
            int randy = rand.nextInt(100);
            if (randy < 15) {
                if (parcelFalling == null || parcelFalling.getOwnedTile().getOwnedBy() == null) {
                    for (int i=0; i<6; i++) {
                        parcelFalling = new FallingParcel(rand.nextInt(20) + 5, rand.nextInt(6) + 2); //Spawn relative to player location
                        level.addSprite(parcelFalling);
                    }
                }
                setAttacking(true);
            } else if (randy > 85) {
                if (tm.getTile(24, getCurrentTiles().get(0).getPositionTile()[1]).getOwnedBy() == null) {
                    ThrowingParcel throwingParcel = new ThrowingParcel(24, getCurrentTiles().get(0).getPositionTile()[1], getTargetEntity());
                    level.addEnemies(new ArrayList<Enemy>(Collections.singletonList(throwingParcel)));
                    throwingParcel.hideHealthBar();

                } else {
                    System.out.println("THIS Y IS OCCUPIED!");
                }
                setAttacking(true);
            }

        /**
         * If fort has been destroyed, charge
         */
        } else {
            if ((getPath() != getPaths().get("charge") && getPath().peek() != DIRECTION.EAST)  && !(getCurrentTiles().get(0).getPositionTile()[0] < initX)) {
                setPath("charge");
            }
        }


//        if ((new Random()).nextInt(10)%2 == 0) {
//            //Charge
//            /**
//             * Set agro path to WEST WEST & deltaHoriz fucken high
//             */
//
//        } else {
//            //Throw
//        }

        /**
         * If rat fort remains throw parcel
         *
         * Otherwise either:
         *      -> Charge at the player, set agro path to WEST WEST & deltaHoriz fucken high
         *      --> If reached player:
         *              damage targetEntity
         *      --> return back to fort (initX)
         */


    }

    @Override
    public boolean move(float delta) throws TileMovementException {
        if (stunned != null) {
            if (stunned.isFinished(delta)) {
                stunned = null;
                //updateTextureRegions(getDirectionFacing());
            } else {
                setTextureRegion((TextureRegion) stunned.runAnimation(delta));
            }
            return true;
        }

        boolean ret = super.move(delta);
        if(isAttacking()) return false;

        /**
         * Make sure we've arrived at the tile and we're not blocked
         */
        if (!ret) return false;

        System.out.println(getTileManager().printOccupiedTiles());


        /**
         * Check if next tile is occupied by target entity
         */
        for (Tile t : getTileManager().getNextTiles(this, getDirectionMoving(), 1)) {
            if (t == null) continue;
            /**
             * Attack any MovingSprite in front
             */
            if (t.getOccupiedBy() instanceof MovingSprite && !(t.getOccupiedBy() instanceof RatKing)) {
                setAttacking(true);
            }
        }

        /**
         * If we're at end of map, turn around
         */
        if (getCurrentTiles().get(0).getPositionTile()[0] == 0) {
            setPath(new LinkedList<>(
                    Arrays.asList(
                            DIRECTION.EAST, DIRECTION.EAST, DIRECTION.EAST, DIRECTION.EAST
                    )));
        /**
         * If we're back at fort run stun
         */
        } else if ((getCurrentTiles().get(0).getPositionTile()[0] > initX) && getPath().peek() == DIRECTION.EAST) {
            setPath("agro");
            setMovementAnimation(1/8f, "enemies/ratking/dazed.txt");
            stunned = getMovementAnimation();
        }

        return true;
    }

    public void underAttack(int y) {
        //Spawn a minion in level
            level.addEnemies(new ArrayList<Enemy>(Arrays.asList(
            new Dog(22, (new Random()).nextInt(8), "level2", this.getTargetEntity(), 10f, 4.5f, 60f, DIRECTION.randomPath(20))
        )));
    }
}
