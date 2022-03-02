package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingAnimation;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;

import java.util.*;

public class RatKing extends Enemy {
    private static final String BASE_PATH = "finished_assets/enemies/ratking";
    private static final float DEFAULT_HEALTH = 200f;
    private static final float DEFAULT_DELTAHORIZ = 15f;
    private static final float DEFAULT_DELTAVERT = 4f;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
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
    public RatKing(int x, int y, Player targetEntity) {

        super(BASE_PATH, Weapon.RATKING, x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH, targetEntity, DEFAULT_DELTAHORIZ, DEFAULT_DELTAVERT, DEFAULT_HORIZAGROTILES, DEFAULT_VERTAGROTILES, DEFAULT_HEALTH, new LinkedList<DIRECTION>(
                Arrays.asList(
                        DIRECTION.WEST
                )

        ));
        initX = x;
        addPath("charge", new LinkedList<DIRECTION>(
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

        if (fort != null && !isAttacking()) {
            //Throw box
            int randy = rand.nextInt(100);
            if (randy < 10) {
                if (parcelFalling == null || parcelFalling.getOwnedTile().getOwnedBy() == null) {
                    parcelFalling = new FallingParcel(rand.nextInt(20) + 5, rand.nextInt(6) + 2); //Spawn relative to player location
                    level.addSprite(parcelFalling);

                }
            } else if (randy > 85) {
                if (tm.getTile(24, getCurrentTiles().get(0).getPositionTile()[1]).getOwnedBy() == null) {
                    ThrowingParcel throwingParcel = new ThrowingParcel(24, getCurrentTiles().get(0).getPositionTile()[1], getTargetEntity());
                    level.addEnemies(new ArrayList<Enemy>(Collections.singletonList(throwingParcel)));
                    throwingParcel.hideHealthBar();
                } else {
                    System.out.println("THIS Y IS OCCUPIED!");
                }
            }
            setAttacking(true);
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
                stunned.runAnimation(delta);
            }
            return true;
        }

        boolean ret = super.move(delta);

        if (!ret) return false;




        if (getCurrentTiles().get(0).getPositionTile()[0] == 0) {
            setPath(new LinkedList<>(
                    Arrays.asList(
                            DIRECTION.EAST, DIRECTION.EAST, DIRECTION.EAST, DIRECTION.EAST
                    )));
        } else if ((getCurrentTiles().get(0).getPositionTile()[0] > initX) && getPath().peek() == DIRECTION.EAST) {
            setPath("agro");
            System.out.println(initX);
            //stunned = new MovingAnimation<TextureRegion>("finished_assets/enemies/rat_king/stunned.txt");
        }


        return true;
    }

    public void underAttack(int y) {
        //Spawn a minion in level
        System.out.println("Under attack at " + y);
//        level.addEnemies(new ArrayList<Enemy>(Arrays.asList(
//                new BatGuy(24, (new Random()).nextInt(8), this.getTargetEntity(), 6f, 1.5f, 65f, new LinkedList<>(Arrays.asList(DIRECTION.WEST, DIRECTION.WEST)))
//        )));
    }
}
