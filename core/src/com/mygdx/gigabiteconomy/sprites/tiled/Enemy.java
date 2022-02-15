package com.mygdx.gigabiteconomy.sprites.tiled;

import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class representing an enemy sprite (many per level)
 */
public class Enemy extends MovingSprite {

    private Queue<DIRECTION> movePath;
    private Queue<DIRECTION> agroMovePath;
    private Tile pathBegin; //Bottom leftmost tile

    private HashMap<String, Queue<DIRECTION>> movementPaths = new HashMap<>(); //Allows n paths for n behaviours
    private Queue<DIRECTION> currentPath;

    int[][] agroDistance; //[[+x, -x], [+y, -y]] tiles to cause agro
    boolean agro = false;

    GameObject targetEntity;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param weapon the weapon the Enemy is carrying
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     * @param height of Tiles to occupy
     * @param width of Tiles to occupy
     */
    public Enemy(Weapon weapon, int x, int y, int height, int width) {
        super(weapon, x, y, height, width);

        movePath = new LinkedList<>();
        movePath.add(DIRECTION.NORTH);
        movePath.add(DIRECTION.EAST);
        movePath.add(DIRECTION.EAST);
        movePath.add(DIRECTION.EAST);
        movePath.add(DIRECTION.EAST);
        movePath.add(DIRECTION.SOUTH);
        movePath.add(DIRECTION.SOUTH);
        movePath.add(DIRECTION.SOUTH);
        movePath.add(DIRECTION.WEST);
        movePath.add(DIRECTION.WEST);
        movePath.add(DIRECTION.WEST);
        movePath.add(DIRECTION.WEST);
        movePath.add(DIRECTION.NORTH);
        movePath.add(DIRECTION.NORTH);

        agroMovePath = new LinkedList<>();
        for (int i=0; i<5; i++) agroMovePath.add(DIRECTION.NORTH);
        for (int i=0; i<5; i++) agroMovePath.add(DIRECTION.SOUTH);

        movementPaths.put("move", movePath);
        movementPaths.put("agro", agroMovePath);

        setPath("agro");

        setMoving(true);
    }


    /**
     * Method to set path of enemy from hashmap of defined paths
     * @param pathID Hashmap ID of path
     */
    public void setPath(String pathID) {
        if ((currentPath = movementPaths.get(pathID)) == null) {
            new Exception("Movement path does not exist");
        }

        setDirectionMovement(currentPath.peek());
    }

    /**
     * Method takes Player and sets agro if within the defined distance
     * @param player
     * @return
     */
    public boolean checkAgro(MovingSprite player) {
        /**
         * If player square position is within agroDistance (see TileManager methods)
         * -> Set agro to true
         * -> Set targetEntity to player
         * ::: In move method override: Agro = true => We run path finding and change pathSet to shortest path to player
         * Else return false
         *
         * Check whether Player is within agro square
         */
        targetEntity = player;
        Tile currTile = getCurrentTiles().get(0);
        Tile currPlayerTile = player.getCurrentTiles().get(0);

        /**
         * Both blocks below check for the same condition (that player is within agro square
         */

        /** */
        /** */
        /** */
        int i=0;
        for (DIRECTION dir : new DIRECTION[]{DIRECTION.EAST, DIRECTION.NORTH}) {
            agro |= (currPlayerTile.getPositionTile()[0] > getTileManager().getAdjacentTile(currTile, dir, agroDistance[i][0]).getPositionTile()[i]);
            agro |= (currPlayerTile.getPositionTile()[0] > getTileManager().getAdjacentTile(currTile, dir.getOpposite(), agroDistance[i][1]).getPositionTile()[i]);
            i++;
        }
        /** */
        /** */
        /** */
        if (
                (currPlayerTile.getPositionTile()[0] > getTileManager().getAdjacentTile(currTile, DIRECTION.EAST, agroDistance[0][0]).getPositionTile()[0])
            &&  (currPlayerTile.getPositionTile()[0] < getTileManager().getAdjacentTile(currTile, DIRECTION.WEST, agroDistance[0][1]).getPositionTile()[0])
            &&  (currPlayerTile.getPositionTile()[1] < getTileManager().getAdjacentTile(currTile, DIRECTION.NORTH, agroDistance[1][0]).getPositionTile()[1])
            &&  (currPlayerTile.getPositionTile()[1] < getTileManager().getAdjacentTile(currTile, DIRECTION.SOUTH, agroDistance[1][1]).getPositionTile()[1])
        ) {
            /** !!! AGRO !!! */
            agro = true;
        }
        /** */
        /** */
        /** */

        return false;
    }
    
    @Override
    public DIRECTION setNextDirection() {
        super.setDirectionMovement(currentPath.remove());
        currentPath.add(getDirectionMoving());
        return getDirectionMoving();
    }

    @Override
    public boolean move(float delta) throws TileMovementException {
        boolean ret = super.move(delta); //Checks if we've arrived else moved
//        if (ret) {
//            targetTile = tm.getAdjecentTile(currentTile, directionMoving.name(), 1);
//            setDeltaMove(directionMoving);
//            setMoving(true);
//        }

        if (ret) setMoving(false);

        /**
         * >>> ATTACKING LOGIC <<<
         * If ret == false (couldn't move to requested Tile) && any adjecent tile is owned by player
         * -> We're next to player
         * -> Set attacking mode
         * -> Call inflictDamage(float val); on player
         */

        /**
         * >>> AGRO LOGIC <<<
         * If agro is true
         * -> Find new path with TileManager: Queue<Tile> findShortestPathBetween(Tile from, Tile to);
         * -> Set new targetSquare from Queue (for next move();
         */
        //Must also check for distance condition
//        DIRECTION dirPlayerIn;
//        if (agro && ((dirPlayerIn = tm.findDirectionFrom(currentTile, targetEntity.getCurrentTile())) != null)) {
//            //Start agro path
//            directionMoving = dirPlayerIn;
//        } else {
//            //Resume agro path
//        }



        /**
         * If above is implemented right, enemy should be able to move in 'direction' towards 'targetTile'
         * with each move() call
         */


        return ret;
    }
}
