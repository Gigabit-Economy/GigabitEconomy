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
public class Player extends MovingSprite implements InputProcessor {
    //Deprecated variable for determining if player has to finish movement to centre of next Tile on keyUp()
    boolean stillMoving = false;
    //How much player should move vertically and horizontally every move() respectively

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

        Gdx.input.setInputProcessor(this);
        LevelOneScreen LevelScreenObj = new LevelOneScreen(LevelOneScreen);
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
     * Deal with a user's key press (initiate movement/attacking, go to pause menu etc.).
     * Part of ApplicationListener implementation.
     *
     * @param keycode the pressed key
     * @return if the key press was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("key pressed: " + keycode);

        /**
         * Movement calculated by:
         * -> Adding values defined above to deltaMove vector
         * -> Every time move() is called, deltaMove is added to position vector
         * -> This allows for diagonal movement
         */
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT || keycode == Input.Keys.D ||
                keycode == Input.Keys.RIGHT || keycode == Input.Keys.W ||
                keycode == Input.Keys.UP || keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            handleMovement(keycode);

        } else if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            pause();
        } else if (keycode == Input.Keys.SPACE) {
            System.out.println("Attacking now");
            setAttacking(true);
        } else {
            return false;
        }

        return true;
    }

    /**
     * Deal with a key press being lifted.
     * Part of ApplicationListener implementation.
     *
     * @param keycode the key lifted
     * @return if the key lift was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        // Player no longer WANTS to be moving, but we must finish animation to centre
        // of target square
        setMoving(false);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void pause() {
        //if (Gdx.input.isKeyPressed(Keys.P)) {
           // LevelScreen levelScreenObj = new LevelScreen();
           //LevelScreenObj.setPauseMenuStatus(true);
            System.out.println("P or ESQ was pressed");
       // }

    }

    @Override
    public void pause() {
        //if (Gdx.input.isKeyPressed(Keys.P)) {
           // LevelScreen levelScreenObj = new LevelScreen();
           //LevelScreenObj.setPauseMenuStatus(true);
            System.out.println("P or ESQ was pressed");
       // }

    }


}
