package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.Input;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;

/**
 * Class representing a player sprite (one per level)
 */
public class Player extends MovingSprite {
    /**
     * Create a new Player sprite (MovingSprite)
     *
     * @param movementConfig path of texture atlas movement config file (.txt)
     * @param attackingConfig path of texture atlas attacking config file (.txt)
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public Player(String movementConfig, String attackingConfig, int x, int y) {
        super(movementConfig, attackingConfig, x, y);
    }



    /**
     * Method to handle movement of the Player
     *
     * @param keycode the user inputted key
     */
    public void handleMovement(int keycode) {
        if (getTargetTile() != null) {
            System.out.println("Not finished with previous movement");
            return; // Not finished with previous movement
        }

        /**
         * Sets direction enum which defines the velocity vector (which speed and
         * direction to move in with each move() call)
         */
        Tile toTarget = null;

        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            super.setDirectionMovement(DIRECTION.WEST);
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            super.setDirectionMovement(DIRECTION.EAST);
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            super.setDirectionMovement(DIRECTION.NORTH);
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            super.setDirectionMovement(DIRECTION.SOUTH);
        } else {
            System.out.println(keycode + " not accounted for in movement logic");
            return;
        }
        setMoving(true);

        /**
         * Uses tile manager to get adjecentTile
         * Sets velocity vector based on value of direction set above
         */
//        toTarget = tm.getAdjecentTile(currentTile, directionMoving.toString(), 1);
//        setDeltaMove(directionMoving);
//
//        //Checks if Player can move to Tile
//        if (toTarget != null && toTarget.getOccupiedBy() == null) {
//            targetTile = toTarget;
//            setMoving(true);
//            System.out.println("Moving true to " + targetTile.getTileCoords()[0] + " " + targetTile.getTileCoords()[1]);
//        } else {
//            targetTile = null;
//            //setMoving(false);
//        }
    }

    /**
     * Stop the player from moving (i.e. when a movement key is lifted)
     */
    public void stopMovement() {
        setMoving(false);
    }

    @Override
    public boolean moveBlocked() {
        if (getTargetTile() == null || getTargetTile().getOccupiedBy() != null) {
            super.setDirectionMovement(null);
            setTargetTile(null);
            setMoving(false);
            return true;
        }
        return false;
    }

    @Override
    public Tile getNextTile() {
        Tile toSet = getTileManager().getAdjecentTile(getCurrentTile(), getDirectionMoving().name(), 1);

        if (toSet != null) System.out.println("Getting player a target tile " + toSet.getPositionTile()[0] + " " + toSet.getPositionTile()[1]);
        if (toSet == null || (toSet.getOccupiedBy() != null && toSet.getOccupiedBy() != this)) return null;

        super.setTargetTile(toSet); //Set target tile to one we want to go to
        return toSet;
    }

    @Override
    public void moveStart() {
        return;
    }

    @Override
    public boolean move(float delta) throws TileMovementException  {
        boolean ret = super.move(delta);

        if (ret && isMoving()) {
            setDirectionMovement(getDirectionMoving());
            System.out.println("Holding down, direction movement set");
        } else if (ret && !isMoving()) {
            setDirectionMovement(null);
            System.out.println("Setting to null");
        }



//        if (ret) {
//            if (isMoving()) {
//                //If key is still held down, get next tile
//                targetTile = tm.getAdjecentTile(targetTile, directionMoving.toString(), 1);
//                if (targetTile == null) setMoving(false);
//                return true;
//            }
//            else if (!isMoving() && targetTile != null) {
//                //If key is released, but there's still distance to cover
//                //currentTile = targetTile; useless line?
//                targetTile = null;
//                snap(delta);
//                //Reset direction moving
//                directionMoving = null;
//                return true;
//            }
//        }

        return false;
    }
}
