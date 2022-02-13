package com.mygdx.gigabiteconomy.sprites;

import com.mygdx.gigabiteconomy.screens.Tile;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to create a new enemy. Will have the ability to:
 * > Specify initial x, y
 * > Specify movement pattern (random about defined area or movement along radius of circle for the doggies)
 */
public class Enemy extends MovingSprite {

    private Queue<DIRECTION> movePath;
    private Queue<DIRECTION> agroMovePath;
    private Tile pathBegin = currentTile;

    private HashMap<String, Queue<DIRECTION>> movementPaths = new HashMap<>(); //Allows n paths for n behaviours
    private Queue<DIRECTION> currentPath;

    int[] agroDistance; //[dx, dy] tiles to cause agro
    boolean agro = false;

    GameObject targetEntity;



    public Enemy(String move_config, String attack_config, int x, int y) {
        super(move_config, attack_config, x, y);
        movePath = new LinkedList<>();
        movePath.add(DIRECTION.NORTH);
        movePath.add(DIRECTION.EAST);
        movePath.add(DIRECTION.SOUTH);
        movePath.add(DIRECTION.WEST);

        setDirectionMovement(movePath.peek());

        setMoving(true);
    }


    /**
     * Method to set path of enemy
     * @param pathSet
     */
    public void setPath(Queue<DIRECTION> pathSet) {
        movePath = pathSet;
    }

    /**
     * Method takes Player and sets agro if within the defined distance
     * @param player
     * @return
     */
    public boolean checkAgro(MovingSprite player) {
        targetEntity = player;
        /**
         * If player square position is within agroDistance (see TileManager methods)
         * -> Set agro to true
         * -> Set targetEntity to player
         * ::: In move method override: Agro = true => We run path finding and change pathSet to shortest path to player
         * Else return false
         *
         */
        return false;
    }


    @Override
    public boolean moveBlocked() {
        if (targetTile == null && !(targetTile.getOccupiedBy() instanceof Player)) {
            //Take this tile out of rotation since we can't go here
//            setDirectionMovement(movePath.remove());
//            targetTile = tm.getAdjecentTile(currentTile, directionMoving.name(), 1);
            //Skip current movement, hope it doesn't happen again
            targetTile = getNextTile();
            return true; //Break and attempt movement again
        } else if (targetTile.getOccupiedBy() instanceof Player) {
            System.out.println("I want to attack!");
            //setMoving(false);
            //Start attacking mode!!
            //Do we need to check for this since attacking will be checked for in other abstract method
            return true;
        } else {
            return false; //Not blocked!
        }
    }

    @Override
    public Tile getNextTile() {
        setDirectionMovement(movePath.remove());
        movePath.add(directionMoving);
        System.out.println("Moving: " + directionMoving.name() + " " + movePath.toString());
        return tm.getAdjecentTile(currentTile, directionMoving.name(), 1);
    }

    @Override
    public void moveStart() {
        //if (targetTile != null || !isMoving() && directionMoving == null) return;
        if ((targetTile == null || directionMoving != null) && isMoving() ) {
            targetTile = tm.getAdjecentTile(currentTile, directionMoving.name(), 1);
        }

    }

    @Override
    public boolean move(float delta) {
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
         * >>> MAIN MOVEMENT LOGIC <<<
         * If ret is true (still moving)
         * -> Set new direction with TileManager: DIRECTION findDirectionFrom(Tile curr, Tile next);
         * -> Call setDeltaMove with direction
         */
//         if (ret) {
//             directionMoving = movePath.remove();
//             movePath.add(directionMoving);
//         }

        /**
         * If above is implemented right, enemy should be able to move in 'direction' towards 'targetTile'
         * with each move() call
         */


        return ret;
    }
}
