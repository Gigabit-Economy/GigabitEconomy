package com.mygdx.gigabiteconomy.sprites.tiled;

import java.lang.System.Logger.Level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.LevelOneScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;

import javax.sound.midi.SysexMessage;

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
        if (getTargetTile() != null && !isMoving() || super.getDirectionMoving() != null) {
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
            super.setDirectionMoving(DIRECTION.WEST);
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            super.setDirectionMoving(DIRECTION.EAST);
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            super.setDirectionMoving(DIRECTION.NORTH);
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            super.setDirectionMoving(DIRECTION.SOUTH);
        } else {
            System.out.println(keycode + " not accounted for in movement logic");
            return;
        }

        /**
         * Uses tile manager to get adjecentTile
         * Sets velocity vector based on value of direction set above
         */
        toTarget = getTileManager().getAdjecentTile(getCurrentTile(), getDirectionMoving().toString(), 1);
        setDeltaMove(getDirectionMoving().dx, getDirectionMoving().dy);

        // Checks if Player can move to Tile
        if (toTarget != null && toTarget.getOccupiedBy() == null) {
            setTargetTile(toTarget);
            setMoving(true);
            System.out.println("Moving true to " + getTargetTile().getTileCoords()[0] + " " + getTargetTile().getTileCoords()[1]);
        } else {
            setTargetTile(null);
        }
    }

    /**
     * Stop the player from moving (i.e. when a movement key is lifted)
     */
    public void stopMovement() {
        setMoving(false);
    }

    /**
     * Set for the player to attack
     */
    public void attack() {
        setAttacking(true);
        System.out.println("Attacking now");
    }
}
