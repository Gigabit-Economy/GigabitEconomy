package com.mygdx.gigabiteconomy.sprites;

import com.mygdx.gigabiteconomy.screens.Tile;

import java.util.ArrayList;

/**
 * Class to create a new enemy. Will have the ability to:
 * > Specify initial x, y
 * > Specify movement pattern (random about defined area or movement along radius of circle for the doggies)
 */
public class Enemy extends MovingSprite {

    private ArrayList<Tile> path;

    int[] agroDistance; //[dx, dy] tiles to cause agro
    boolean agro = false;



    public Enemy(String move_config, String attack_config, int x, int y) {
        super(move_config, attack_config, x, y);
    }


    /**
     * Method to set path of enemy
     * @param pathSet
     */
    public void setPath(ArrayList<Tile> pathSet) {
        path = pathSet;
    }

    /**
     * Method takes Player and sets agro if within the defined distance
     * @param player
     * @return
     */
    public boolean checkAgro(GameObject player) {
        /**
         * If player square position is within agroDistance (see TileManager methods)
         * -> Set agro to true
         * ::: In move method override: Agro = true => We run path finding and change pathSet to shortest path to player
         * Else return false
         *
         */
        return false;
    }

    @Override
    public boolean move(float delta) {
        boolean ret = super.move(delta); //Checks if we've arrived else moved

        /**
         * If agro is true
         * -> Find new path with TileManager: ArrayList<Tile> findShortestPathBetween(Tile from, Tile to);
         * -> Set new targetSquare from ArrayList (for next move();
         */

        /**
         * If ret is true (still moving)
         * -> Set new direction with TileManager: DIRECTION findDirectionFrom(Tile curr, Tile next);
         * -> Call setDeltaMove with direction
         */

        /**
         * If above is implemented right, enemy should be able to move in 'direction' towards 'targetTile'
         * with each move() call
         */


        return ret;
    }
}
